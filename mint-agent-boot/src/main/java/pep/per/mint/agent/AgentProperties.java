package pep.per.mint.agent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import pep.per.mint.agent.exception.AgentLauncherException;

public class AgentProperties extends Properties{

	/**
	 *
	 */
	private static final long serialVersionUID = -3714651411193263451L;

	@Override
	public synchronized void load(InputStream inStream) throws IOException {
		super.load(inStream);

		Enumeration<?> properties = keys();
		while(properties.hasMoreElements()){
			String property = (String)properties.nextElement();
			String value = getProperty(property);

			Enumeration<?> keys = keys();
			while(keys.hasMoreElements()){
				String key = (String)keys.nextElement();
				if(value.contains("${" + key + "}")){
					String replacement = getProperty(key);
					String replacementValue = value.replace("${" + key + "}", replacement);
					setProperty(property, replacementValue);
				}
			}

		}
	}

	public String readProperty(String key, boolean required) throws Exception{
		String value = getProperty(key);
		if(required && (value == null || value.trim().length() == 0)){
			throw new AgentLauncherException("classespath:config/agent.properties 파일내에 필드[" + key + "]필수값이 정의되지 않았거나 값이 비어있습니다. 확인해 주세요. 제발");
		}
		return value;
	}
}
