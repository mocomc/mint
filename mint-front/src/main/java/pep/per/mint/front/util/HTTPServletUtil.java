package pep.per.mint.front.util;

import javax.servlet.http.HttpServletRequest;

public class HTTPServletUtil {

	/**
	 * 1.프록시를 거쳐서 들어오는 접속의 경우, 정확한 ip정보를 가져올 수 없다. 2. was가 2차 방화벽 안에 있고 클라이언트에서
	 * web server를 통해 호출하는 경우 getRemoteAddr은 진짜 접속한 클라이언트의 ip가 아닌, 웹서버의 ip 정보를
	 * 가져온다. 3. 클러스터로 구성도외 load balancer에서 호출되는 경우도 getRemoteAddr은 load
	 * balancer의 ip를 가져온다. 이걸 해결하기 위해 HTTP Header 를 확인하여 분기를 통해 처리한다. 구글링한 코드를
	 * 참조하여 일반적으로 쓸 수 있는 유틸을 만들어서 사용했다. (proxy나 웹로직 서버의 경우까지 처리할 수 있다)
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getRemoteIP(HttpServletRequest request) throws Exception {

		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}

}
