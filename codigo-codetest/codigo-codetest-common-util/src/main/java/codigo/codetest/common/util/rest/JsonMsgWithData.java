package codigo.codetest.common.util.rest;

import java.io.Serializable;

public class JsonMsgWithData<M, T> implements Serializable {
	
	/**
	 * Serial version UID for serialization.
	 */
	private static final long serialVersionUID = -7677353641365615962L;
	
	private String statusCode;
	private M statusMsg;
	private T data;
	
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
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}

	public void constructMsg(String statusCode, M statusMsg, T data) {
		this.statusCode = statusCode;
		this.statusMsg = statusMsg;
		this.data = data;
	}
}
