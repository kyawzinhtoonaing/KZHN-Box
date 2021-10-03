package reconciliation.batchapp.dal;

import org.springframework.data.jpa.repository.JpaRepository;

import reconciliation.common.domain.entity.JobTxId;
import reconciliation.common.domain.entity.JobTxMismatchReport;

public interface IBatchappJobTxMismatchReportRepo
	extends JpaRepository<JobTxMismatchReport, JobTxId> {

}
