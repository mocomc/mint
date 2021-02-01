package pep.per.mint.batch.job;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Solution TF on 16. 1. 8..
 */
abstract public class JobImpl implements Runnable {

    protected Map params = new HashMap();

    @SuppressWarnings("unchecked")
	public void setParam(String key, Object value){
        params.put(key,value);
    }


}
