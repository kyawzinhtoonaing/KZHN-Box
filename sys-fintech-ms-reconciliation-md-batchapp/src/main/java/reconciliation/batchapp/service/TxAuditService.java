package reconciliation.batchapp.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reconciliation.batchapp.dal.IBatchappJobRepo;
import reconciliation.batchapp.dal.IBatchappJobReportSummaryRepo;
import reconciliation.batchapp.dal.IBatchappJobTxBrokenIntegrityReportRepo;
import reconciliation.batchapp.dal.IBatchappJobTxMismatchReportRepo;
import reconciliation.batchapp.service.exception.InvalidJobIDException;
import reconciliation.batchapp.service.exception.TxIdFileNotFoundException;
import reconciliation.common.domain.entity.Job;
import reconciliation.common.domain.entity.JobReportSummary;
import reconciliation.common.domain.entity.JobTxBrokenIntegrityReport;
import reconciliation.common.domain.entity.JobTxId;
import reconciliation.common.domain.entity.JobTxMismatchReport;
import reconciliation.common.domain.entity.constant.IntegrityReportCounterpartLineno;
import reconciliation.common.domain.entity.constant.JobStatus;
import reconciliation.common.domain.entity.constant.TxDescription;
import reconciliation.common.util.datetime.ZoneOffsetStrings;
import reconciliation.common.util.string.DelimiterStrings;
import reconciliation.common.util.string.FileUtilStrings;

@Service("TxAuditService")
public class TxAuditService {
	private final IBatchappJobRepo jobRepo;
	private final IBatchappJobTxBrokenIntegrityReportRepo jobTxBrokenIntegrityReportRepo;
	private final IBatchappJobTxMismatchReportRepo jobTxMismatchReportRepo;
	private final IBatchappJobReportSummaryRepo jobReportSummaryRepo;

	public TxAuditService(IBatchappJobRepo jobRepo,
			IBatchappJobTxBrokenIntegrityReportRepo jobTxBrokenIntegrityReportRepo,
			IBatchappJobTxMismatchReportRepo jobTxMismatchReportRepo,
			IBatchappJobReportSummaryRepo jobReportSummaryRepo) {
		this.jobRepo = jobRepo;
		this.jobTxBrokenIntegrityReportRepo = jobTxBrokenIntegrityReportRepo;
		this.jobTxMismatchReportRepo = jobTxMismatchReportRepo;
		this.jobReportSummaryRepo = jobReportSummaryRepo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void execute(TxAuditServiceParam param) throws InvalidJobIDException, TxIdFileNotFoundException, IOException {
		Job job = this.prcd1RetrieveJob(param);
		
		this.prcd2IterateTxIdAndCheck(param, job);
		
		this.prcd3StoreJobAsCompletedOne(job);
	}
	
	private Job prcd1RetrieveJob(TxAuditServiceParam param) throws InvalidJobIDException {
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.of(ZoneOffsetStrings.PLUS_06_30));
		Job job = this.jobRepo.findById(param.getJobId()).orElseThrow(() -> new InvalidJobIDException());
		job.setStartedDatetime(now);
		return job;
	}

	private void prcd2IterateTxIdAndCheck(TxAuditServiceParam param, Job job)
			throws TxIdFileNotFoundException, IOException {

		String relBasePath = param.getCsvDir() + FileUtilStrings.FILE_PATH_SEPARATOR + job.getId();
		File txIdFile = new File(
				relBasePath + FileUtilStrings.FILE_PATH_SEPARATOR + job.getId() + FileUtilStrings.TEMP_FILE_EXTENSION);

		if (!txIdFile.exists()) {
			throw new TxIdFileNotFoundException(job.getId() + FileUtilStrings.FILE_PATH_SEPARATOR + job.getId()
					+ FileUtilStrings.TEMP_FILE_EXTENSION);
		}
		
		this.helpToAuditTxs(job, txIdFile, relBasePath);
		
	}

