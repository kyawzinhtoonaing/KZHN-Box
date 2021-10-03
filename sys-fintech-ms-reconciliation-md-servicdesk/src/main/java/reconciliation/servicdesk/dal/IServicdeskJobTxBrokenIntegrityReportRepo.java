package reconciliation.servicdesk.dal;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import reconciliation.common.domain.entity.JobTxBrokenIntegrityReport;
import reconciliation.common.domain.entity.JobTxId;

@Repository("IServicdeskJobTxBrokenIntegrityReportRepo")
public interface IServicdeskJobTxBrokenIntegrityReportRepo extends CrudRepository<JobTxBrokenIntegrityReport, JobTxId> {
	@Query("SELECT report FROM JobTxBrokenIntegrityReport report WHERE report.jobTxId.jid = :jobId")
	List<JobTxBrokenIntegrityReport> findReportByJobId(@Param("jobId")String jobId, Sort sort);
}
