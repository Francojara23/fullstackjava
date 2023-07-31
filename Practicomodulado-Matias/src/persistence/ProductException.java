package persistence;

public class ProductException extends Exception {
	private static final long serialVersionUID = 01L;

	public ProductException(String msgError) {
		super(msgError);
	}

	public ProductException(String msgError, Throwable errorOriginal) {
		super(msgError, errorOriginal);
	}

}