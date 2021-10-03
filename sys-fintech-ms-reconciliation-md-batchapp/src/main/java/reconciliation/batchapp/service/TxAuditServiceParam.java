package reconciliation.batchapp.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TxAuditServiceParam {
	private String csvDir;
	private String jobId;
}
