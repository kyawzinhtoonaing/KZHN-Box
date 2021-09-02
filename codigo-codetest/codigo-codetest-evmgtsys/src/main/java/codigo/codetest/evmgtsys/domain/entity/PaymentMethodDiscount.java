package codigo.codetest.evmgtsys.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbl_payment_method_discount")
public class PaymentMethodDiscount {

	@Id
	@Column(name = "col_id")
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "col_evoucher_def_id")
	private EvoucherDef evoucherDef;
	
	@Column(name = "col_pmethod_id")
	private String pmethodId;
	
	@Column(name = "col_discount_percent")
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

	public EvoucherDef getEvoucherDef() {
		return evoucherDef;
	}

	public void setEvoucherDef(EvoucherDef evoucherDef) {
		this.evoucherDef = evoucherDef;
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
