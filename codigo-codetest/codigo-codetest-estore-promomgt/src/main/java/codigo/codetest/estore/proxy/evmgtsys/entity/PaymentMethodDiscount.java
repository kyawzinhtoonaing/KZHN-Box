package codigo.codetest.estore.proxy.evmgtsys.entity;

public class PaymentMethodDiscount {

	private String id;

	private String pmethodId;

	private Integer discountPercent;
	
	public PaymentMethodDiscount() { }
	
	public PaymentMethodDiscount(String id, String pmethodId, Integer discountPercent) {
		this.id = id;
		this.pmethodId = pmethodId;
		this.discountPercent = discountPercent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPmethodId() {
		return pmethodId;
	}

	public void setPmethodId(String pmethodId) {
		this.pmethodId = pmethodId;
	}

	public Integer getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Integer discountPercent) {
		this.discountPercent = discountPercent;
	}
}
