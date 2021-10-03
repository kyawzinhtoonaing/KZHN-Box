package reconciliation.common.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_job_report_summary")
public class JobReportSummary {
	@Id
	@Column(name = "col_id")
	private String id;
	
	@Column(name = "col_file1name")
	private String file1name;
	
	@Column(name = "col_file1_line_count")
	private Integer file1LineCount;
	
	@Column(name = "col_file1_match_count")
	private Integer file1MatchCount;
	
	@Column(name = "col_file1_unmatch_count")
	private Integer file1UnmatchCount;
	
	@Column(name = "col_file2name")
	private String file2name;
	
	@Column(name = "col_file2_line_count")
	private Integer file2LineCount;
	
	@Column(name = "col_file2_match_count")
	private Integer file2MatchCount;
	
	@Column(name = "col_file2_unmatch_count")
	private Integer file2UnmatchCount;
	
	@Column(name = "col_all_match")
	private Boolean allMatch;
	
	@Column(name = "col_fm_count")
	private Integer fmCount;
	
	@Column(name = "col_mnmm_count")
	private Integer mnmmCount;
	
	@Column(name = "col_ftmmht_count")
	private Integer ftmmhtCount;
	
	@Column(name = "col_ftmmlt_count")
	private Integer ftmmltCount;
	
	@Column(name = "col_srmmlc_count")
	private Integer srmmlcCount;
	
	@Column(name = "col_fmm_count")
	private Integer fmmCount;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "col_id")
	private Job job;
}
