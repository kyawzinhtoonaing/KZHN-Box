package codigo.codetest.estore.domain.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_user_payment_method")
public class UserPaymentMethod {

	@Id
	@Column(name = "col_id")
	private String id;
	
	@Column(name = "col_username")
	private String username;
	
	@Column(name = "col_pmethod_id")
	private String pmethodId;
	
	@Column(name = "col_balance")
	private BigDecimal balance;
	
	public UserPaymentMethod() { }
	
	public UserPaymentMethod(String id, String username, String pmethodId, BigDecimal balance) {
		this.id = id;
		this.username = username;
		this.pmethodId = pmethodId;
		this.balance = balance;
	}

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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}
