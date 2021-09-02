package codigo.codetest.evmgtsys.endpoint.controller.rest.evoucherdef;

import java.math.BigDecimal;
import java.util.List;

public class EvoucherDefModificationRestControllerParam {
	
	private EvoucherDef evoucherDef;

	public EvoucherDef getEvoucherDef() {
		return evoucherDef;
	}

	public void setEvoucherDef(EvoucherDef evoucherDef) {
		this.evoucherDef = evoucherDef;
	}

	public static class EvoucherDef {
		private String id;
		private String title;
		private String description;
		private String expiryDateStr;
		private String imageLocation;
		private BigDecimal amount;
		private Integer quantity;
		private Character buyingType;
		private Integer maxEvoucherLimit;
		private Integer giftPerUserLimit;
		private List<PaymentMethodDiscount> paymentMethodDiscounts;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getExpiryDateStr() {
			return expiryDateStr;
		}
		public void setExpiryDateStr(String expiryDateStr) {
			this.expiryDateStr = expiryDateStr;
		}
		public String getImageLocation() {
			return imageLocation;
		}
		public void setImageLocation(String imageLocation) {
			this.imageLocation = imageLocation;
		}
		public BigDecimal getAmount() {
			return amount;
		}
		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}
		public Integer getQuantity() {
			return quantity;
		}
		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}
		public Character getBuyingType() {
			return buyingType;
		}
		public void setBuyingType(Character buyingType) {
			this.buyingType = buyingType;
		}
		public Integer getMaxEvoucherLimit() {
			return maxEvoucherLimit;
		}
		public void setMaxEvoucherLimit(Integer maxEvoucherLimit) {
			this.maxEvoucherLimit = maxEvoucherLimit;
		}
		public Integer getGiftPerUserLimit() {
			return giftPerUserLimit;
		}
		public void setGiftPerUserLimit(Integer giftPerUserLimit) {
			this.giftPerUserLimit = giftPerUserLimit;
		}
		public List<PaymentMethodDiscount> getPaymentMethodDiscounts() {
			return paymentMethodDiscounts;
		}
		public void setPaymentMethodDiscounts(List<PaymentMethodDiscount> paymentMethodDiscounts) {
			this.paymentMethodDiscounts = paymentMethodDiscounts;
		}
	}
	
	public static class PaymentMethodDiscount {
		private String id;
		private String pmethodId;
		private Integer discountPercent;
		
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
}
