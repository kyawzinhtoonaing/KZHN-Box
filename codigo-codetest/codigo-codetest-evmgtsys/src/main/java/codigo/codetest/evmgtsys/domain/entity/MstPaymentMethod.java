package codigo.codetest.evmgtsys.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_mst_payment_method")
public class MstPaymentMethod {

	@Id
	@Column(name = "col_id")
	private String id;
	
	@Column(name = "col_name")
	private String name;
	
	@Column(name = "col_description")
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
