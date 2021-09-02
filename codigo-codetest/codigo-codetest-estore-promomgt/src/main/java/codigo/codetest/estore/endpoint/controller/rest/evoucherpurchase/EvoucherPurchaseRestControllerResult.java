package codigo.codetest.estore.endpoint.controller.rest.evoucherpurchase;

import java.util.List;

public class EvoucherPurchaseRestControllerResult {

	private String evoucherPurchaseId;
	private List<String> evoucherTaskIds;
	
	public String getEvoucherPurchaseId() {
		return evoucherPurchaseId;
	}
	public void setEvoucherPurchaseId(String evoucherPurchaseId) {
		this.evoucherPurchaseId = evoucherPurchaseId;
	}
	public List<String> getEvoucherTaskIds() {
		return evoucherTaskIds;
	}
	public void setEvoucherTaskIds(List<String> evoucherTaskIds) {
		this.evoucherTaskIds = evoucherTaskIds;
	}
}
