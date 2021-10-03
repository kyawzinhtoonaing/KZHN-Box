package reconciliation.common.domain.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_job_tx_broken_integrity_report")
public class JobTxBrokenIntegrityReport {
	@EmbeddedId
	JobTxId jobTxId;
	
	@Column(name = "col_pf_name")
	private String pfName;
	
	@Column(name = "col_tx_date")
	private String txDate;
	
	@Column(name = "col_tx_amt")
	private String txAmt;
	
	@Column(name = "col_tx_nrtive")
	private String txNrtive;
	
	@Column(name = "col_tx_dscpt")
	private String txDscpt;
	
	@Column(name = "col_tx_type")
	private String txType;
	
	@Column(name = "col_wallet_ref")
	private String walletRef;
	
	@Column(name = "col_counterpart_lineno")
	private Integer counterpartLineno;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("jid")
	@JoinColumn(name = "col_jid")
	private Job job;
}
