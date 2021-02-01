package pep.per.mint.front.filter;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import pep.per.mint.common.util.Util;

/**
 * HTTP Request 에 대한 Filtering Handle Class
 * HTTP Request -> Filter -> Interceptor -> DispatcherServlet -> Controller
 * TODO : 서비스 호출전 사전 체크사항 도출후 지속적 보완
 * @author IIP-DEV
 *
 */
public class CommonInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		boolean flag = true;
		//-------------------------------------------------------------------------------------------
		//Step 1) AccessControl Call
		//-------------------------------------------------------------------------------------------
		flag = accessControl(request, response, handler);
		return flag;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 로그인 사용자별 App 접근권한 체크 수행
	 * [임시버전-20200520]
	 * - NH농협 보안취약점 대응수준으로 작성된 부분으로, 향후 AccessControl 표준 정리후 보완 필요
	 * - TODO : AccessControl 내용 보완
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 */
	private boolean accessControl(HttpServletRequest request, HttpServletResponse response, Object handler) {
		boolean flag = true;
		try {
			if( handler instanceof HandlerMethod ) {
				//TODO
			} else {
				String requestURI = request.getRequestURI();
				//-------------------------------------------------------------------------------------------
				// Login 시점에, RestirctAccess Application List Setting
				//-------------------------------------------------------------------------------------------
				HttpSession httpSession = request.getSession();
				Object acObj = httpSession.getAttribute("restrictAccessAppList");
				Map<String,String> restrictAccessAppList = null;
				if( acObj != null ) {
					restrictAccessAppList = (Map) acObj;
				}

				if( restrictAccessAppList != null ) {
					//-------------------------------------------------------------------------------------------
					// IIP 페이지 기준으로 체크, Access 금지 페이지 접근시도시 차단
					// - 403 이나 Exception throw 하지 않고, 404로 처리
					// - TODO : 하드코딩 부분 보완
					//-------------------------------------------------------------------------------------------
					if( requestURI.startsWith( request.getContextPath() +   "/view/sub-") ) {
						if(requestURI.endsWith(".html") || requestURI.endsWith(".jsp")) {

							requestURI = Util.replaceString(requestURI, ".html", "");
							requestURI = Util.replaceString(requestURI, ".jsp", "");

							if( restrictAccessAppList.containsKey(requestURI) ) {
								response.sendRedirect( request.getContextPath() + "/view/main/404.jsp");
								flag = true;
							}
						}
					}
				} else {
					//-------------------------------------------------------------------------------------------
					// Null 이면 Login 수행되지 않은 시점으로, 세션 체크부분에서 오류 핸들링
					//-------------------------------------------------------------------------------------------
				}
			}
		} catch(Exception e) {
			flag = false;
			//TODO
		}
		return flag;
	}

}
