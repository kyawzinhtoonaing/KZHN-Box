package reconciliation.servicdesk.service;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReconciliationJobCreationServiceParam {
	private String csvDir;
	private MultipartFile file1;
	private MultipartFile file2;
}
