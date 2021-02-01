package pep.per.mint.inhouse.sso.nh;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.inhouse.sso.ILoginService;

public class NHLoginService implements ILoginService{

	static Logger logger = LoggerFactory.getLogger(NHLoginService.class);

	public void init() throws Exception{

	}

	@Override
	public Map<?, ?> login(Map<?, ?> params) throws Exception {
		Map<String,String> res = new HashMap<String,String>();
		String isSso = (String)params.get("isSso");
		if(isSso == null || isSso.equalsIgnoreCase("false")) {
			res.put("errorMsg", "통합 로그인 인증 절차후 재접속 해주세요.");
		} else {
			res.put("errorCd", COMMON_SUCCESS_CD);
		}
		return res;
	}
}