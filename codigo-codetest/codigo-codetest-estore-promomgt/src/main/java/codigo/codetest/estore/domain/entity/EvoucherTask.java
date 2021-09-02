package codigo.codetest.estore.domain.entity;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_evoucher_task")
public class EvoucherTask {

	@Id
	@Column(name = "col_eo_id")
	private String eoId;
	
	@Column(name = "col_ep_id")
	private String epId;
	
	@Column(name = "col_is_task_complete")
	private Boolean isTaskComplete;
	
	@Column(name = "col_creation_datetime")
	private OffsetDateTime creationDatetime;
	
	@Column(name = "col_complete_datetime")
	private OffsetDateTime completeDatetime;
	
	public EvoucherTask() { }
	
	public EvoucherTask(String eoId, String epId, Boolean isTaskComplete,
			OffsetDateTime creationDatetime, OffsetDateTime completeDatetime) {
		this.eoId = eoId;
		this.epId = epId;
		this.isTaskComplete = isTaskComplete;
		this.creationDatetime = creationDatetime;
		this.completeDatetime = completeDatetime;
	}

	public String getEoId() {
		return eoId;
	}

	public void setEoId(String eoId) {
		this.eoId = eoId;
	}

	public String getEpId() {
		return epId;
	}

	public void setEpId(String epId) {
		this.epId = epId;
	}

	public Boolean getIsTaskComplete() {
		return isTaskComplete;
	}

	public void setIsTaskComplete(Boolean isTaskComplete) {
		this.isTaskComplete = isTaskComplete;
	}

	public OffsetDateTime getCreationDatetime() {
		return creationDatetime;
	}

	public void setCreationDatetime(OffsetDateTime creationDatetime) {
		this.creationDatetime = creationDatetime;
	}

	public OffsetDateTime getCompleteDatetime() {
		return completeDatetime;
	}

	public void setCompleteDatetime(OffsetDateTime completeDatetime) {
		this.completeDatetime = completeDatetime;
	}
}
