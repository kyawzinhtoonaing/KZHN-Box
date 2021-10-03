package reconciliation.servicdesk.service;

import org.springframework.stereotype.Service;

import reconciliation.common.domain.entity.Job;
import reconciliation.servicdesk.dal.IServicdeskJobRepo;
import reconciliation.servicdesk.service.exception.InvalidJobIDException;

@Service("ReconciliationJobInfoService")
public class ReconciliationJobInfoService {
	private final IServicdeskJobRepo jobRepo;
	
	public ReconciliationJobInfoService(IServicdeskJobRepo jobRepo) {
		this.jobRepo = jobRepo;
	}
	
	public ReconciliationJobInfoServiceResult execute(ReconciliationJobInfoServiceParam param) throws InvalidJobIDException {
		Job job = this.prcd1RetriveJobInfo(param.getJobId());
		
		return this.prcd2PrepareResult(job);
	}
	
	private Job prcd1RetriveJobInfo(String jobId) throws InvalidJobIDException {
		Job job = this.jobRepo.findById(jobId).orElseThrow(() -> new InvalidJobIDException());
		return job;
	}
	
	private ReconciliationJobInfoServiceResult prcd2PrepareResult(Job job) {
		return new ReconciliationJobInfoServiceResult(job);
	}
}
