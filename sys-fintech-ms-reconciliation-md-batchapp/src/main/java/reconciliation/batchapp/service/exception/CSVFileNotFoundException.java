package reconciliation.batchapp.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CSVFileNotFoundException extends Exception {
	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	private String csvFileName;
}
