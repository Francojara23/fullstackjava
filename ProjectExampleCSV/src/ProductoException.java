
public class ProductoException extends Exception {

	public ProductoException(String msgError) {
		super(msgError);
	}

	public ProductoException(String msgError, Throwable errorOriginal) {
		super(msgError, errorOriginal);
	}

}