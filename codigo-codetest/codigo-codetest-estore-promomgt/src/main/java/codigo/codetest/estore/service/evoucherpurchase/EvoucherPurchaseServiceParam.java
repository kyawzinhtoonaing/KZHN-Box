package codigo.codetest.estore.service.evoucherpurchase;

import java.util.List;

import codigo.codetest.estore.domain.entity.EvoucherOwner;
import codigo.codetest.estore.domain.entity.UserPaymentMethod;

public class EvoucherPurchaseServiceParam {

	private String evoucherDefId;
	private List<EvoucherOwner> evoucherOwners;
	private UserPaymentMethod userPaymentMethod;
	
	public String getEvoucherDefId() {
		return evoucherDefId;
	}
	public void setEvoucherDefId(String evoucherDefId) {
		this.evoucherDefId = evoucherDefId;
	}
	public List<EvoucherOwner> getEvoucherOwners() {
		return evoucherOwners;
	}
	public void setEvoucherOwners(List<EvoucherOwner> evoucherOwners) {
		this.evoucherOwners = evoucherOwners;
	}
	public UserPaymentMethod getUserPaymentMethod() {
		return userPaymentMethod;
	}
	public void setUserPaymentMethod(UserPaymentMethod userPaymentMethod) {
		this.userPaymentMethod = userPaymentMethod;
	}
}
