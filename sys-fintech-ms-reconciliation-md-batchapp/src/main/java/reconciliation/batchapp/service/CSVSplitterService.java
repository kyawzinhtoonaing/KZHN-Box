package reconciliation.batchapp.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.stereotype.Service;

import reconciliation.batchapp.dal.IBatchappJobRepo;
import reconciliation.batchapp.service.exception.CSVFileNotFoundException;
import reconciliation.batchapp.service.exception.InvalidJobIDException;
import reconciliation.common.domain.entity.Job;
import reconciliation.common.util.string.DelimiterStrings;
import reconciliation.common.util.string.FileUtilStrings;

@Service("CSVSplitterService")
public class CSVSplitterService {
	private final IBatchappJobRepo jobRepo;

	public CSVSplitterService(IBatchappJobRepo jobRepo) {
		this.jobRepo = jobRepo;
	}

	public void execute(CSVSplitterServiceParam param)
			throws InvalidJobIDException, CSVFileNotFoundException, IOException {
		this.prcd1ImportCSVDataToDB(param);
	}

	private void prcd1ImportCSVDataToDB(CSVSplitterServiceParam param)
			throws InvalidJobIDException, CSVFileNotFoundException, IOException {
		Job job = this.jobRepo.findById(param.getJobId()).orElseThrow(() -> new InvalidJobIDException());

		String relBasePath = param.getCsvDir() + FileUtilStrings.FILE_PATH_SEPARATOR + job.getId();
		File txIdFile = new File(
				relBasePath + FileUtilStrings.FILE_PATH_SEPARATOR + job.getId() + FileUtilStrings.TEMP_FILE_EXTENSION);
		File csvFile1 = new File(relBasePath + FileUtilStrings.FILE_PATH_SEPARATOR + job.getFile1name());
		File csvFile2 = new File(relBasePath + FileUtilStrings.FILE_PATH_SEPARATOR + job.getFile2name());

		if (!csvFile1.exists()) {
			throw new CSVFileNotFoundException(job.getId() + FileUtilStrings.FILE_PATH_SEPARATOR + job.getFile1name());
		}

		if (!csvFile2.exists()) {
			throw new CSVFileNotFoundException(job.getId() + FileUtilStrings.FILE_PATH_SEPARATOR + job.getFile2name());
		}

		this.helpToCSVDataIntoFiles(job, job.getFile1name(), csvFile1, txIdFile, relBasePath);
		this.helpToCSVDataIntoFiles(job, job.getFile2name(), csvFile2, txIdFile, relBasePath);
	}

	private void helpToCSVDataIntoFiles(Job job, String csvFileName, File csvFile, File txIdFile, String relBasePath) throws IOException {
		LineIterator lit = null;
		try {
			lit = FileUtils.lineIterator(csvFile, StandardCharsets.UTF_8.name());
			int lineNo = 0;

			while (lit.hasNext()) {
				lineNo++;
				String csvLine = lit.nextLine();

				// Header in csv file is skipped.
				if (lineNo == 1) {
					continue;
				}

				String[] csvSegs = csvLine.split(DelimiterStrings.COMMA);
				String txid = csvSegs[5];
				String transformedData = csvFileName + DelimiterStrings.COMMA
						+ lineNo + DelimiterStrings.COMMA + csvLine + FileUtilStrings.NEW_LINE;

				// write transaction ID to temp transaction IDs file.
				FileUtils.write(txIdFile, txid + FileUtilStrings.NEW_LINE, StandardCharsets.UTF_8.name(), true);

				//
				File splittedFile = new File(
						relBasePath + FileUtilStrings.FILE_PATH_SEPARATOR + txid + FileUtilStrings.TEMP_FILE_EXTENSION);
				FileUtils.write(splittedFile, transformedData, StandardCharsets.UTF_8.name(), true);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (lit != null) lit.close();
		}

		
	}

	/*
	 * private void helpToImportCSVData(Job job, String fileName, File csvFile)
	 * throws IOException { LineIterator lit = FileUtils.lineIterator(csvFile,
	 * StandardCharsets.UTF_8.name()); int lineNo = 0; List<JobTx> jobTxsToImport =
	 * new ArrayList<JobTx>();
	 * 
	 * while (lit.hasNext()) { lineNo++; String csvLine = lit.nextLine();
	 * 
	 * // Header in csv file is skipped. if (lineNo == 1) { continue; }
	 * 
	 * String[] csvSegs = csvLine.split(DelimiterStrings.COMMA);
	 * 
	 * String txid = csvSegs[5]; String pfName = csvSegs[0]; OffsetDateTime txDate =
	 * OffsetDateTime.parse(csvSegs[1], DateTimeFormatter
	 * .ofPattern(DateTimePatternStrings.PATTERN_INCSV_WITHOUT_TIMEZONE)
	 * .withZone(ZoneOffset.of(ZoneOffsetStrings.PLUS_06_30))); BigDecimal txAmt =
	 * BigDecimal.valueOf(Double.valueOf(csvSegs[2])); String txNrtive = csvSegs[3];
	 * String txDscpt = csvSegs[4]; String txType = csvSegs[6]; String walletRef = 7
	 * < csvSegs.length ? csvSegs[7] : "";
	 * 
	 * jobTxsToImport.add(new JobTx(new JobTxId(null, txid, fileName, lineNo),
	 * pfName, txDate, txAmt, txNrtive, txDscpt, txType, walletRef, job));
	 * 
	 * // Data is inserted every 100 lines. if (lineNo % 100 == 0) {
	 * this.jobTxRepo.saveAll(jobTxsToImport); jobTxsToImport = new
	 * ArrayList<JobTx>(); } }
	 * 
	 * if (jobTxsToImport.size() > 0) { this.jobTxRepo.saveAll(jobTxsToImport); } }
	 */
}
