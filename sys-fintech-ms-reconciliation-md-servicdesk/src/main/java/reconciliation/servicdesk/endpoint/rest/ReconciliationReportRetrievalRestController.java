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

import reconciliation.servicdesk.service.ReconciliationReportRetrievalService;
import reconciliation.servicdesk.service.ReconciliationReportRetrievalServiceParam;
import reconciliation.servicdesk.service.ReconciliationReportRetrievalServiceResult;
import reconciliation.servicdesk.service.exception.InvalidJobIDException;

@CrossOrigin
@RestController
public class ReconciliationReportRetrievalRestController {
	private final ReconciliationReportRetrievalService service;
	
	public ReconciliationReportRetrievalRestController(ReconciliationReportRetrievalService service) {
		this.service = service;
	}
	
	@GetMapping("rc/servicdesk/reports/{jobId}")
	public ReconciliationReportRetrievalRestControllerResult handle(@PathVariable("jobId") String jobId) throws InvalidJobIDException {
		return this.prcd1RetrieveReports(jobId);
	}
	
	private ReconciliationReportRetrievalRestControllerResult prcd1RetrieveReports(String jobId) throws InvalidJobIDException {
		ReconciliationReportRetrievalServiceParam svParam = new ReconciliationReportRetrievalServiceParam(jobId);
		ReconciliationReportRetrievalServiceResult svResult = this.service.execute(svParam);
		
		ReconciliationReportRetrievalRestControllerResult result = new ReconciliationReportRetrievalRestControllerResult(
				svResult.getBrokenIntegrityReports(), 
				svResult.getMisMatchSummaryReport(), 
				svResult.getMisMatchDetailReports());
		
		return result;
	}
	
	@ExceptionHandler
	public ResponseEntity<Map<String, String>> handleInvalidJobIDException(InvalidJobIDException excpt) {
		Map<String, String> msgMap = new HashMap<>();
		msgMap.put("statusId", "E01");
		msgMap.put("statusMsg", "Invalid Job ID!");
		return new ResponseEntity<Map<String, String>>(msgMap, HttpStatus.NOT_FOUND);
	}
}
