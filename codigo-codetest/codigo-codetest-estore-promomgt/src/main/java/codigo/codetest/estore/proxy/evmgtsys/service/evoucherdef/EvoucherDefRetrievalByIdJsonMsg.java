package codigo.codetest.estore.proxy.evmgtsys.service.evoucherdef;

public class EvoucherDefRetrievalByIdJsonMsg {

	private String statusCode;
	private String statusMsg;
	private EvoucherDefRetrievalByIdRestControllerResult data;
	
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public EvoucherDefRetrievalByIdRestControllerResult getData() {
		return data;
	}
	public void setData(EvoucherDefRetrievalByIdRestControllerResult data) {
		this.data = data;
	}
}
