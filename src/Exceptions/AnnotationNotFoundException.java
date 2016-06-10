package Exceptions;

public class AnnotationNotFoundException extends Exception {

	private static final long serialVersionUID = 3281717010554737857L;
	
	@Override
	public String getMessage() {
		return "Annotatie niet aanwezig";
	}

}
