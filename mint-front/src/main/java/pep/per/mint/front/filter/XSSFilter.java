package pep.per.mint.front.filter;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.util.Util;

/**
 * 20170720
 * 작업하려다 대기중인 소스 다음에 정식으로 적용할 예정
 * 월급날인데 일찍 가자. 응?
 */
public class XSSFilter implements Filter{

	Logger logger = LoggerFactory.getLogger(XSSFilter.class);

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		String contentType = request.getContentType();
		if(!Util.isEmpty(contentType) && contentType.toLowerCase().contains("application/json")){

			BufferedReader reader = null;
			StringBuffer sb = new StringBuffer();
			try{
				reader = request.getReader();
				String line = null;
				while((line = reader.readLine()) != null) sb.append(line);
			}finally{
				if(reader != null) reader.close();
			}
			logger.debug(Util.join("XSSFilter-request-body:" + sb.toString()));
		}
		//filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
