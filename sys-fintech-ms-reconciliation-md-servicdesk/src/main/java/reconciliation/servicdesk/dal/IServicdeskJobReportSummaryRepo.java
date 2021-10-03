package reconciliation.servicdesk.dal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import reconciliation.common.domain.entity.JobReportSummary;

@Repository("IServicdeskJobReportSummaryRepo")
public interface IServicdeskJobReportSummaryRepo extends CrudRepository<JobReportSummary, String> {

}
