package codigo.codetest.estore.domain.entity;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_evoucher")
public class Evoucher {

	@Id
	@Column(name = "col_promo_code")
	private String promoCode;
	
	@ManyToOne
	@JoinColumn(name = "col_ep_id")
	private EvoucherPurchase evoucherPurchase;
	
	@Column(name = "col_owner_ph_number")
	private String ownerPhNumber;
	
	@Column(name = "col_qr_codefile_location")
	private String qrCodefileLocation;
	
	@Column(name = "col_gen_datetime")
	private OffsetDateTime genDatetime;
	
	@Column(name = "col_expiry_date")
	private OffsetDateTime expiryDate;
	
	public Evoucher() { }
	
	public Evoucher(String promoCode, String ownerPhNumber, String qrCodefileLocation,
			OffsetDateTime genDatetime, OffsetDateTime expiryDate) {
		this.promoCode = promoCode;
		this.ownerPhNumber = ownerPhNumber;
		this.qrCodefileLocation = qrCodefileLocation;
		this.genDatetime = genDatetime;
		this.expiryDate = expiryDate;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
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

	public String getQrCodefileLocation() {
		return qrCodefileLocation;
	}

	public void setQrCodefileLocation(String qrCodefileLocation) {
		this.qrCodefileLocation = qrCodefileLocation;
	}

	public OffsetDateTime getGenDatetime() {
		return genDatetime;
	}

	public void setGenDatetime(OffsetDateTime genDatetime) {
		this.genDatetime = genDatetime;
	}

	public OffsetDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(OffsetDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}
}
