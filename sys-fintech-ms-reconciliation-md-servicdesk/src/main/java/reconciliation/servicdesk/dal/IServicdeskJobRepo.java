package reconciliation.servicdesk.dal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import reconciliation.common.domain.entity.Job;

@Repository("IServicdeskJobRepo")
public interface IServicdeskJobRepo extends CrudRepository<Job, String> {

}
