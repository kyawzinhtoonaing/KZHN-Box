package codigo.codetest.idp.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_mst_authority")
public class MstAuthority {

	@Id
	@Column(name = "col_authority")
	private String authority;
	
	@Column(name = "col_display_name")
	private String displayName;
}
