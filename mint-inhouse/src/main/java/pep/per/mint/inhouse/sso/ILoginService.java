package pep.per.mint.inhouse.sso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ILoginService {

	public Map<?,?> login(Map<?,?> params) throws Exception;

	public final static List<String> ERROR_CDS = new ArrayList<String>();

	public final static String COMMON_SUCCESS_CD = "0";

}
