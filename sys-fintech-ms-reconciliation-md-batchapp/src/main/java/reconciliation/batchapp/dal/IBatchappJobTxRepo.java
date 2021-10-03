package reconciliation.batchapp.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import reconciliation.common.domain.entity.JobTx;
import reconciliation.common.domain.entity.JobTxId;

@Repository("IBatchappJobTxRepo")
public interface IBatchappJobTxRepo extends JpaRepository<JobTx, JobTxId> {

}
