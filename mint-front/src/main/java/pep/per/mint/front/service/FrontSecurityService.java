package pep.per.mint.front.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.data.basic.security.LoginHistory;
import pep.per.mint.common.util.LogUtil;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.co.SecurityService;
import pep.per.mint.front.env.FrontEnvironments;
import pep.per.mint.front.util.HTTPServletUtil;

@Service
public class FrontSecurityService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private Map<String, HttpSession> sessionMap = new ConcurrentHashMap<String, HttpSession>();

	@Autowired
	CommonService commonService;

	@Autowired
	SecurityService securityService;

	//유효한 세션 개수 확인
	public int getActiveSessionCount(){
		return sessionMap.size();
	}

	//특정 세션 가져오기
	public HttpSession getSession(String checkKey){
		return sessionMap.get(checkKey);
	}

	//특성 세션이 세션맵에 등록되어 있는지 확인
	public Boolean checkOldKey(String checkKey){
		return sessionMap.containsKey(checkKey);
	}

	//세션을 세션맵에 등록
	public void setSession(String checkKey, HttpSession httpSession){
		sessionMap.put(checkKey, httpSession);
	}

	//세션맵에서 특정 키를 제거
	public void removeSession(String checkKey){
		sessionMap.remove(checkKey);
	}


	/**
	 * <pre>
	 * 중복 로그인 체크
	 *
	 * </pre>
	 * @param newSession 세션
	 * @param request the request
	 * @param option 중복 로그인 체크 옵션(1:Id, 2:Id+Ip)
	 * @return boolean 중복 로그인이 있었는지 여부
	 * @author psp
     * @since 2018.10.30
     */
	public boolean checkDuplicationLogin(HttpSession newSession, HttpServletRequest request, String option){
		StringBuffer debugMsg = new StringBuffer();

		if(logger.isDebugEnabled()) {
			LogUtil.bar2(debugMsg,LogUtil.prefix("중복 로그인 체크 로직 [FrontSecurityService.checkDupliactionLogin] 시작"));
		}

		int dupOption = Integer.parseInt(option);
		boolean alreadyLogin = false;
		//-----------------------------------------------
		// 현재 세션에서 User 정보 가져옴
		//-----------------------------------------------
		User user = (User) newSession.getAttribute("user");
		String userId   = "";
		String checkKey = "";
		String clientIp = "";
		String currentDate = Util.getFormatedDate();
		try {
			userId = user.getUserId();
			clientIp = HTTPServletUtil.getRemoteIP(request);

			//-----------------------------------------------
			// New Session에 ip, loginDate 기록
			//-----------------------------------------------
			{
				newSession.setAttribute("ip", clientIp);
				newSession.setAttribute("loginDate", currentDate);
			}

			//-----------------------------------------------
			// 중복 로그인 체크 옵션
			//-----------------------------------------------
			switch (dupOption){
				case 1: // 세션맵 키값으로 userId만 사용
					checkKey = userId;
					break;
				case 2: // 세션맵 키값으로 id@ip 사용
					checkKey = userId + "@" + clientIp;
					break;
				default :
					checkKey = userId;
					break;
			}

			//-------------------------------------------------------------------------------------------
			// 기존 세션 맵에 해당 Key가 존재하는지 확인 ( 기존 세션 맵에 해당 Key가 존재하면 중복 로그인 )
			///--------------------------------------------------------------------------------------------
			alreadyLogin = checkOldKey(checkKey);
			if( alreadyLogin ) {
				//-------------------------------------------------------------------------------------------
				// ▶ 중복 로그인 정보가 있을 경우
				//-------------------------------------------------------------------------------------------
				HttpSession oldSession = getSession(checkKey);

				//-------------------------------------------------------------------------------------------
				// 2018.12.5
				// 크롬, 엣지 등 브라우저에서, 동일 브라우저 다른 탭 로그인 시에 동일 세션으로 처리하는 경우가 있어
				// old와 new 세션이 같은 경우 추가적인 처리 없이 체크 종료
				//-------------------------------------------------------------------------------------------
				if(newSession.getId().equals(oldSession.getId())) {
					if(logger.isDebugEnabled()) {
						LogUtil.prefix(debugMsg, Util.join(checkKey, " // 동일 세션이므로 추가적인 처리 없이 중복 로그인 처리를 스킵합니다. ( sessonId : ", oldSession.getId(), " )") );
					}
					return false;
				}

				if(logger.isDebugEnabled()) {
					LogUtil.prefix(debugMsg, Util.join(checkKey, " // 해당 ID로 기존에 등록된 세션이 있습니다. ( sessonId : ", oldSession.getId(), " )") );
					LogUtil.prefix(debugMsg, Util.join(checkKey, " // 해당 ID로 새 세션이 로그인 합니다. ( newSessonId : ", newSession.getId(), " )") );
				}

				//-----------------------------------------------
				// Old Session 을 로그인 히스토리 테이블에 업데이트
				//-----------------------------------------------
				Map<String, Object> loginParams = new HashMap<String, Object>();
				try{

					loginParams.put("userId",userId);
					loginParams.put("sessionId",oldSession.getId());
					loginParams.put("logoutDate",currentDate);
					//-------------------------------------------------------------------------------------------
					// 기존 로그인 히스토리 테이블에 (TOP0200) 업데이트 ( 로그아웃 정보 )
					//-------------------------------------------------------------------------------------------
					commonService.updateLogoutUser(loginParams);
					//-------------------------------------------------------------------------------------------
					// 신규 로그인 히스토리 테이블에 (TOP0204) 업데이트 ( 로그아웃 정보 )
					//-------------------------------------------------------------------------------------------
					if(FrontEnvironments.historyYn) {
						securityService.updateLoginHistory(loginParams);
					}

				}catch(Exception e){
					logger.error("[무시해도되는예외]로그아웃 사용자 정보 로깅하다 에러 발생!:",e);
				}

				//-------------------------------------------------------------------------------------------
				// Old Session 을 중복로그인세션 정보(LoginHistory) 에 저장 - UI에서 Alert 정보로 참고함
				//-------------------------------------------------------------------------------------------
				LoginHistory duplicationLoginHistory = new LoginHistory();
				duplicationLoginHistory.setUserId(userId);
				duplicationLoginHistory.setSessionId(oldSession.getId());
				duplicationLoginHistory.setLoginDate((String)oldSession.getAttribute("loginDate"));
				duplicationLoginHistory.setLogoutDate((String)loginParams.get("logoutDate"));
				duplicationLoginHistory.setIp((String)oldSession.getAttribute("ip"));

				//-----------------------------------------------
				// Old Session 삭제(순서주의)
				//-----------------------------------------------
				{
					oldSession.invalidate();
					removeSession(checkKey);
					if(logger.isDebugEnabled()) {
						LogUtil.prefix(debugMsg, Util.join(checkKey, " // 해당 ID로 기존에 등록된 세션을 종료했습니다. ( sessonId : ", oldSession.getId(), " )") );
					}
				}

				//-----------------------------------------------
				// New Session 등록(순서주의)
				//-----------------------------------------------
				{
					newSession.setAttribute("checkKey", checkKey);
					newSession.setAttribute("duplicationDetect", true);
					newSession.setAttribute("duplicationLoginHistory", duplicationLoginHistory);
					sessionMap.put(checkKey, newSession);
				}

				return true;
			} else {
				//-------------------------------------------------------------------------------------------
				// ▶ 중복 로그인 정보가 없을 경우
				//-------------------------------------------------------------------------------------------

				if(logger.isDebugEnabled()) {
					LogUtil.prefix(debugMsg, Util.join(checkKey, " // 해당 ID로 기존에 등록된 세션이 없습니다.") );
				}

				//-----------------------------------------------
				// New Session 등록
				//-----------------------------------------------
				{
					newSession.setAttribute("checkKey", checkKey);
					newSession.setAttribute("duplicationDetect", false);
					newSession.setAttribute("duplicationLoginHistory", null);
					sessionMap.put(checkKey, newSession);
				}

				return false;
			}

		} catch(Throwable t) {
			if(logger.isDebugEnabled()) {
				debugMsg.append(LogUtil.bar2(LogUtil.prefix("중복 로그인 체크 로직 중 예외발생")));
				LogUtil.prefix(debugMsg, t.getMessage() );
			}
			return false;
		} finally {
			if(logger.isDebugEnabled()) {
				debugMsg.append(LogUtil.bar2(LogUtil.prefix("중복 로그인 체크 로직 [FrontSecurityService.checkDupliactionLogin] 종료")));
				logger.debug(debugMsg.toString());
			}
		}

	}

}
