package pep.per.mint.front.security;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import pep.per.mint.common.util.LogUtil;
import pep.per.mint.common.util.Util;

/**
 * request 를 다시 읽기 위한 목적으로 사용되는 클래스<p>
 */
public class DecryptHttpServletRequest extends HttpServletRequestWrapper {
	
	Logger logger = LoggerFactory.getLogger(DecryptHttpServletRequest.class);
	
	// 읽어들인 문자열.. 
	private String readString = null;
	
	public DecryptHttpServletRequest(HttpServletRequest request) {
		super(request);
		
		try {
			readString = IOUtils.toString(request.getInputStream());
		} catch( IOException e) {
			logger.error("#input read failed.", e);
		}
	
		try {
			StringBuffer sb = new StringBuffer();
			LogUtil.bar2(sb,LogUtil.prefix("DecryptHTTPServletRequest"));
			LogUtil.prefix(sb, "encryptData : " + readString);
			
			
			//----------------------------------------------------------------------------
			// CASE#1. ComMessage 의 개별적인 항목에 대한 복호화시 아래 로직 패턴으로 구현...
			//----------------------------------------------------------------------------
			// convert JSON string to Map
			Map<String,Object> map = Util.jsonToMap(readString);
			
			Iterator<String> keys = map.keySet().iterator();
			while( keys.hasNext() ) {
				String key = keys.next();
				
				// 복호화를 한다. 
				if( key.equals("requestObject") ) {
					String str = RSAKeyManager.decrypt((String)map.get(key));
					map.put(key, Util.jsonToObject(str));
				} 

			}
			// convert map to JSON string
			readString = Util.toJSONString(map);
			
			
			//----------------------------------------------------------------------------
			// ComMessage 의 전체 복호화시 아래 로직 패턴으로 구현...
			//  - 성능이슈도 있고 해서, 로그인시에만 암호화 하는것으로 한다.
			//----------------------------------------------------------------------------
			//readString = RSAKeyManager.decrypt(readString);
			
			LogUtil.prefix(sb, "decryptData : " + readString);
			logger.debug(sb.toString());

		} catch(Exception e){
			logger.error("#input decrypt failed.", e);
		}
	}

	@Override
	public BufferedReader getReader() throws IOException {
		InputStream is = new ByteArrayInputStream(readString.getBytes("utf-8"));
		return new BufferedReader(new InputStreamReader(is));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(readString.getBytes("utf-8"));
	    ServletInputStream servletInputStream = new ServletInputStream(){
	        public int read() throws IOException {
	          return byteArrayInputStream.read();
	        }
	    };
	    
		return servletInputStream;
	}
}
