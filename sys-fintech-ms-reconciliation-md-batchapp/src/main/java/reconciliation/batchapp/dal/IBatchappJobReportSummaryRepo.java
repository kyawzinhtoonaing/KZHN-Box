package reconciliation.batchapp.dal;

import org.springframework.data.jpa.repository.JpaRepository;

import reconciliation.common.domain.entity.JobReportSummary;

public interface IBatchappJobReportSummaryRepo extends JpaRepository<JobReportSummary, String> {

}
