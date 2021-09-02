package codigo.codetest.common.util.rest;

import java.io.Serializable;

public class JsonMsgWithoutData<M> implements Serializable {
	
	/**
	 * Serial version UID for serialization.
	 */
	private static final long serialVersionUID = -3494213410727685967L;
	
	private String statusCode;
	private M statusMsg;
	
	public String getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	public M getStatusMsg() {
		return statusMsg;
	}
	
	public void setStatusMsg(M statusMsg) {
		this.statusMsg = statusMsg;
	}

	public void constructMsg(String statusCode, M statusMsg) {
		this.statusCode = statusCode;
		this.statusMsg = statusMsg;
	}
}
