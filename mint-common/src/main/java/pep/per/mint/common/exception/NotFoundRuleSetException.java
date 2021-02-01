package pep.per.mint.common.exception;

public class NotFoundRuleSetException extends Exception{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 445950583626610731L; 
	
	Object ruleSetId;
	
	public NotFoundRuleSetException(Object id){
		super("not found RuleSet[id:" + id + "]");
		this.ruleSetId = id;
	}

	public Object getRuleSetId() {
		return ruleSetId;
	}
	
}
