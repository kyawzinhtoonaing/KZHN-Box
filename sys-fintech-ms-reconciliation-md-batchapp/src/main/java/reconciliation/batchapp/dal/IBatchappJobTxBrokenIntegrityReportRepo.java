package reconciliation.batchapp.dal;

import org.springframework.data.jpa.repository.JpaRepository;

import reconciliation.common.domain.entity.JobTxBrokenIntegrityReport;
import reconciliation.common.domain.entity.JobTxId;

public interface IBatchappJobTxBrokenIntegrityReportRepo 
	extends JpaRepository<JobTxBrokenIntegrityReport, JobTxId> {

}
