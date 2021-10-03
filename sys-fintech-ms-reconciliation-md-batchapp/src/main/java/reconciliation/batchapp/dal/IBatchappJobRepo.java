package reconciliation.batchapp.dal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import reconciliation.common.domain.entity.Job;

@Repository("IBatchappJobRepo")
public interface IBatchappJobRepo extends CrudRepository<Job, String> {

}
