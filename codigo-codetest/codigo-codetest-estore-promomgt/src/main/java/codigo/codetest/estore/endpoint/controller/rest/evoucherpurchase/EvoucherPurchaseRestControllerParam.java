package codigo.codetest.estore.endpoint.controller.rest.evoucherpurchase;

import java.util.List;

public class EvoucherPurchaseRestControllerParam {
	
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

	public static class EvoucherOwner {
		private String ownerPhNumber;
		private Integer evoucherCount;
		private Boolean forSelf;
		
		public String getOwnerPhNumber() {
			return ownerPhNumber;
		}
		public void setOwnerPhNumber(String ownerPhNumber) {
			this.ownerPhNumber = ownerPhNumber;
		}
		public Integer getEvoucherCount() {
			return evoucherCount;
		}
		public void setEvoucherCount(Integer evoucherCount) {
			this.evoucherCount = evoucherCount;
		}
		public Boolean getForSelf() {
			return forSelf;
		}
		public void setForSelf(Boolean forSelf) {
			this.forSelf = forSelf;
		}
	}

	public static class UserPaymentMethod {
		private String id;
		private String username;
		private String pmethodId;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPmethodId() {
			return pmethodId;
		}
		public void setPmethodId(String pmethodId) {
			this.pmethodId = pmethodId;
		}
	}
}
