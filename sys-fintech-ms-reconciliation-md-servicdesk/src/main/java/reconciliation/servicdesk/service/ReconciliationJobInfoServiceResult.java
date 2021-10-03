package reconciliation.servicdesk.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reconciliation.common.domain.entity.Job;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReconciliationJobInfoServiceResult {
	private Job job;
}
