package serie2.grupo2.testFw;

/**
 * Thrown when an assertion failed.
 */
public class AssertionFailedError extends AssertionError {

	private static final long serialVersionUID= 1L;

	public AssertionFailedError() {
	}

	public AssertionFailedError(String message) {
		super(message);
	}
}