	private void prcd3StoreJobAsCompletedOne(Job job) {
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.of(ZoneOffsetStrings.PLUS_06_30));
		job.setFinishedDatetime(now);
		job.setStatus(JobStatus.JOB_STATUS_FINISHED);
		this.jobRepo.save(job);
	}
	
	/**
	 * This method gets txId from "txIdStoreFile" and does audit transactions 
	 * by reading temporary files whose names are txId.
	 * 
	 * @param job
	 * @param txIdStoreFile
	 * @param relBasePath
	 * @throws IOException
	 */
	private void helpToAuditTxs(Job job, File txIdStoreFile, String relBasePath) throws IOException {
		List<JobTxBrokenIntegrityReport> jobTxBrokenIntegrityReports = new ArrayList<JobTxBrokenIntegrityReport>();
		JobReportSummary jobReportSummary = null;
		
		Integer file1LineCount = 0;
		Integer file1MatchCount = 0;
		Integer file1UnmatchCount = 0;

		Integer file2LineCount = 0;
		Integer file2MatchCount = 0;
		Integer file2UnmatchCount = 0;
		
		Boolean allMatch = false;
		Integer fmCount = 0;
		Integer mnmmCount = 0;
		Integer ftmmhtCount = 0;
		Integer ftmmltCount = 0;
		Integer srmmlcCount = 0;
		Integer fmmCount = 0;
		List<JobTxMismatchReport> jobTxMismatchReports = new ArrayList<JobTxMismatchReport>();

		LineIterator lit = null;
		try {
			lit = FileUtils.lineIterator(txIdStoreFile, StandardCharsets.UTF_8.name());

			Set<String> processedTxId = new HashSet<>();
			while (lit.hasNext()) {
				// Transaction ID is read from "txIdStoreFile".
				String txId = lit.nextLine();

				// Skip processing for redundant txIds.
				if (processedTxId.contains(txId)) {
					continue;
				}

				// Audit information in temporary file whose file name is "txId" (Transaction ID).
				File txIdCsvFile = new File(relBasePath + FileUtilStrings.FILE_PATH_SEPARATOR + txId + FileUtilStrings.TEMP_FILE_EXTENSION);
				
				Object[] reports = this.helpToAuditEachTxFile(job, txId, txIdCsvFile);
				@SuppressWarnings("unchecked")
				List<JobTxBrokenIntegrityReport> tmpTxBiReports = (ArrayList<JobTxBrokenIntegrityReport>)reports[0];
				JobReportSummary tmpSummaryRport = (JobReportSummary)reports[1];
				@SuppressWarnings("unchecked")
				List<JobTxMismatchReport> tmpTxMismatchReport = (ArrayList<JobTxMismatchReport>)reports[2];
				
				jobTxBrokenIntegrityReports.addAll(tmpTxBiReports);
				jobTxMismatchReports.addAll(tmpTxMismatchReport);
				
				file1LineCount += tmpSummaryRport.getFile1LineCount();
				file1MatchCount += tmpSummaryRport.getFile1MatchCount();
				file1UnmatchCount += tmpSummaryRport.getFile1UnmatchCount();
				file2LineCount += tmpSummaryRport.getFile2LineCount();
				file2MatchCount += tmpSummaryRport.getFile2MatchCount();
				file2UnmatchCount += tmpSummaryRport.getFile2UnmatchCount();
				
				fmCount += tmpSummaryRport.getFmCount();
				mnmmCount += tmpSummaryRport.getMnmmCount();
				ftmmhtCount += tmpSummaryRport.getFtmmhtCount();
				ftmmltCount += tmpSummaryRport.getFtmmltCount();
				srmmlcCount += tmpSummaryRport.getSrmmlcCount();
				fmmCount += tmpSummaryRport.getFmmCount();
				
				if (jobTxBrokenIntegrityReports.size() >= 100) {
					this.jobTxBrokenIntegrityReportRepo.saveAll(jobTxBrokenIntegrityReports);
					jobTxBrokenIntegrityReports = new ArrayList<JobTxBrokenIntegrityReport>();
				}
				
				if (jobTxMismatchReports.size() >= 100) {
					this.jobTxMismatchReportRepo.saveAll(jobTxMismatchReports);
					jobTxMismatchReports = new ArrayList<JobTxMismatchReport>();
				}
				
				processedTxId.add(txId);
			}
			
			// If there is no mismatch, consider it all matched.
			if (mnmmCount == 0 &&
					ftmmhtCount == 0 &&
					ftmmltCount == 0 &&
					srmmlcCount == 0 &&
					fmmCount == 0) {
				allMatch = true;
			}
			jobReportSummary = new JobReportSummary(null, 
					job.getFile1name(), file1LineCount, file1MatchCount, file1UnmatchCount, 
					job.getFile2name(), file2LineCount, file2MatchCount, file2UnmatchCount, 
					allMatch, fmCount, 
					mnmmCount, ftmmhtCount, ftmmltCount, srmmlcCount, fmmCount, job);
			
			if (jobTxBrokenIntegrityReports.size() > 0) {
				this.jobTxBrokenIntegrityReportRepo.saveAll(jobTxBrokenIntegrityReports);
			}
			
			if (jobTxMismatchReports.size() > 0) {
				this.jobTxMismatchReportRepo.saveAll(jobTxMismatchReports);
			}
			
			this.jobReportSummaryRepo.save(jobReportSummary);
		} catch (IOException e) {
			throw e;
		} finally {
			if (lit != null) lit.close();
		}
	}
	
	/**
	 * This method reads transaction data in given "txIdCsvFile" and make audit.
	 * After that, it produces three reports (broken integrity report, mismatch summary report,
	 * and mismatch report).
	 * 
	 * @param job
	 * @param txId
	 * @param txIdCsvFile
	 * @return
	 * 	Object array whose elements are:
	 * 		- 0 (broken integrity report)
	 * 		- 1 (summary report)
	 * 		- 2 (mismatch report)
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private Object[] helpToAuditEachTxFile(Job job, String txId, File txIdCsvFile) throws IOException {
		
		List<JobTxBrokenIntegrityReport> txBiReports = null;
		JobReportSummary summaryReport = null;
		List<JobTxMismatchReport> txMMReports = null;
		
		LineIterator lit = null;
		
		// <csv_file_name, data_lines>
		Map<String, List<String[]>> txMap = new HashMap<String, List<String[]>>();
		
		try {
			lit = FileUtils.lineIterator(txIdCsvFile, StandardCharsets.UTF_8.name());
			String txCsvLine = null;
			while (lit.hasNext()) {
				txCsvLine = lit.nextLine();
				
				// tx data segments
				String[] txDataSegs = txCsvLine.split(DelimiterStrings.COMMA);
				
				// Append tx data segments into key that is CSV file name.
				// txDataSegs[0] is original CSV file name.
				List<String[]> dataLine = txMap.getOrDefault(txDataSegs[0], new ArrayList<String[]>());
				dataLine.add(txDataSegs);
				txMap.put(txDataSegs[0], dataLine);
			}
			
			// Check integrity of each file.
			txBiReports = this.helpToCheckIntegrity(job, txId, txMap);
			
			// Check mismatch between files.
			Object[] mmReports = this.helpToCheckMisMatch(job, txId, txMap);
			summaryReport = (JobReportSummary)mmReports[0];
			txMMReports = (ArrayList<JobTxMismatchReport>)mmReports[1];
			
		} catch (IOException e) {
			throw e;
		} finally {
			if (lit != null) lit.close();
		}
		
		return new Object[] { txBiReports, summaryReport, txMMReports };
	}
	
	private List<JobTxBrokenIntegrityReport> helpToCheckIntegrity(Job job, String txId, Map<String, List<String[]>> txMap) {
		List<JobTxBrokenIntegrityReport> txBiReports = new ArrayList<JobTxBrokenIntegrityReport>();
		
		List<String[]> dataLines = null;
		int txCount = -1;
		// Iterate each file and check if each file has correct transaction information.
		for (String originalCSVFileName: txMap.keySet()) {
			
			dataLines = txMap.get(originalCSVFileName);
			txCount = dataLines.size();

			// If transaction has possibility of being rolled back and a chance of anomaly.
			if (txCount == 2) {
				String[] line1 = dataLines.get(0);
				String[] line2 = dataLines.get(1);
				
				boolean isProfileNameOK = line1[2].equals(line2[2]);
				boolean isTransactionDateOK = line1[3].equals(line2[3]);

				boolean isTransactionAmountOK = (line1[4].isBlank() && line2[4].isBlank())
						||
						(
							!line1[4].isBlank() && !line2[4].isBlank() && 
							BigDecimal.valueOf(Double.valueOf(line1[4]))
								.add(BigDecimal.valueOf(Double.valueOf(line2[4])))
								.compareTo(BigDecimal.ZERO) == 0
						);
				boolean isTransactionNarrativeOK = line1[5].equals(line2[5]);
				boolean isTransactionDescriptionOK = TxDescription.DEDUCT.equals(line1[6]) && TxDescription.REVERSAL.equals(line2[6]);
				boolean isTransactionTypeOK = (8 >= line1.length && 8 >= line2.length)
						||
						(8 < line1.length && 8 < line2.length && line1[8].equals(line2[8]));
				boolean isWalletReferenceOK = (9 >= line1.length && 9 >= line2.length)
						||
						(9 < line1.length && 9 < line2.length && line1[9].equals(line2[9]));
				
				if (!isProfileNameOK ||
						!isTransactionDateOK ||
						!isTransactionAmountOK ||
						!isTransactionNarrativeOK ||
						!isTransactionDescriptionOK ||
						!isTransactionTypeOK ||
						!isWalletReferenceOK) {

					txBiReports.add(this.helpToCreateJobTxBrokenIntegrityReport(job, txId, line1, Integer.valueOf(line2[1])));
					txBiReports.add(this.helpToCreateJobTxBrokenIntegrityReport(job, txId, line2, Integer.valueOf(line1[1])));
					continue;
				}
				
			}
			// If abnormal transaction frequencies have been detected.
			else if (txCount > 2) {
				for (String[] line : dataLines) {
					txBiReports.add(this.helpToCreateJobTxBrokenIntegrityReport(job, 
							txId, 
							line, 
							IntegrityReportCounterpartLineno.NONEXISTING_LINE_NO));
				}
				continue;
			}
		}
		
		return txBiReports;
	}
	
	
	/**
	 * This method will do mismatch check in two CSV files for given transaction ID.
	 * After checking, summary report and mismatch report will be returned.
	 * 
	 * @param job
	 * @param txId
	 * @param txMap
	 * @return 
	 * 		Object array whose elements are:
	 * 		- 0 (summary report)
	 * 		- 1 (mismatch report)
	 */
	private Object[] helpToCheckMisMatch(Job job, String txId, Map<String, List<String[]>> txMap) {
		Integer file1LineCount = null;
		Integer file1MatchCount = 0;
		Integer file1UnmatchCount = 0;

		Integer file2LineCount = null;
		Integer file2MatchCount = 0;
		Integer file2UnmatchCount = 0;
		
		Integer fmCount = 0;
		Integer mnmmCount = 0;
		Integer ftmmhtCount = 0;
		Integer ftmmltCount = 0;
		Integer srmmlcCount = 0;
		Integer fmmCount = 0;
		
		JobReportSummary summaryReport = null;
		List<JobTxMismatchReport> txMMReports = new ArrayList<JobTxMismatchReport>();
		
		List<String[]> file1DataLines = txMap.get(job.getFile1name());
		List<String[]> file2DataLines = txMap.get(job.getFile2name());
		
		// Elect iteration count based on greater line count between two CSV files.
		int itCount = 0;
		file1LineCount = file1DataLines != null ? file1DataLines.size() : 0;
		file2LineCount = file2DataLines != null ? file2DataLines.size() : 0;
		if (file1LineCount == file2LineCount) {
			itCount = file1LineCount;
		} else {
			itCount = file1LineCount > file2LineCount ? file1LineCount : file2LineCount;
		}
		
		
		for (int i = 0; i < itCount; i++) {
			String[] file1Line = i < file1LineCount ? file1DataLines.get(i) : null;
			String[] file2Line = i < file2LineCount ? file2DataLines.get(i) : null;
			
			// If transaction information exists only in one side of two CSV files.
			// This will regard as "full mismatch".
			if (file1Line == null || file2Line == null) {
				txMMReports.add(this.helpToCreateJobTxMismatchReport(job, 
						txId, 
						file1Line == null ? file2Line : file1Line,
						false,
						false,
						false));
				
				file1UnmatchCount += file2Line == null ? 1 : 0;
				file2UnmatchCount += file1Line == null ? 1 : 0;
				fmmCount += 1;
				continue;
			}
			
			// Check for Transaction information (Main information)
			// Transaction information (Main information) are:
			// 		1. TransactionID (this information is automatically matched because we group data by it)
			//  	2. TransactionType
			//  	3. TransactionDescription
			//  	4. TransactionAmount
			boolean isTransactionTypeOK = (
				(8 < file1Line.length && 8 < file2Line.length &&  file1Line[8].equals(file2Line[8]))
				||
				(8 >= file1Line.length && 8 >= file2Line.length)
			);
			boolean isTransactionDescriptionOK = file1Line[6].equals(file2Line[6]);
			boolean isTransactionAmountOK = file1Line[4].equals(file2Line[4]);
			boolean txinfoMatched = isTransactionTypeOK && isTransactionDescriptionOK && isTransactionAmountOK;
			
			// Check for Transaction event information
			// Transaction event information are:
			//		1. TransactionDate	
			//		2. WalletReference	
			//		3. TransactionNarrative
			boolean isTransactionDateOK = file1Line[3].equals(file2Line[3]);
			boolean isWalletReferenceOK = (
					(9 < file1Line.length && 9 < file2Line.length &&  file1Line[9].equals(file2Line[9]))
					||
					(9 >= file1Line.length && 9 >= file2Line.length)
				);
			boolean isTransactionNarrativeOK = file1Line[5].equals(file2Line[5]);
			boolean txeinfoMatched = isTransactionDateOK && isWalletReferenceOK && isTransactionNarrativeOK;
			
			// Check for Transaction supplement information
			// Transaction supplement information is:
			//		1. ProfileName
			boolean isProfileName = file1Line[2].equals(file2Line[2]);
			boolean txsinfoMatched = isProfileName;
			
			if (txinfoMatched && txeinfoMatched && txsinfoMatched) {
				file1MatchCount += 1;
				file2MatchCount += 1;
				fmCount += 1;
			} else {
				file1UnmatchCount += 1;
				file2UnmatchCount += 1;
				
				mnmmCount += txinfoMatched && txeinfoMatched && !txsinfoMatched ? 1 : 0;
				ftmmhtCount += (txinfoMatched && !txeinfoMatched && txsinfoMatched) || (txinfoMatched && !txeinfoMatched && !txsinfoMatched) ? 1 : 0;
				ftmmltCount += (!txinfoMatched && txeinfoMatched && txsinfoMatched) || (!txinfoMatched && txeinfoMatched && !txsinfoMatched) ? 1 : 0;
				srmmlcCount += !txinfoMatched && !txeinfoMatched && txsinfoMatched ? 1 : 0;
				fmmCount += !txinfoMatched && !txeinfoMatched && !txsinfoMatched ? 1 : 0;
				
				txMMReports.add(this.helpToCreateJobTxMismatchReport(job, 
						txId, 
						file1Line,
						txinfoMatched,
						txeinfoMatched,
						txsinfoMatched));
				
				txMMReports.add(this.helpToCreateJobTxMismatchReport(job, 
						txId, 
						file2Line,
						txinfoMatched,
						txeinfoMatched,
						txsinfoMatched));
			}
		}
		
		summaryReport = new JobReportSummary(null, 
				null, file1LineCount, file1MatchCount, file1UnmatchCount, 
				null, file2LineCount, file2MatchCount, file2UnmatchCount, 
				false, fmCount, 
				mnmmCount, ftmmhtCount, ftmmltCount, srmmlcCount, fmmCount, job);
		
		return new Object[] { summaryReport, txMMReports };
	}
	
	private JobTxBrokenIntegrityReport helpToCreateJobTxBrokenIntegrityReport(Job job, 
			String txId, 
			String[] txDataLine,
			Integer counterpartLineno) {
		
		return new JobTxBrokenIntegrityReport(new JobTxId(null, txId, txDataLine[0], Integer.valueOf(txDataLine[1])), 
				txDataLine[2], 
				txDataLine[3], 
				txDataLine[4], 
				txDataLine[5], 
				txDataLine[6], 
				8 < txDataLine.length ? txDataLine[8] : "", 
				9 < txDataLine.length ? txDataLine[9] : "", 
				counterpartLineno, 
				job);
	}
	
	private JobTxMismatchReport helpToCreateJobTxMismatchReport(Job job, 
			String txId, 
			String[] txDataLine,
			Boolean txinfoMatched,
			Boolean txeinfoMatched,
			Boolean txsinfoMatched) {
		
		return new JobTxMismatchReport(new JobTxId(null, txId, txDataLine[0], Integer.valueOf(txDataLine[1])), 
				txDataLine[2], 
				txDataLine[3], 
				txDataLine[4], 
				txDataLine[5], 
				txDataLine[6], 
				8 < txDataLine.length ? txDataLine[8] : "", 
				9 < txDataLine.length ? txDataLine[9] : "", 
				txinfoMatched,
				txeinfoMatched,
				txsinfoMatched,
				job);
	}
}
