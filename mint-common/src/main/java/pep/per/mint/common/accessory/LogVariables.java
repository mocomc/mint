package pep.per.mint.common.accessory;

import pep.per.mint.common.util.Util;


public class LogVariables {

	
	public static String logPrefix = "# ";
	
	public static String logSerperator2 = logSerperator("=",80);
	
	public static String logSerperator1 = logSerperator("-",80);
	
	public static String logSerperator(String seperatorChar, int repeatCount){
		String seperator = Util.join("#", Util.repeat(seperatorChar,repeatCount));
		return seperator;
	}
}
