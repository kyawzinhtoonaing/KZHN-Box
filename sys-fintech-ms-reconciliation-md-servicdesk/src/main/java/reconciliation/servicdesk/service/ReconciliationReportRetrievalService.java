package reconciliation.servicdesk.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import reconciliation.common.domain.entity.JobReportSummary;
import reconciliation.common.domain.entity.JobTxBrokenIntegrityReport;
import reconciliation.common.domain.entity.JobTxMismatchReport;
import reconciliation.servicdesk.dal.IServicdeskJobReportSummaryRepo;
import reconciliation.servicdesk.dal.IServicdeskJobTxBrokenIntegrityReportRepo;
import reconciliation.servicdesk.dal.IServicdeskJobTxMismatchReportRepo;
import reconciliation.servicdesk.service.exception.InvalidJobIDException;

@Service("ReconciliationReportRetrievalService")
public class ReconciliationReportRetrievalService {
	private final IServicdeskJobReportSummaryRepo jobReportSummaryRepo;
	private final IServicdeskJobTxBrokenIntegrityReportRepo jobTxBrokenIntegrityReportRepo;
	private final IServicdeskJobTxMismatchReportRepo jobTxMismatchReportRepo;
	
	public ReconciliationReportRetrievalService(IServicdeskJobReportSummaryRepo jobReportSummaryRepo,
			IServicdeskJobTxBrokenIntegrityReportRepo jobTxBrokenIntegrityReportRepo,
			IServicdeskJobTxMismatchReportRepo jobTxMismatchReportRepo) {
		this.jobReportSummaryRepo = jobReportSummaryRepo;
		this.jobTxBrokenIntegrityReportRepo = jobTxBrokenIntegrityReportRepo;
		this.jobTxMismatchReportRepo = jobTxMismatchReportRepo;
	}
	
	public ReconciliationReportRetrievalServiceResult execute(ReconciliationReportRetrievalServiceParam param) throws InvalidJobIDException {
		List<JobTxBrokenIntegrityReport> brokenIntegrityReports = this.prcd1RetrieveBrokenIntegrityReports(param.getJobId());
		
		JobReportSummary misMatchSummaryReport = this.prcd2RetrieveMisMatchSummarReport(param.getJobId());
		
		List<JobTxMismatchReport> misMatchDetailReports = this.prcd3RetrieveMisMatchDetailReports(param.getJobId());
		
		return this.prepareResult(brokenIntegrityReports, misMatchSummaryReport, misMatchDetailReports);
	}
	
	private List<JobTxBrokenIntegrityReport> prcd1RetrieveBrokenIntegrityReports(String jobId) {
		return this.jobTxBrokenIntegrityReportRepo.findReportByJobId(jobId, Sort.by("jobTxId.txid", "jobTxId.filename", "jobTxId.lineno"));
	}
	
	private JobReportSummary prcd2RetrieveMisMatchSummarReport(String jobId) throws InvalidJobIDException {
		return this.jobReportSummaryRepo.findById(jobId).orElseThrow(() -> new InvalidJobIDException());
	}
	
	private List<JobTxMismatchReport> prcd3RetrieveMisMatchDetailReports(String jobId) {
		return this.jobTxMismatchReportRepo.findReportByJobId(jobId, Sort.by("jobTxId.txid", "jobTxId.filename", "jobTxId.lineno"));
	}
	
	private ReconciliationReportRetrievalServiceResult prepareResult(List<JobTxBrokenIntegrityReport> brokenIntegrityReports,
			JobReportSummary misMatchSummaryReport,
			List<JobTxMismatchReport> misMatchDetailReports) {
		
		return new ReconciliationReportRetrievalServiceResult(brokenIntegrityReports, misMatchSummaryReport, misMatchDetailReports);
	}
}
