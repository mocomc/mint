package pep.per.mint.common.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;


/**
 * <pre>
 * com.mocomsys.proj.c4i.util.StringUtil.java
 * </pre>
 *
 * Desc : String Util 클래스이다.<BR>
 * @Date 2018. 3. 29.
 * @Version : 1.0
 */
public class StringUtil {

    /**
     * 사용목적 : Default Constructor.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     */
    private StringUtil() {
    }

    /**
     * 사용목적 : 파라미터의 string을 repeat 만큼 append하여 반환하는 method이다.<BR>
     * 사용법 : repeat("str",3) --> "strstrstr"<BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
      * @param str      repeat할 string
      * @param repeat   repeat 횟수
      * @return String  결과 string
      */
    public static String repeat(String str, int repeat) {
        StringBuffer buffer = new StringBuffer(repeat * str.length());

        for (int i = 0; i < repeat; i++) {
            buffer.append(str);
        }

        return buffer.toString();
    }

    /**
     * 사용목적 : string의 오른쪽에 size만큼 " "를 padding하는 method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     *
     * @param str
     *            string
     * @param size
     *            padding하는 " "의 횟수
     * @return String 결과 string
     */
    public static String rightPad(final String str, final int size) {
        return rightPad(str, size, " ");
    }

    /**
     * 사용목적 : string의 오른쪽에 size 만큼 delim string을 padding하는 method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     * @param str      string
     * @param size     padding 횟수
     * @param delim	   padding string
     * @return String  결과 string
     */
    public static String rightPad(final String str, final int size,
            final String delim) {
        String rtnStr = str;
        int rtnSize = 0;
        if (rtnStr == null) {
            rtnStr = "";
            rtnSize = size / delim.length();
        } else {
            if (rtnStr.getBytes().length > size) {
                try {
                    rtnStr = new String(rtnStr.getBytes(), 0, size, "KSC5601");
                } catch (Exception e) {
                }
            }
            rtnSize = (size - rtnStr.getBytes().length) / delim.length();
        }

        if (rtnSize > 0) {
            rtnStr += repeat(delim, rtnSize);
        }

        return rtnStr;
    }

    /**
     * 사용목적 : string의 왼쪽에 size만큼 " "를 padding하는 method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     *
     * @param str
     *            string
     * @param size
     *            padding하는 " "의 횟수
     * @return String 결과 string
     */
    public static String leftPad(final String str, final int size) {
        return leftPad(str, size, " ");
    }

    /**
     * 사용목적 : string의 왼쪽에 size 만큼 delim string을 padding하는 method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     * @param str      string
     * @param size     padding 횟수
     * @param delim    padding string
     * @return String  결과 string
     */
    public static String leftPad(final String str, final int size,
            final String delim) {
        String rtnStr = str;
        int rtnSize = 0;
        if (str == null) {
            rtnStr = "";
            rtnSize = size / delim.length();
        } else {
            if (str.getBytes().length > size) {
                try {
                    rtnStr = new String(str.getBytes(), 0, size, "KSC5601");
                } catch (Exception e) {
                }
            }
            rtnSize = (size - str.getBytes().length) / delim.length();
        }

        if (rtnSize > 0) {
            rtnStr = repeat(delim, rtnSize) + rtnStr;
        }

        return rtnStr;
    }

    /**
     * 사용목적 : string의 왼쪽에 size 만큼 delim string을 padding하는 method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     * @param str      string
     * @param size     padding 횟수
     * @param padStr    padding string
     * @param defaultValue    문자열이 Null인 경우, 사용할 문자열
     * @return String  결과 string
     */
    public static String leftPad(final String str, final int size,
            final String padStr, final String defaultValue) {
        String rtnStr = str;
        int repeatCnt = 0;
        if (str == null || str.equals("")) {
            rtnStr = defaultValue;
            repeatCnt = (size - rtnStr.getBytes().length) / padStr.length();
        } else {
            if (str.getBytes().length > size) {
                try {
                    rtnStr = new String(str.getBytes(), 0, size, "KSC5601");
                } catch (Exception e) {
                }
            }
            repeatCnt = (size - rtnStr.getBytes().length) / padStr.length();
        }

        if (repeatCnt > 0) {
            rtnStr = repeat(padStr, repeatCnt) + rtnStr;
        }

        return rtnStr;
    }

    /**
     * 사용목적 : string을 ksc5601로 encoding하는 method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     *
     * @param str
     *            string
     * @return String 결과 string
     * @exception UnsupportedEncodingException
     */
    public static String toKo(final String str) throws UnsupportedEncodingException {
        if (str == null) {
            return null;
        }

        String returnStr = null;

        try {
            returnStr = new String(str.getBytes("8859_1"), "KSC5601");
        } catch (UnsupportedEncodingException e) {
            throw e;
        }

        return returnStr;
    }

