package reconciliation.servicdesk.endpoint.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import reconciliation.common.domain.entity.Job;
import reconciliation.common.util.config.FileUploadConfigBean;
import reconciliation.servicdesk.service.ReconciliationJobCreationService;
import reconciliation.servicdesk.service.ReconciliationJobCreationServiceParam;
import reconciliation.servicdesk.service.exception.InvalidUploadFileNameException;

@CrossOrigin
@RestController
public class ReconciliationJobCreationRestController {
	private final FileUploadConfigBean fileUploadConfigBean;
	private final ReconciliationJobCreationService service;
	
	public ReconciliationJobCreationRestController(FileUploadConfigBean fileUploadConfigBean,
			ReconciliationJobCreationService service) {
		this.fileUploadConfigBean = fileUploadConfigBean;
		this.service = service;
	}
	
	@PostMapping("rc/servicdesk/job")
	@ResponseStatus(HttpStatus.CREATED)
	public Job handle(@RequestBody MultipartFile csvFile1, @RequestBody MultipartFile csvFile2) throws InvalidUploadFileNameException, 
			IOException {
		ReconciliationJobCreationServiceParam svParam = this.prcd1PrepareServiceParam(csvFile1, csvFile2);
		
		return this.prcd2CreateReconciliationJob(svParam);
	}
	
	private ReconciliationJobCreationServiceParam prcd1PrepareServiceParam(MultipartFile csvFile1, MultipartFile csvFile2) {
		return new ReconciliationJobCreationServiceParam(this.fileUploadConfigBean.getCsvDir(), 
				csvFile1, 
				csvFile2);
	}
	
	private Job prcd2CreateReconciliationJob(ReconciliationJobCreationServiceParam svParam) throws InvalidUploadFileNameException, IOException {
		return this.service.execute(svParam).getSavedJob();
	}
	
	@ExceptionHandler
	public ResponseEntity<Map<String, String>> handleInvalidUploadFileNameException(InvalidUploadFileNameException excpt) {
		Map<String, String> msgMap = new HashMap<>();
		msgMap.put("statusId", "E01");
		msgMap.put("statusMsg", "Invalid file name!");
		return new ResponseEntity<Map<String, String>>(msgMap, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<Map<String, String>> handleIOException(IOException excpt) {
		Map<String, String> msgMap = new HashMap<>();
		msgMap.put("statusId", "E02");
		msgMap.put("statusMsg", "File operation error. Please try again!");
		return new ResponseEntity<Map<String, String>>(msgMap, HttpStatus.INTERNAL_SERVER_ERROR);
	} 
}
