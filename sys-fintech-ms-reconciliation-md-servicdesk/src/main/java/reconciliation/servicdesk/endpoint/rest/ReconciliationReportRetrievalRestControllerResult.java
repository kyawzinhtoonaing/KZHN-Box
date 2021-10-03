package reconciliation.servicdesk.endpoint.rest;

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
public class ReconciliationReportRetrievalRestControllerResult {
	private List<JobTxBrokenIntegrityReport> brokenIntegrityReports;
	private JobReportSummary misMatchSummaryReport;
	private List<JobTxMismatchReport> misMatchDetailReports;
}
