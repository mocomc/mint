package pep.per.mint.front.security;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.apache.xmlbeans.impl.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.util.LogUtil;

public class RSAKeyManager {

	private static String MODE = "RSA/ECB/OAEPwithMD5andMGF1Padding";

	private final String pubKey_1024 = "AQAB";
	private final String pubKey_2048 = "AQAB";
	private final String pubKey_4096 = "AQAB";

	private final String priKey_1024 = "CW3ivni6zs1/55fBaeTnYShPbB4mBxujxBYGoUGXrcM60HI92jBJFBWZ+dGGYtrJBua9pnLB9ObE4U72Y0IsUELPgzlWUHgcqrnUNhAqSs8WrmRU6vTv6MW5zFqMx2v1s1/SQBTlyvlkzP/k8pYq4mxplXU2ELr+kTRcLk/sqGE=";
	private final String priKey_2048 = "HiZ4hDaUzhSrG6Ovrt7D22oL2z2HVcj+XnmCdnRDsegXl1CJYqVyg7FvoPclV+oTVCvz8t+n2NHXJzSIm3U7Ut1ves8IhQ3cgGIBXAhavx+uObQs8pVIRP0kmQ4GKVGvlv5CmBqqRta42xV+tmTZt/ek6jQ63NfFDhK8bczD/I1awkRGKf0GMe7ybS4PDS821SIJKjtYqdDic84pCOrYTfJ6LsEJ4n3qPGqKkXStX0LiOEkXJP5wG/DHPKgw4QCS3te9GgFDSiJU1AKDv3KkAxhCcvTNWHCUcLYJtWgBZFBmFst3rnVBJkavVTLfE3UpklM9oVtZg50X1X5TkUZS4Q==";
	private final String priKey_4096 = "AItrqmBz7HxadHCTxqOuFWTQirlaSYY37BbzBRVpR+OW/TWPDJNLNnXfrk/vlkSU7Tovwn3/oeTpt00TILpbs4fgPGoNl9+DCexvGN9Hr2Y/qYAB4XB9vf7+weK/TB482NNpFBPCSHmdZ376loNEeARNRELC1MhlgvL0INwhXVQ2dL93AwEyIon2XRCgBq1vkJstDmmicjHegq8LrnwB1bwwWOIb0vr/iSBq0lexjFdRNwOxAR+ZjwP0LgpZ2Xvoo1AOAwcYLjkKOYiW1QImgZ5pBVx+4mVHq8xAd0sCWhHpwyYCmAcSpMaxBDOv47VWePjPwtn/PRQaRBs5bkqLG85dMvJPWDGCyQV7DfxVNVQuDuYztZ+785R3JNGM46Wh5dWqjUN1nCc2iwfljHZZWNIvdKXX8x4nCoHNZEFtMsyUljGFQCQkyY8clArx4R2D+z/vRa7kSSXlPgZkMDiD2Tfws0QjxJNctuuYkq6NnsB7hEA8XcJ1jKrWN2ji12omJYiGNMO47ecg4tfSS921TJfaKcR8ZiOOzIyVG7bvznAkAkAGlhRzrDGOdNzDn3MsCZVjg6o/xP57p4NjaQLGYDuRN1Sl137G2IScIqVkPLvna7anZTcm3tt0OLXAIl/QQWlBmw5DA+UIxAryVagkgwdZ9jiG1zF+8OSIIPE8+84J";

