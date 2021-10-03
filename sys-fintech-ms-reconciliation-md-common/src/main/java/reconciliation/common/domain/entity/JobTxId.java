package reconciliation.common.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class JobTxId implements Serializable {
	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 8526253742648193507L;

	@Column(name = "col_jid")
	private String jid;

	@Column(name = "col_txid")
	private String txid;

	@Column(name = "col_filename")
	private String filename;

	@Column(name = "col_lineno")
	private Integer lineno;
}
