package codigo.codetest.evmgtsys.service.evoucherdef;

import java.util.List;

import codigo.codetest.evmgtsys.domain.entity.EvoucherDef;

public class EvoucherDefRetrievalServiceResult {
	private List<EvoucherDef> evoucherDefs;
	private Integer currentPage;
	private Integer pageSize;
	private Integer totalPages;
	private Long totalRecords;
	
	public List<EvoucherDef> getEvoucherDefs() {
		return evoucherDefs;
	}
	public void setEvoucherDefs(List<EvoucherDef> evoucherDefs) {
		this.evoucherDefs = evoucherDefs;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public Long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}
}
