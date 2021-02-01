package pep.per.mint.common.resource;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import pep.per.mint.common.util.Util;

public class ExceptionResource {

	static ResourceBundle bundle = null;
	static{
		try{
			bundle = ResourceBundle.getBundle("pep.per.mint.common.resource.ExceptionMessages", new Locale("ko",""));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String getString(String key){
		try{
			if(bundle == null) return null;
			return bundle.getString(key);
		}catch(Exception e){
			return Util.join("Undefined Bundle Message[",key,"]");
		}
	}

	
	public static String getString(String key, Object...arguments){
		try{
			if(bundle == null) return null;
			return MessageFormat.format(bundle.getString(key), arguments);
		}catch(Exception e){
			return Util.join("Undefined Bundle Message[",key,"]");
		}
	}
}
