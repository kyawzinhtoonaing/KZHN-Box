package reconciliation.servicdesk.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reconciliation.common.domain.entity.JobReportSummary;
import reconciliation.common.domain.entity.JobTxBrokenIntegrityReport;
import reconciliation.common.domain.entity.JobTxMismatchReport;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReconciliationReportRetrievalServiceResult {
	private List<JobTxBrokenIntegrityReport> brokenIntegrityReports;
	private JobReportSummary misMatchSummaryReport;
	private List<JobTxMismatchReport> misMatchDetailReports;
}
