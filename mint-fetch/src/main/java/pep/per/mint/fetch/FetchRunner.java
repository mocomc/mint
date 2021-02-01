/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.fetch;

import java.util.Map;

/**
 * <pre>
 * pep.per.mint.fetch
 * FetchRunner.java
 * </pre>
 * @author whoana
 * @date Apr 24, 2019
 */
public interface FetchRunner {

	public void run(Map<String, Object> params) throws Exception;

}
