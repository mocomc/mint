package pep.per.mint.common.exception;

public class NotFoundSystemInfoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5491738257869228271L;
	
	Object systemId;

	public NotFoundSystemInfoException(Object systemId) {
		super("not found SystemInfo[id:" + systemId + "]");
		this.systemId = systemId;
	}

	public Object getSystemId() {
		return systemId;
	}
	
	
	

}
