package reconciliation.servicdesk.dal;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import reconciliation.common.domain.entity.JobTxId;
import reconciliation.common.domain.entity.JobTxMismatchReport;

@Repository("IServicdeskJobTxMismatchReportRepo")
public interface IServicdeskJobTxMismatchReportRepo extends CrudRepository<JobTxMismatchReport, JobTxId> {
	@Query("SELECT report FROM JobTxMismatchReport report WHERE report.jobTxId.jid = :jobId")
	List<JobTxMismatchReport> findReportByJobId(@Param("jobId")String jobId, Sort sort);
}
