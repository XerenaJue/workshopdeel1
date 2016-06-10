package Exceptions;

public class DataTypeNotSupportedException extends Exception {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "Dit data type wordt niet ondersteund";		
	}

}
