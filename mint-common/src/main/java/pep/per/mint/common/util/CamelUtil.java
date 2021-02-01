package pep.per.mint.common.util;

public class CamelUtil {

	public static final String TO_DEFAULT = "0";
	public static final String TO_CAMEL = "1";
	public static final String TO_UNDERSCORE = "2";

	public static String conv(String str, String convType) {
		if( convType.equals(TO_CAMEL) ) {
			return convCamel(str);
		} else if( convType.equals(TO_UNDERSCORE) ) {
			return convUnderscore(str);
		} else {
			return str;
		}
	}

	/**
	 * toCamel Conversion
	 * @param underScore
	 * @return
	 */
	public static String convCamel(String underScore) {

		boolean isUnderscoreCheck = false;
		int underScorePosition = underScore.indexOf('_');

		StringBuilder result = new StringBuilder();
		boolean nextUpper = false;
		int len = underScore.length();
		for (int i = 0; i < len; i++) {
			//-----------------------------------------------------------------------
			// 이미 Camel Type 이면 변환하지 않고 Skip
			//-----------------------------------------------------------------------
			if( !isUnderscoreCheck ) {
				//-----------------------------------------------------------------------
				// Type1) 소문자가 하나라도 있으면 CamelType 으로 판단하고 Skip
				//-----------------------------------------------------------------------
				if ( Character.isLowerCase(underScore.charAt(i)) ) {
					//System.out.println("toCamel Skip :: Type1");
					return underScore;
				}
			} else {
				//-----------------------------------------------------------------------
				// Type2) 소문자가 하나라도 있고 '_' 없는경우만 CamelType 으로 판단하고 Skip
				//-----------------------------------------------------------------------
				if (underScorePosition < 0 && Character.isLowerCase(underScore.charAt(i)) ) {
					//System.out.println("toCamel Skip :: Type2");
					return underScore;
				}
			}

			char currentChar = underScore.charAt(i);
			if (currentChar == '_') {
				nextUpper = true;
			} else {
				if (nextUpper) {
					result.append(Character.toUpperCase(currentChar));
					nextUpper = false;
				} else {
					result.append(Character.toLowerCase(currentChar));
				}
			}
		}
		return result.toString();
	}

	/**
	 * toA-Z + Underscore Conversion
	 * @param camelStr
	 * @return
	 */
	public static String convUnderscore(String camelStr) {

		StringBuilder result = new StringBuilder();
		boolean preLower = false;
		boolean nextUpper = false;
		int len = camelStr.length();

		for (int i = 0; i < len; i++) {
			//-----------------------------------------------------------------------
			// SKIP 해야 하는 예외상황 있으면 기술해야하는데, 예외상황을 정리할수 있을까?
			//-----------------------------------------------------------------------

			char currentChar = camelStr.charAt(i);

			if( Character.isLowerCase(currentChar) ) {
				preLower = true;
			}

			if( preLower && Character.isUpperCase(currentChar) ) {
				result.append('_');
				result.append(Character.toUpperCase(currentChar));
				preLower = false;
			} else {
				result.append(Character.toUpperCase(currentChar));
			}
		}

		return result.toString();
	}
}
