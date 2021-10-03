package reconciliation.servicdesk.service;

import java.io.File;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reconciliation.common.domain.entity.Job;
import reconciliation.common.domain.entity.constant.JobStatus;
import reconciliation.common.util.datetime.DateTimePatternStrings;
import reconciliation.common.util.datetime.ZoneOffsetStrings;
import reconciliation.common.util.string.DelimiterStrings;
import reconciliation.servicdesk.dal.IServicdeskJobRepo;
import reconciliation.servicdesk.service.exception.InvalidUploadFileNameException;

@Service("ReconciliationJobCreationService")
public class ReconciliationJobCreationService {
	private final IServicdeskJobRepo jobRepo;
	
	public ReconciliationJobCreationService(IServicdeskJobRepo jobRepo) {
		this.jobRepo = jobRepo;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public ReconciliationJobCreationServiceResult execute(ReconciliationJobCreationServiceParam param) throws InvalidUploadFileNameException, IOException {
		Job jobToBeSaved = this.prcd1PrepareJob(param);
		
		this.prcd2StoreFile(param, jobToBeSaved);
		
		this.prcd3SaveJobInDB(jobToBeSaved);
		
		return this.prcd4PrepareResult(jobToBeSaved);
	}
	
	/**
	 * Create Job object to be saved in the database.
	 * 
	 * @param param
	 * @return
	 * 	Job object to be save.
	 * @throws InvalidUploadFileNameException 
	 */
	private Job prcd1PrepareJob(ReconciliationJobCreationServiceParam param) throws InvalidUploadFileNameException {
		DateTimeFormatter sdf = DateTimeFormatter.ofPattern(DateTimePatternStrings.PATTERN_PLAIN_WITHOUT_TIMEZONE);
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.of(ZoneOffsetStrings.PLUS_06_30));
		String jobId = now.format(sdf) + UUID.randomUUID().toString().split(DelimiterStrings.DASH)[1];
		
		String file1Name = param.getFile1().getOriginalFilename();
		String file2Name = param.getFile2().getOriginalFilename();
		
		String invalidFileName = "..";
		if (file1Name.contains(invalidFileName) || 
				file2Name.contains(invalidFileName) ||
				file1Name.contains(file2Name)) {
			throw new InvalidUploadFileNameException();
		}
		
		return new Job(jobId, 
				file1Name, 
				file2Name, 
				JobStatus.JOB_STATUS_STARTED, 
				now, null, 
				null);
	}
	
	private void prcd2StoreFile(ReconciliationJobCreationServiceParam param, Job job) throws IOException {
		String relBasePath = param.getCsvDir() + "/" + job.getId();
		File csvFile1 = new File(relBasePath + "/" + job.getFile1name());
		File csvFile2 = new File(relBasePath + "/" + job.getFile2name());
		
		FileUtils.createParentDirectories(csvFile1);
		
		FileUtils.copyInputStreamToFile(param.getFile1().getInputStream(), csvFile1);
		FileUtils.copyInputStreamToFile(param.getFile2().getInputStream(), csvFile2);
	}
	
	/**
	 * Job in method parameter is saved in the database.
	 * 
	 * @param jobToBeSaved
	 */
	private void prcd3SaveJobInDB(Job jobToBeSaved) {
		this.jobRepo.save(jobToBeSaved);
	}
	
	/**
	 * Service result is prepared.
	 * 
	 * @param savedJob
	 * @return
	 */
	private ReconciliationJobCreationServiceResult prcd4PrepareResult(Job savedJob) {
		return new ReconciliationJobCreationServiceResult(savedJob);
	}
}