	private final String pubModule_1024 = "AIFjtTESFCEI74yhtzDLUxXaS7VBuRljo1y4GvoCMbUdMKcUEBp8h70I5qtIet/m6IF9ByOc92fPo89SULxHKN+A8EV5Vuu9eOTBl5R4KPbSc7plYsLP7pZFvOYy3lcsMxvdA2piLyNtoAFHgrH9RAECM+KatXKWni39JYmUYlCN";
	private final String pubModule_2048 = "AKRZJQrRlSsQRYFedeMjCqvfBg2dNwbMn9paLCjwkOupPlppKEoSuDRg6LxewiAN3dogaOeRrXAOTtjhA/BeLBPcXRiakcU9oeRqah9r8smbUKoM9bB0vmdMfsem5Tin+Yqh5klQ0DAOpItjaROEFHVO6Fc9EYEGXUIGDsnWAbBbuopIPR0/T1SxCXM6jnq7lz6i9aTCgOLe0zz5qMWtLApNKPBo+3eOhSiaQdqrPW6qcp+c/4ljZJS/OuQVkwbnGG+5eG6PlDTLBL/r9xQq1+L4NB4p91l7H5o4hOVB3iW9264yS47N28FV8t0buXm8Ng0KUyPjDx0fM05EKW5tDw0=";
	private final String pubModule_4096 = "AJW3dOwipt1bJzuXGMEs6Zqy4AT8zIieIauEDm5yZvUUrto5V29Vb46rWByDzi9YjowsP4O2eb+AVRAGgDqCRvjK571DVRSoh9ZtRJe2+U4Xm0J7lKkGRvoJ0bbMvrITYBnHrAbFjXf3eypIUnmKRzHp1nkWbDgM3dDDp6URsv4g1v4g0AW57mqZM51/ScX9J/yRPhV2pj54RYU5UFfasQ5/QO9q02OZolMfApYIuZvfI/O31qj0jz7blyCb2yUMOE/fMaTsGQFaQTJhHxYndl5kERqR60u9MScWQURFUSrxt6qWWFiz+9gxGz45VH5z8zYwjlLg4VFIBe3aGnoA2UujVNuZ88b6zO1nVbbndqyDLiApX0JDCXfmodmW4kmMx2AiSRUgBmSY+31h2cmmCcGwrlm05B79P/39ktiBxtrjQStCygSik/7eQUDA8Hhl4ZPgvfXepgU/z0+k5XrIddLCMkIrJe8Z4MChN14G0M+G172DUzcWw/YcMMd8EXQ0Q2OINm1d4adl8vvq0sqcNTpOGpHhKPm+ImH/9cLhTQeJb0BIv7Ff42G3bmIjsOlxqQ5iEclv9XnLOY6uWydtZu7JRpV6ZbOhAetspp2nX+h74QFgvWYGMVpY0V+kZ3vNZ8R93hYHs+dj23do1gy1LbAAXGxM5jzjeOSIFNriAF+V";

	private final String priModule_1024 = "AIFjtTESFCEI74yhtzDLUxXaS7VBuRljo1y4GvoCMbUdMKcUEBp8h70I5qtIet/m6IF9ByOc92fPo89SULxHKN+A8EV5Vuu9eOTBl5R4KPbSc7plYsLP7pZFvOYy3lcsMxvdA2piLyNtoAFHgrH9RAECM+KatXKWni39JYmUYlCN";
	private final String priModule_2048 = "AKRZJQrRlSsQRYFedeMjCqvfBg2dNwbMn9paLCjwkOupPlppKEoSuDRg6LxewiAN3dogaOeRrXAOTtjhA/BeLBPcXRiakcU9oeRqah9r8smbUKoM9bB0vmdMfsem5Tin+Yqh5klQ0DAOpItjaROEFHVO6Fc9EYEGXUIGDsnWAbBbuopIPR0/T1SxCXM6jnq7lz6i9aTCgOLe0zz5qMWtLApNKPBo+3eOhSiaQdqrPW6qcp+c/4ljZJS/OuQVkwbnGG+5eG6PlDTLBL/r9xQq1+L4NB4p91l7H5o4hOVB3iW9264yS47N28FV8t0buXm8Ng0KUyPjDx0fM05EKW5tDw0=";
	private final String priModule_4096 = "AJW3dOwipt1bJzuXGMEs6Zqy4AT8zIieIauEDm5yZvUUrto5V29Vb46rWByDzi9YjowsP4O2eb+AVRAGgDqCRvjK571DVRSoh9ZtRJe2+U4Xm0J7lKkGRvoJ0bbMvrITYBnHrAbFjXf3eypIUnmKRzHp1nkWbDgM3dDDp6URsv4g1v4g0AW57mqZM51/ScX9J/yRPhV2pj54RYU5UFfasQ5/QO9q02OZolMfApYIuZvfI/O31qj0jz7blyCb2yUMOE/fMaTsGQFaQTJhHxYndl5kERqR60u9MScWQURFUSrxt6qWWFiz+9gxGz45VH5z8zYwjlLg4VFIBe3aGnoA2UujVNuZ88b6zO1nVbbndqyDLiApX0JDCXfmodmW4kmMx2AiSRUgBmSY+31h2cmmCcGwrlm05B79P/39ktiBxtrjQStCygSik/7eQUDA8Hhl4ZPgvfXepgU/z0+k5XrIddLCMkIrJe8Z4MChN14G0M+G172DUzcWw/YcMMd8EXQ0Q2OINm1d4adl8vvq0sqcNTpOGpHhKPm+ImH/9cLhTQeJb0BIv7Ff42G3bmIjsOlxqQ5iEclv9XnLOY6uWydtZu7JRpV6ZbOhAetspp2nX+h74QFgvWYGMVpY0V+kZ3vNZ8R93hYHs+dj23do1gy1LbAAXGxM5jzjeOSIFNriAF+V";


