package reconciliation.servicdesk.endpoint.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reconciliation.common.domain.entity.Job;
import reconciliation.servicdesk.service.ReconciliationJobInfoService;
import reconciliation.servicdesk.service.ReconciliationJobInfoServiceParam;
import reconciliation.servicdesk.service.exception.InvalidJobIDException;

@CrossOrigin
@RestController
public class ReconciliationJobInfoRestController {
	private final ReconciliationJobInfoService service;
	
	public ReconciliationJobInfoRestController(ReconciliationJobInfoService service) {
		this.service = service;
	}
	
	@GetMapping("rc/servicdesk/job/{jobId}")
	public Job handle(@PathVariable("jobId") String jobId) throws InvalidJobIDException {
		
		return this.prcd1RetrieveJobInfo(jobId);
	}
	
	private Job prcd1RetrieveJobInfo(String jobId) throws InvalidJobIDException {
		ReconciliationJobInfoServiceParam svParam = new ReconciliationJobInfoServiceParam(jobId);
		return this.service.execute(svParam).getJob();
	}
	
	@ExceptionHandler
	public ResponseEntity<Map<String, String>> handleInvalidJobIDException(InvalidJobIDException excpt) {
		Map<String, String> msgMap = new HashMap<>();
		msgMap.put("statusId", "E01");
		msgMap.put("statusMsg", "Invalid Job ID!");
		return new ResponseEntity<Map<String, String>>(msgMap, HttpStatus.NOT_FOUND);
	}
}