    /**
     * 사용목적 : string을 8859_1로 encoding하는 method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     *
     * @param str
     *            string
     * @return String 결과 string
     * @exception UnsupportedEncodingException
     */
    public static String toEn(final String str) throws UnsupportedEncodingException {
        if (str == null) {
            return null;
        }

        String returnStr = null;

        try {
            returnStr = new String(str.getBytes("KSC5601"), "8859_1");
        } catch (UnsupportedEncodingException e) {
            throw e;
        }

        return returnStr;
    }

    /**
     * 사용목적 : caspae 문자를 변화하는 method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
      * @param str      string
      * @return String  결과 string
      */
    public static String escape(final String str) {
        final int paramLen = str.length();
        final StringBuffer buffer = new StringBuffer(2 * paramLen);

        for (int i = 0; i < paramLen; i++) {
            final char ch = str.charAt(i);

            // handle unicode
            if (ch > 0xfff) {
                buffer.append("\\u").append(Integer.toHexString(ch));
            } else if (ch > 0xff) {
                buffer.append("\\u0").append(Integer.toHexString(ch));
            } else if (ch > 0x7f) {
                buffer.append("\\u00").append(Integer.toHexString(ch));
            } else if (ch < 32) {
                switch (ch) {
                case '\b':
                    buffer.append('\\');
                    buffer.append('b');

                    break;

                case '\n':
                    buffer.append('\\');
                    buffer.append('n');

                    break;

                case '\t':
                    buffer.append('\\');
                    buffer.append('t');

                    break;

                case '\f':
                    buffer.append('\\');
                    buffer.append('f');

                    break;

                case '\r':
                    buffer.append('\\');
                    buffer.append('r');

                    break;

                default:

                    if (ch > 0xf) {
                        buffer.append("\\u00" + Integer.toHexString(ch));
                    } else {
                        buffer.append("\\u000" + Integer.toHexString(ch));
                    }

                    break;
                }
            } else {
                switch (ch) {
                case '\'':
                    buffer.append('\\');
                    buffer.append('\'');

                    break;

                case '"':
                    buffer.append('\\');
                    buffer.append('"');

                    break;

                case '\\':
                    buffer.append('\\');
                    buffer.append('\\');

                    break;

                default:
                    buffer.append(ch);

                    break;
                }
            }
        }

        return buffer.toString();
    }

    /**
     * 사용목적 : string을 대문자로 변환하는 method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
      * @param str      string
     * @return String  결과 strings
     */
    public static String capitalize(final String str) {
        if (str == null) {
            return null;
        }

        if (str.length() == 0) {
            return "";
        }

        return new StringBuffer(str.length()).append(Character.toTitleCase(
                                                             str.charAt(0)))
                                             .append(str.substring(1))
                                             .toString();
    }

    /**
     * 사용목적 : string reverse method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     * @param str      string
     * @return String  결과 string
     */
    public static String reverse(String str) {
        if (str == null) {
            return null;
        }

        return new StringBuffer(str).reverse().toString();
    }

    /**
     * 사용목적 : 대문자를 소문자로, 소문자를 대문자로 변화하는 method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     * @param str      string
     * @return String  결과 string
     */
    public static String swapCase(String str) {
        if (str == null) {
            return null;
        }

        int sz = str.length();
        StringBuffer buffer = new StringBuffer(sz);

        boolean whitespace = false;
        char ch = 0;
        char tmp = 0;

        for (int i = 0; i < sz; i++) {
            ch = str.charAt(i);

            if (Character.isUpperCase(ch)) {
                tmp = Character.toLowerCase(ch);
            } else if (Character.isTitleCase(ch)) {
                tmp = Character.toLowerCase(ch);
            } else if (Character.isLowerCase(ch)) {
                if (whitespace) {
                    tmp = Character.toTitleCase(ch);
                } else {
                    tmp = Character.toUpperCase(ch);
                }
            } else {
                tmp = ch;
            }

            buffer.append(tmp);
            whitespace = Character.isWhitespace(ch);
        }

        return buffer.toString();
    }

    /**
     * 사용목적 : 숫자 string인지를 check하는 method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     * @param str       string
     * @return boolean  숫자 string 여부
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }

        int sz = str.length();

        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }

        return true;
    }

    /**
     * 사용목적 : string이 숫자 or ' '인지를 check하는 method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     * @param str       string
     * @return boolean  숫자 여부
     */
    public static boolean isNumericSpace(String str) {
        if (str == null) {
            return false;
        }

        int sz = str.length();

        for (int i = 0; i < sz; i++) {
            if ((Character.isDigit(str.charAt(i)) == false) &&
                    (str.charAt(i) != ' ')) {
                return false;
            }
        }

        return true;
    }

