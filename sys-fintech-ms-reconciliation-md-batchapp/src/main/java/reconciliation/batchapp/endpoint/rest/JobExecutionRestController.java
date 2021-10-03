package reconciliation.batchapp.endpoint.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reconciliation.batchapp.service.CSVSplitterService;
import reconciliation.batchapp.service.CSVSplitterServiceParam;
import reconciliation.batchapp.service.TxAuditService;
import reconciliation.batchapp.service.TxAuditServiceParam;
import reconciliation.batchapp.service.exception.CSVFileNotFoundException;
import reconciliation.batchapp.service.exception.InvalidJobIDException;
import reconciliation.batchapp.service.exception.TxIdFileNotFoundException;
import reconciliation.common.util.config.FileUploadConfigBean;

@CrossOrigin
@RestController
public class JobExecutionRestController {
	private final FileUploadConfigBean fileUploadConfigBean;
	private final CSVSplitterService csvSplitterService;
	private final TxAuditService txAuditService;
	
	public JobExecutionRestController(FileUploadConfigBean fileUploadConfigBean,
			CSVSplitterService csvSplitterService,
			TxAuditService txAuditService) {
		this.fileUploadConfigBean = fileUploadConfigBean;
		this.csvSplitterService = csvSplitterService;
		this.txAuditService = txAuditService;
	}
	
	@PostMapping("rc/batchapp/startjob")
	public ResponseEntity<Map<String, String>> handle(@RequestParam String jobId) throws InvalidJobIDException, CSVFileNotFoundException, IOException, TxIdFileNotFoundException {
		this.prcd1SplitCSVFiles(jobId);
		
		this.prcd2AuditCSVFiles(jobId);
		
		Map<String, String> msgMap = new HashMap<>();
		msgMap.put("statusId", "I01");
		msgMap.put("statusMsg", "Job execution succeeds.");
		return new ResponseEntity<Map<String, String>>(msgMap, HttpStatus.OK);
	}
	
	private void prcd1SplitCSVFiles(String jobId) throws InvalidJobIDException, CSVFileNotFoundException, IOException {
		CSVSplitterServiceParam svParam = new CSVSplitterServiceParam(this.fileUploadConfigBean.getCsvDir(), jobId);
		this.csvSplitterService.execute(svParam);
	}
	
	private void prcd2AuditCSVFiles(String jobId) throws InvalidJobIDException, TxIdFileNotFoundException, IOException {
		TxAuditServiceParam svParam = new TxAuditServiceParam(this.fileUploadConfigBean.getCsvDir(), jobId);
		this.txAuditService.execute(svParam);
	}
	
	@ExceptionHandler
	public ResponseEntity<Map<String, String>> handleInvalidJobIDException(InvalidJobIDException excpt) {
		Map<String, String> msgMap = new HashMap<>();
		msgMap.put("statusId", "E01");
		msgMap.put("statusMsg", "Invalid job ID!");
		return new ResponseEntity<Map<String, String>>(msgMap, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<Map<String, String>> handleCSVFileNotFoundException(CSVFileNotFoundException excpt) {
		Map<String, String> msgMap = new HashMap<>();
		msgMap.put("statusId", "E02");
		msgMap.put("statusMsg", "Upload CSV files not found! Please upload again and start the job again.");
		return new ResponseEntity<Map<String, String>>(msgMap, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<Map<String, String>> handleTxIdFileNotFoundException(TxIdFileNotFoundException excpt) {
		Map<String, String> msgMap = new HashMap<>();
		msgMap.put("statusId", "E03");
		msgMap.put("statusMsg", "Necessary files not found! Please upload again and start the job again.");
		return new ResponseEntity<Map<String, String>>(msgMap, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<Map<String, String>> handleIOException(IOException excpt) {
		Map<String, String> msgMap = new HashMap<>();
		msgMap.put("statusId", "E04");
		msgMap.put("statusMsg", "File operation error. Please try again.");
		return new ResponseEntity<Map<String, String>>(msgMap, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
