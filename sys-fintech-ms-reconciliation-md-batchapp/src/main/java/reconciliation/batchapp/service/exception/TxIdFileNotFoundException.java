package reconciliation.batchapp.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TxIdFileNotFoundException extends Exception {
	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 7443472409604438727L;
	
	private String txIdFileName;
}