    /**
     * 사용목적 : 파라미터의 string이 "on", "true", "yes" 이면 true,
     * 아니면 false를 반환하는 method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     * @param str       string
     * @return boolean
     */
    public static boolean isTrue(String str) {
        if ("true".equalsIgnoreCase(str)) {
            return true;
        } else if ("on".equalsIgnoreCase(str)) {
            return true;
        } else if ("yes".equalsIgnoreCase(str)) {
            return true;
        }

        return false;
    }

    /**
     * 사용목적 : 문자열을 받아서 널이면 제로스트링을, 아니면 트림된 문자열을 리턴하는 method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     * @param str      변환 대상
     * @return String
     */
    public static String checkNull( String str ) {
        String rtnStr = str;
        if (rtnStr == null ) {
            rtnStr = "";
        }
        return rtnStr;
    }


    /**
     * 사용목적 : 문자열을 받아서 널이면 rep을, 아니면 트림된 문자열을 리턴하는 method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     * @param str		변환 대상
     * @param rep		변환 대상
     * @return String
     */
    public static String checkNull( String str, String rep ) {

        String sResult = checkNull(str);
        if (sResult.length() < 1 ) {
            sResult = rep;
        }
        return sResult;
    }




    /**
     * 사용목적 : 에러대신 Zero String을 리턴하는 substring method이다.<BR>
     * 사용법 : getSubstring("1234",4,2) --> ""<BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     * @param    str 	 String Source
     * @param    start    start index.
     * @param    len      length
     * @return   String
     */
    public static String getSubstring(String str, int start, int len)
    {
        if (str == null) return "";
        int slen = str.length();

        if ((slen < 1) || (start<0) || (len < 1)) return "";

        if ((slen-1) < start) return "";

        if (slen < (start+len)) {
            return str.substring(start,str.length());
        }
        else {
            return str.substring(start,start+len);
        }
    }

    /**
     * 사용목적 : int값을 String으로 변환하는 method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     * @param i        String으로 변환될 int 값.
     * @return String  변환된 String 값.
     */
    public static String itos(int i)
    {
        return Integer.valueOf(i).toString();
    }

    /**
     * 사용목적 : long값을 String으로 변환하는 method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     * @param l        String으로 변환될 long 값.
     * @return String  변환된 String 값.
     */
    public static String ltos(long l)
    {
        return Long.valueOf(l).toString();
    }


   /**
     * 사용목적 : 금액표시타입을 금액문자열로 변환하는 method이다.<BR>
     * 사용법 : makeNoMoneyType("12,345,678",",") --> 12345678 <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     * @param moneyString  금액표시문자열.
     * @param delimeter    금액표시구분자.
     * @return String      금액문자열.
     */
    public static String makeNoMoneyType(String moneyString, String delimeter)
    {
        StringTokenizer st = new StringTokenizer(moneyString,delimeter);
        String out = "";
        String temp = null;
        while (st.hasMoreTokens()) {
            temp = st.nextToken();
            out = out + temp;
        }
        return out;
    }
    /**
     * 사용목적 : String을 int값으로 변환하는 method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     * @param str   int값으로 변환될 String문자열.
     * @return int  변환된 int 값.
     */
    public static int stoi(String str)
    {
        if (str == null ) {
            return 0;
        }
        return (Integer.valueOf(str).intValue());
    }

    /**
     * 사용목적 : String을 Long값으로 변환하는 method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     * @param str    Long값으로 변환될 String문자열.
     * @return long  변환된 long 값.
     */
    public static long stol(String str)
    {
        long rtn_value = 0l;

        if (str == null || str.trim().equals("")) {
            return 0;
        }
        try {
            rtn_value = Long.valueOf(str).longValue();
        } catch(Exception e) {;}

        return rtn_value;
    }

    /**
     * 사용목적 :String을 Float값으로 변환하는 method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     * @param str     Float값으로 변환될 String문자열.
     * @return float  변환된 float 값.
     */
    public static float stof(String str)
    {
        float rtn_value = 0f;
        if (str == null ) {
            return 0;
        }
        try {
            rtn_value = Float.valueOf(str).floatValue();
        } catch(Exception e) {;}

        return rtn_value;
    }

    /**
     * 사용목적 : String을 double 값으로 변환하는 method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     * @param str      Double 값으로 변환될 String문자열.
     * @return double  변환된 double 값.
     */
    public static double stod(String str)
    {
        if (str == null || str.trim().equals("")) {
            return 0;
        }
        return (Double.valueOf(str).doubleValue());
    }