	private static String pubKey = "";
	private static String priKey = "";

	private static String pubModule = "";
	private static String priModule = "";


	private static boolean encryptEnable = false;

	private static RSAKeyManager rsaKeyManager = null;

	static Logger logger = LoggerFactory.getLogger(RSAKeyManager.class);


	private RSAKeyManager() {
		//WAS 기동시에 생성되도록 하려면 주석 해제
		//createRSAKey();

		//WAS 가 이중화 되었을 경우를 고려하여...
		//key 를 fix 된 값으로 사용하도록 우선 구현함ß.
		{
			pubKey    = pubKey_2048;
			priKey    = priKey_2048;
			pubModule = pubModule_2048;
			priModule = priModule_2048;

			encryptEnable = true;
		}
	}

	public static synchronized RSAKeyManager getInstance() {
		if( rsaKeyManager == null ) {
			rsaKeyManager = new RSAKeyManager();
		}
		return rsaKeyManager;
	}


	/**
	 * 공개키와 비밀키를 생성한다.<p>
	 * 암호화는 RSA ( 1024bit 키 ) 사용하며 byte[] 로 변환 후 Base64로 encode 한 문자열이다.
	 */
	public static void createRSAKey() {

		StringBuffer sb = new StringBuffer();
		LogUtil.bar2(sb,LogUtil.prefix("RSA Key gen-start"));

		try {

			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(4096);

			KeyPair keyPair = generator.generateKeyPair();
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");

			//----------------------------------------------------------------------------
			//PublicKey
			//----------------------------------------------------------------------------
			PublicKey publicKey = keyPair.getPublic();
			RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);

			String publicKeyModulus = new String(Base64.encode(publicSpec.getModulus().toByteArray())); // toString(16);
			String publicKeyExponent = new String(Base64.encode(publicSpec.getPublicExponent().toByteArray())); //.toString(16);


			//----------------------------------------------------------------------------
			//PrivateKey
			//----------------------------------------------------------------------------
			PrivateKey privateKey = keyPair.getPrivate();
			RSAPrivateKeySpec privateKeySpec = (RSAPrivateKeySpec)
			keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);

			String privateKeyModulus = new String(Base64.encode(privateKeySpec.getModulus().toByteArray())); // toString(16);
			String privateKeyExponent = new String(Base64.encode(privateKeySpec.getPrivateExponent().toByteArray())); //.toString(16);

