package reconciliation.batchapp.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CSVSplitterServiceParam {
	private String csvDir;
	private String jobId;
}
