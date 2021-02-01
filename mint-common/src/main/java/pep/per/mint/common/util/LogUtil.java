/**
 * Copyright 2013 ~ 2015 Mocomsys's guys(Minhwoa Bak, Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 
 * 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다. 
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
 */
package pep.per.mint.common.util;
/**
 * @author Solution TF
 *
 */
public class LogUtil {
	
	public static final String bar = "\n------------------------------------------------------------------------------------------------------------------";
	public static final String bar2 = "\n==================================================================================================================";
	public static final String prefix = "\n> ";
	
	/**
	 * 내용을 append 한다. 
	 * @param msgBuffer
	 * @param msgs
	 * @return
	 */
	public static StringBuffer append(StringBuffer msgBuffer, Object... msgs) {
		if(msgBuffer == null) return null;
		try {
			for (Object msg : msgs) {
				msgBuffer.append(msg);
			}
			return msgBuffer;
		} catch (Exception e) {
			e.printStackTrace();
			return msgBuffer;
		} finally {
		}
	}
	
	/**
	 * 첫행에 첫열에 prefix를 넣어준다. 
	 * @param msgBuffer
	 * @param msgs
	 * @return
	 */
	public static StringBuffer prefix(StringBuffer msgBuffer, Object... msgs) {
		if(msgBuffer == null) return null;
		try {
			msgBuffer.append(prefix);
			for (Object msg : msgs) {
				msgBuffer.append(msg);
			}
			return msgBuffer;
		} catch (Exception e) {
			e.printStackTrace();
			return msgBuffer;
		} finally {
		}
	}
	
	
	public static StringBuffer prefix(Object... msgs) {
		StringBuffer msgBuffer = new StringBuffer();
		try {
			msgBuffer.append(prefix);
			for (Object msg : msgs) {
				msgBuffer.append(msg);
			}
			return msgBuffer;
		} catch (Exception e) {
			e.printStackTrace();
			return msgBuffer;
		} finally {
		}
	}
	

	/**
	 * 첫번째 2바를 붙이고 , 마지막행에 1바를 붙인다. 
	 * 
	 * @param msgBuffer
	 * @param msgs
	 * @return
	 */
	public static StringBuffer bar2(StringBuffer msgBuffer, Object... msgs) {
		if(msgBuffer == null) return null;
		try {
			msgBuffer.insert(0, bar2);
			for (Object msg : msgs) {
				msgBuffer.append(msg);
			}
			msgBuffer.append(bar);
			return msgBuffer;
		} catch (Exception e) {
			e.printStackTrace();
			return msgBuffer;
		} finally {
		}
	}
	
	
	/**
	 * 첫행, 마지막행에 바를 붙인다. 
	 * @param msgBuffer
	 * @param msgs
	 * @return
	 */
	public static StringBuffer bar(StringBuffer msgBuffer, Object... msgs) {
		if(msgBuffer == null) return null;
		try {
			msgBuffer.insert(0, bar);
			for (Object msg : msgs) {
				msgBuffer.append(msg);
			}
			msgBuffer.append(bar);
			return msgBuffer;
		} catch (Exception e) {
			e.printStackTrace();
			return msgBuffer;
		} finally {
		}
	}
	
	public static StringBuffer bar(Object... msgs) {
		StringBuffer msgBuffer = new StringBuffer();
		try {
			msgBuffer.insert(0, bar);
			for (Object msg : msgs) {
				msgBuffer.append(msg);
			}
			msgBuffer.append(bar);
			return msgBuffer;
		} catch (Exception e) {
			e.printStackTrace();
			return msgBuffer;
		} finally {
		}
	}
	
	/**
	 * 첫행에만 바를 붙인다 .
	 * @param msgBuffer
	 * @param msgs
	 * @return
	 */
	public static StringBuffer prebar(StringBuffer msgBuffer, Object... msgs) {
		if(msgBuffer == null) return null;
		try {
			msgBuffer.insert(0, bar);
			for (Object msg : msgs) {
				msgBuffer.append(msg);
			}
			return msgBuffer;
		} catch (Exception e) {
			e.printStackTrace();
			return msgBuffer;
		} finally {
		}
	}
	
	/**
	 * 마지막행에만 바를 붙인다. 
	 * @param msgBuffer
	 * @param msgs
	 * @return
	 */
	public static StringBuffer postbar(StringBuffer msgBuffer, Object... msgs) {
		if(msgBuffer == null) return null;
		try {
			return append(msgBuffer, msgs).append(bar);
		} catch (Exception e) {
			e.printStackTrace();
			return msgBuffer;
		} finally {
		}
	}
	
	/**
	 * 마지막행에만 바를 붙인다. 
	 * @param msgBuffer
	 * @return
	 */
	public static StringBuffer postbar(StringBuffer msgBuffer) {
		if(msgBuffer == null) return null;
		try {
			return msgBuffer.append(bar);
		} catch (Exception e) {
			e.printStackTrace();
			return msgBuffer;
		} finally {
		}
	}
	
}