			{
				pubKey = publicKeyExponent;
				priKey = privateKeyExponent;

				pubModule = publicKeyModulus;
				priModule = privateKeyModulus;

				encryptEnable = true;

				LogUtil.prefix(sb, "pubKey : " + pubModule + ":" + pubKey);
				LogUtil.prefix(sb, "priKey : " + priModule + ":" + priKey);
			}

		} catch( Exception e) {
			LogUtil.prefix(sb, "RSA Key 생성에 실패했습니다. [exception:", e.getMessage(), "]");
		} finally {
			logger.info(sb.toString());
		}
	}


	/**
	 * 주어진 문자열을 공개키로 암호화한다.<p>
	 *
	 * @param text 암호화 대상 문자열
	 * @return 암호화된 문자열
	 * @throws Exception
	 */
	public static String encrypt(String text) throws Exception {
		if( priKey == null || priModule == null ) {
			return null;
		}

		return encrypt( priModule, priKey, text);
	}

	/**
	 * 공개키를 가지고 암호화된 문자열을 제공한다. <p>
	 *
	 * @param publicKeyModulus
	 * @param publicKeyExponent
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String publicKeyModulus, String publicKeyExponent, String text) throws Exception {
		if( publicKeyModulus == null || publicKeyModulus.equals("")) {
			throw new IllegalArgumentException("PublicKeyModulus must be specified.");
		}

		if( publicKeyExponent == null || publicKeyExponent.equals("")) {
			throw new IllegalArgumentException("PublicKeyExponent must be specified.");
		}

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		BigInteger bigIntPubModulus
			= new BigInteger(Base64.decode(publicKeyModulus.getBytes()));
	    BigInteger bigIntPubExponent
	    	= new BigInteger(Base64.decode(publicKeyExponent.getBytes()));

		RSAPublicKeySpec p = new RSAPublicKeySpec(bigIntPubModulus
			, bigIntPubExponent);
		PublicKey publicKey = keyFactory.generatePublic(p);

		Cipher cipher = Cipher.getInstance(MODE);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

	    byte[] encrypted = cipher.doFinal(text.getBytes());
	    return new String(Base64.encode(encrypted));
	}

	/**
	 * 개인키를 가지고 복호화된 문자열을 제공한다. <p>
	 *
	 * @param privateKeyModulus
	 * @param privateKeyExponent
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String privateKeyModulus, String privateKeyExponent, String text) throws Exception {
		if( privateKeyModulus == null || privateKeyModulus.equals("")) {
			throw new IllegalArgumentException("PrivateKeyModulus must be specified.");
		}

		if( privateKeyExponent == null || privateKeyExponent.equals("")) {
			throw new IllegalArgumentException("PrivateKeyExponent must be specified.");
		}

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		BigInteger bigIntPrivateModulus
			= new BigInteger(Base64.decode(privateKeyModulus.getBytes()));
	    BigInteger bigIntPrivateExponent
	    	= new BigInteger(Base64.decode(privateKeyExponent.getBytes()));

		RSAPrivateKeySpec pa = new RSAPrivateKeySpec(
				bigIntPrivateModulus, bigIntPrivateExponent);
		PrivateKey privateKey = keyFactory.generatePrivate(pa);

		Cipher cipher = Cipher.getInstance(MODE);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

	    byte[] decrypted = cipher.doFinal(Base64.decode(text.getBytes()));
		return new String(decrypted);
	}

	/**
	 * 주어진 문자열을 개인키로 복호화한다.<p>
	 *
	 * @param text 복호화 대상 문자열
	 * @return 복호화된 문자열
	 * @throws Exception
	 */
	public static String decrypt(String text) throws Exception {
		if( priKey == null || priModule == null ) {
			return null;
		}

		return decrypt( priModule, priKey, text);
	}

	/**
	 * privateKey return
	 * @return
	 */
	public static String getPrivateKey() {
		return priKey;
	}

	/**
	 * publicKey return
	 * @return
	 */
	public static String getPublicKey() {
		return pubKey;
	}

	/**
	 * privateModule return
	 * @return
	 */
	public static String getPrivateModule() {
		return priModule;
	}

	/**
	 * publicModule return
	 * @return
	 */
	public static String getPublicModule() {
		return pubModule;
	}

	/**
	 *
	 * @return
	 */
	public static boolean isEncryptEnable() {
		return encryptEnable;
	}
}
