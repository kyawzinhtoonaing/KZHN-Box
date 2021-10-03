package reconciliation.common.domain.entity;

import java.time.OffsetDateTime;

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
@Table(name="tbl_job")
public class Job {
	
	@Id
	@Column(name = "col_id")
	private String id;
	
	@Column(name = "col_file1name")
	private String file1name;
	
	@Column(name = "col_file2name")
	private String file2name;
	
	@Column(name = "col_status")
	private Character status;
	
	@Column(name = "col_creation_datetime")
	private OffsetDateTime creationDatetime;
	
	@Column(name = "col_started_datetime")
	private OffsetDateTime startedDatetime;
	
	@Column(name = "col_finished_datetime")
	private OffsetDateTime finishedDatetime;
}
