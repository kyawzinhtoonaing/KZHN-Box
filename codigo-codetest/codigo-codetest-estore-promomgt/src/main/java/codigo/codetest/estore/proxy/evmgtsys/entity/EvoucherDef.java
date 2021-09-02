package codigo.codetest.estore.proxy.evmgtsys.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

public class EvoucherDef {
	
	public static final char BUYING_TYPE_ZERO = '0';
	public static final char BUYING_TYPE_ONE = '1';

	private String id;

	private String title;

	private String description;

	private OffsetDateTime expiryDate;

	private String imageLocation;

	private BigDecimal amount;

	private Integer quantity;
	
	private Character buyingType;
	
	private Integer maxEvoucherLimit;
	
	private Integer giftPerUserLimit;

	private Boolean isactive;

	private String createdBy;

	private OffsetDateTime createdOn;

	private String modifiedBy;

	private OffsetDateTime modifiedOn;
	
	private Set<PaymentMethodDiscount> paymentMethodDiscounts = new HashSet<>();
	
	public EvoucherDef() { }
	
	public EvoucherDef(String id, 
			String title, 
			String description, 
			OffsetDateTime expiryDate, 
			String imageLocation, 
			BigDecimal amount, 
			Integer quantity,
			Character buyingType,
			Integer maxEvoucherLimit,
			Integer giftPerUserLimit,
			Boolean isactive,
			String createdBy,
			OffsetDateTime createdOn,
			String modifiedBy,
			OffsetDateTime modifiedOn) {
		
		this.id = id;
		this.title = title;
		this.description = description;
		this.expiryDate = expiryDate;
		this.imageLocation = imageLocation;
		this.amount = amount;
		this.quantity = quantity;
		this.buyingType = buyingType;
		this.maxEvoucherLimit = maxEvoucherLimit;
		this.giftPerUserLimit = giftPerUserLimit;
		this.isactive = isactive;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.modifiedBy = modifiedBy;
		this.modifiedOn = modifiedOn;
	}

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

	public OffsetDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(OffsetDateTime expiryDate) {
		this.expiryDate = expiryDate;
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

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public OffsetDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(OffsetDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public OffsetDateTime getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(OffsetDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Set<PaymentMethodDiscount> getPaymentMethodDiscounts() {
		return paymentMethodDiscounts;
	}

	public void setPaymentMethodDiscounts(Set<PaymentMethodDiscount> paymentMethodDiscounts) {
		this.paymentMethodDiscounts = paymentMethodDiscounts;
	}
}