    /**
     * 사용목적 : delimeter에 의해서 구성된 String을 String[]로 변환하는 method이다.<BR>
     * 사용법 : strToStrArray("12:23",":") --> String[0] = "12", String[1] = 23 <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     * @param str        Delimeter를 포함한 String
     * @param delimeter  구분자.
     * @return String[]
     */
    public static String[] strToStrArray(String str, String delimeter)
    {
        StringTokenizer st = new StringTokenizer(str,delimeter);
        Vector<String> vt = new Vector<String>();

        String temp = null;
        // 끊어서 Vector에 집어넣는다.
        while (st.hasMoreTokens()) {
            temp = st.nextToken();
            vt.addElement(temp);
        }

        String[] out = new String[vt.size()];

        // 집어넣은 data를 String[]로 변환한다.
        for ( int i = 0; i < vt.size() ; i ++ ) {
            out[i] = (String)vt.elementAt(i);
        }

        return out;
    }


    public static List<String> strToArrayList(String str, String delimeter)
    {
        StringTokenizer st = new StringTokenizer(str,delimeter);
        ArrayList<String> vt = new ArrayList<String>();

        String temp = null;
        // 끊어서 Vector에 집어넣는다.
        while (st.hasMoreTokens()) {
            temp = st.nextToken();
            vt.add(temp);
        }
        return vt;
    }


    /**
     * 사용목적 : String[]를 delimeter에 의해서 구성된 String로 변환하는 method이다.<BR>
     * 사용법 : str[0] = "12", str[1] = "23", strArrayToStr(str,":") --> "12:23"<BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     * @param str        변환할 String[]
     * @param delimeter  구분자.
     * @return String
     */
    public static String strArrayToStr(String[] str, String delimeter)
    {
        String out = "";

        for(int i=0; i<str.length; i++) {
            out += str[i];
            out += delimeter;
        }
        out = out.substring(0,out.length()-1);

        return out;
    }


   /**
     * 사용목적 : 문자열 replace하는 method이다.<BR>
     * 사용법 : <BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     * @param text         text
     * @param start	start  시작 index
     * @param src          바뀔문자열
     * @param dest         새문자열
     * @return String      결과 문자열
     */
    public static String replaceAll( String text, final int start, String src, String dest )
    {
        if (text==null ) return null;
        if (src==null || dest==null )	return text;

        int textlen = text.length();
        int srclen	= src.length();
        int diff	= dest.length() - srclen;
        int d = 0;

        StringBuffer t = new StringBuffer( text );

        int adjStart = start;
        while (adjStart < textlen) {
            adjStart = text.indexOf(src, adjStart);
            if (adjStart < 0)
                break;
            t.replace(adjStart + d, adjStart + d + srclen, dest);
            adjStart += srclen;
            d += diff;
        }
        return t.toString();
    }

   /**
     * 사용목적 : 문자열을 금액표시타입로 변환하는 method이다.<BR>
     * 사용법 : makeMoneyType("100000") --> "100,000.00"<BR>
     * 구현 설명 : <BR>
     * 유효 리턴 값 : <BR>
     * 유효 인자 값 : <BR>
     * @param moneyString  변환할 숫자형 문자열
     * @return String      결과 문자열
     */
    public static String makeMoneyType(String moneyString) {
        if ((moneyString == null) || (moneyString.length() == 0)) return "";
        double dval = (new Double(moneyString)).doubleValue();
        DecimalFormat df = new DecimalFormat("###,###,###,###,##0.##");
        if (dval == 0) return "";
        else return df.format(dval);
    }

    /**
     * 사용목적 : 문자열이 널이거나 공백문자이면 true를 반환한다.
     * @param str 문자열
     * @return boolean 결과
     */
    public static boolean isNullOrWhiteSpace(String str) {
        if(str == null)
            return true;

        for(int i=0; i<str.length(); i++)
            if(!Character.isWhitespace(str.charAt(i)))
                return false;

        return true;
    }


    public static String getDate(String format) {
        Calendar rightNow = Calendar.getInstance();
        SimpleDateFormat formatter = null;
        formatter = new SimpleDateFormat(format);
        return formatter.format(rightNow.getTime());
    }

    public static int randomRange(int n1, int n2) {
        return (int) (Math.random() * (n2 - n1 + 1)) + n1;
    }

    public static byte[] intToByte(int value) {
        return new byte[] { (byte)(value >>>24) ,(byte)(value >>>16) ,(byte)(value >>>8) ,(byte)value};
    }

    public static int byteToInt(byte[] value) {
        return (value[0] <<24) + ( (value[1] &0xFF) <<16 ) + ( (value[2] &0xFF) <<8 ) + (value[3] &0xFF);
    }

    public static String removeLastChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        if(s.endsWith(".")){
             return s.substring(0, s.length()-1);
        }else{
            return s;
        }
    }
}
