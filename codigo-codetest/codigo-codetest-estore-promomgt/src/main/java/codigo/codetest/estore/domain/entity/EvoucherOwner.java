package codigo.codetest.estore.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_evoucher_owner")
public class EvoucherOwner {

	@Id
	@Column(name = "col_id")
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "col_ep_id")
	private EvoucherPurchase evoucherPurchase;
	
	@Column(name = "col_owner_ph_number")
	private String ownerPhNumber;
	
	@Column(name = "col_evoucher_count")
	private Integer evoucherCount;
	
	@Column(name = "col_for_self")
	private Boolean forSelf;
	
	public EvoucherOwner() { }
	
	public EvoucherOwner(String id, String ownerPhNumber, Integer evoucherCount, Boolean forSelf) {
		this.id = id;
		this.ownerPhNumber = ownerPhNumber;
		this.evoucherCount = evoucherCount;
		this.forSelf = forSelf;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EvoucherPurchase getEvoucherPurchase() {
		return evoucherPurchase;
	}

	public void setEvoucherPurchase(EvoucherPurchase evoucherPurchase) {
		this.evoucherPurchase = evoucherPurchase;
	}

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
