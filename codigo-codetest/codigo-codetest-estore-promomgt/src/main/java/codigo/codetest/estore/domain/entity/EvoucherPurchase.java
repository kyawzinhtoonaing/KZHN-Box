package codigo.codetest.estore.domain.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_evoucher_purchase")
public class EvoucherPurchase {

	@Id
	@Column(name = "col_id")
	private String id;
	
	@Column(name = "col_evoucher_def_id")
	private String evoucherDefId;
	
	@Column(name = "col_only_me_usage_count")
	private Integer onlyMeUsageCount;
	
	@Column(name = "col_gift_to_others_count")
	private Integer giftToOthersCount;
	
	@Column(name = "col_paid_method")
	private String paidMethod;
	
	@Column(name = "col_cost")
	private BigDecimal cost;
	
	@Column(name = "col_discount")
	private BigDecimal discount;
	
	@Column(name = "col_purchase_datetime")
	private OffsetDateTime purchaseDatetime;
	
	@Column(name = "col_purchase_by")
	private String purchaseBy;
	
	@OneToMany(mappedBy = "evoucherPurchase", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<EvoucherOwner> evoucherOwners = new HashSet<>();
	
	@OneToMany(mappedBy = "evoucherPurchase", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Evoucher> evouchers = new HashSet<>();
	
	public EvoucherPurchase() { }
	
	public EvoucherPurchase(String id, String evoucherDefId, Integer onlyMeUsageCount, Integer giftToOthersCount,
			String paidMethod, BigDecimal cost, BigDecimal discount, OffsetDateTime purchaseDatetime, String purchaseBy) {
		this.id = id;
		this.evoucherDefId = evoucherDefId;
		this.onlyMeUsageCount = onlyMeUsageCount;
		this.giftToOthersCount = giftToOthersCount;
		this.paidMethod = paidMethod;
		this.cost = cost;
		this.discount = discount;
		this.purchaseDatetime = purchaseDatetime;
		this.purchaseBy = purchaseBy;
	}
	
	public void addEvoucherOwner(EvoucherOwner evoucherOwner) {
		if (evoucherOwners == null) {
			this.evoucherOwners = new HashSet<>();
		}
		this.evoucherOwners.add(evoucherOwner);
		evoucherOwner.setEvoucherPurchase(this);
	}
	
	public void addEvoucher(Evoucher evoucher) {
		if (evouchers == null) {
			this.evouchers = new HashSet<>();
		}
		this.evouchers.add(evoucher);
		evoucher.setEvoucherPurchase(this);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEvoucherDefId() {
		return evoucherDefId;
	}

	public void setEvoucherDefId(String evoucherDefId) {
		this.evoucherDefId = evoucherDefId;
	}

	public Integer getOnlyMeUsageCount() {
		return onlyMeUsageCount;
	}

	public void setOnlyMeUsageCount(Integer onlyMeUsageCount) {
		this.onlyMeUsageCount = onlyMeUsageCount;
	}

	public Integer getGiftToOthersCount() {
		return giftToOthersCount;
	}

	public void setGiftToOthersCount(Integer giftToOthersCount) {
		this.giftToOthersCount = giftToOthersCount;
	}

	public String getPaidMethod() {
		return paidMethod;
	}

	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public OffsetDateTime getPurchaseDatetime() {
		return purchaseDatetime;
	}

	public void setPurchaseDatetime(OffsetDateTime purchaseDatetime) {
		this.purchaseDatetime = purchaseDatetime;
	}

	public String getPurchaseBy() {
		return purchaseBy;
	}

	public void setPurchaseBy(String purchaseBy) {
		this.purchaseBy = purchaseBy;
	}

	public Set<Evoucher> getEvouchers() {
		return evouchers;
	}

	public void setEvouchers(Set<Evoucher> evouchers) {
		this.evouchers = evouchers;
	}

	public Set<EvoucherOwner> getEvoucherOwners() {
		return evoucherOwners;
	}

	public void setEvoucherOwners(Set<EvoucherOwner> evoucherOwners) {
		this.evoucherOwners = evoucherOwners;
	}
}
