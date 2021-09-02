package codigo.codetest.estore.service.evoucherpurchase;

import java.util.List;

import codigo.codetest.estore.domain.entity.EvoucherPurchase;
import codigo.codetest.estore.domain.entity.EvoucherTask;

public class EvoucherPurchaseServiceResult {
	private EvoucherPurchase evoucherPurchase;
	private List<EvoucherTask> evoucherTasks;
	
	public EvoucherPurchase getEvoucherPurchase() {
		return evoucherPurchase;
	}
	public void setEvoucherPurchase(EvoucherPurchase evoucherPurchase) {
		this.evoucherPurchase = evoucherPurchase;
	}
	public List<EvoucherTask> getEvoucherTasks() {
		return evoucherTasks;
	}
	public void setEvoucherTasks(List<EvoucherTask> evoucherTasks) {
		this.evoucherTasks = evoucherTasks;
	}
}
