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
package pep.per.mint.front.env;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import pep.per.mint.common.data.basic.UserRole;
import pep.per.mint.common.data.basic.monitor.FrontLogOption;
import pep.per.mint.common.parser.BooleanTypeHandler;
import pep.per.mint.common.util.LogUtil;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.Environments;
import pep.per.mint.database.service.co.CommonService;

import javax.annotation.PostConstruct;

/**
 * @author Solution TF
 *
 */
public class FrontEnvironments {

	final static String DEFAULT_UPLOAD_FILE_PATH = "WEB-INF/attachfiles";


	Logger logger = LoggerFactory.getLogger(FrontEnvironments.class);

	public static String frontLoggingYn = "Y";

	public static Map<String, FrontLogOption> frontLogOptionMap;

	public static String systemEncoding = "UTF-8";

	public static List<UserRole> interfaceDataAccessRoleList;

	public static String attachFilePath;

	public static boolean entityHerstoryOn = false;

	public static String deployInterfaceUrl;

	public static boolean useExternApproval  = true;

	public static String interfaceTransferRealUrl = null;//"http://idc.mocomsys.com:38080";

	public static String interfaceTransferTestUrl = null;//"http://idc.mocomsys.com:38080";

	/* 아시아나건으로 추가된 환경설정  */
	// 로그인히스토리 사용여부
	public static boolean historyYn = false;
	// 중복로그인방지 체크 여부
	public static boolean duplicationLoginCheckYn = false;
	// 중복로그인 체크 방법(1=userId, 2=userId+IP)
	public static String duplicationOption = "1";
	// 주기적인 패스워드 변경 사용 여부
	public static boolean passwordCheckYn = false;
	// 주기적인 패스워드 변경 사용시 패스워드 유효기간
	public static int passwordInterval = 90;
	// 패스워드 변경시 정규식
	public static String passwordRgularex;
	// 패스워드 변경시 정규식 알림 메세지
	public static String passwordRgularexMsg;
	// 패스워드 실패 횟수 체크 여부
	public static boolean passwordFailCheck = false;
	// 패스워드 실패 횟수 한계치
	public static int failCheckCount = 5;
	// 로그인 IP 체크 여부
	public static boolean ipCheck = false;
	// 사용자 정보 테이블에 패스워드 저장시 암호화 사용여부
	public static boolean passwordEncrypt = false;
	/* 아시아나건으로 추가된 환경설정 */

	// XSS Filtering
	public static String[] frontScriptFilters;

	public static Map<String, Object> complexType;
	
	// 신한생명 DRM 해제 사용 여부
	public static String drmApiUseYn = "N";

	@Autowired
	CommonService commonService;


	public void init() throws Throwable {

		StringBuffer sb = new StringBuffer();
		LogUtil.bar2(sb,LogUtil.prefix("IIP 포털 환경 설정"));
		try {

			Map<String, List<String>> environmentalValues = commonService.getEnvironmentalValues();

			//Solution TF:test
			String testUploadPath = System.getProperty("testUploadPath");
			if(!Util.isEmpty(testUploadPath)){

				attachFilePath = testUploadPath;
				LogUtil.prefix(sb, "테스트환경설정값(testUploadPath = ", testUploadPath ,")존재하여 테스트환경으로 보고 system.file.upload.path 값으로 사용한다.");
				File path = new File(attachFilePath);
				if (!path.exists()) {
					String exceptionMsg = Util.join("포털설정값 파일업로드 패스[system.file.upload.path:", attachFilePath, "] 가 존재하지 않습니다. 디렉토리 및 권한 여부를 점검 후 디플로이해주세요.");
					LogUtil.prefix(sb, exceptionMsg);
					throw new Exception(exceptionMsg);
				}

			}else{
				List<String> paths = environmentalValues.get("system.file.upload.path");
				if (paths != null && paths.size() > 0) {
					attachFilePath = paths.get(0);
					File path = new File(attachFilePath);
					if (!path.exists()) {
						String exceptionMsg = Util.join("포털설정값 파일업로드 패스[system.file.upload.path:", attachFilePath, "] 가 존재하지 않습니다. 디렉토리 및 권한 여부를 점검 후 디플로이해주세요.");
						LogUtil.prefix(sb, exceptionMsg);
						throw new Exception(exceptionMsg);
					}
//				if (!path.canWrite()) {
//					String exceptionMsg = Util.join("포털설정값 파일업로드 패스 프로퍼티[system.file.upload.path:", attachFilePath, "]위치에 파일 쓰기 권한이 없습니다. 권한 부여 후 어플리케이션을 디플로이해주세요.");
//					LogUtil.prefix(sb, exceptionMsg);
//					throw new Exception(exceptionMsg);
//				}
				} else {
					String exceptionMsg = Util.join("파일업로드 패스 프로퍼티가 포털설정에 존재하지 않습니다.[system.file.upload.path]");
					LogUtil.prefix(sb, exceptionMsg);
					throw new Exception(exceptionMsg);
				}

			}
			LogUtil.prefix(sb, Util.join("system.file.upload.path:", attachFilePath));

			try {
				List<String> herstoryOnValues = environmentalValues.get("system.entity.herstory.on");
				if (herstoryOnValues == null || herstoryOnValues.size() == 0) {
					entityHerstoryOn = false;
					LogUtil.prefix(sb, "포털환경설정값[system.entity.herstory.on] 이 등록되지 않았으므로 false 로 지정한다.");
				}else {
					entityHerstoryOn = Boolean.parseBoolean(herstoryOnValues.get(0));
				}
			} catch (Throwable e) {
				entityHerstoryOn = false;
				logger.error("[FrontEnvironments.init 예외발생][예외가 발생되어 포털환경 프로퍼티는 다음과 같이 설정됨(entityHerstoryOn=false)]", e);
				LogUtil.prefix(sb, "포털환경설정값[system.entity.herstory.on] 세팅중 예외발생하여 기본 값 false 로 지정한다.[exception:", e.getMessage(), "]");

			}

			//Solution TF:test
			if("true".equals(System.getProperty("alwaysEntityHerstoryOn"))) {
				entityHerstoryOn = true;
				LogUtil.prefix(sb, "시스템포털환경설정값 alwaysEntityHerstoryOn = true 이므로 system.entity.herstory.on 값을 true 로 지정한다.");
			}

			Environments.entityHerstoryOn = entityHerstoryOn;
			LogUtil.prefix(sb, Util.join("system.entity.herstory.on:", entityHerstoryOn));




			useExternApproval  = true;
			try {
				List<String> useExternApprovalValues = environmentalValues.get("system.approval.use.extern");
				if (useExternApprovalValues == null || useExternApprovalValues.size() == 0) {
					useExternApproval = true;
					LogUtil.prefix(sb, "포털환경설정값[system.approval.use.extern] 이 등록되지 않았으므로 true 로 지정한다.");
				}else {
					useExternApproval = Boolean.parseBoolean(useExternApprovalValues.get(0));
				}
			} catch (Throwable e) {
				useExternApproval = true;
				LogUtil.prefix(sb, "포털환경설정값[system.approval.use.extern] 세팅중 예외발생하여 기본 값 true 로 지정한다.[exception:", e.getMessage(), "]");

			}
			Environments.useExternApproval = useExternApproval;
			LogUtil.prefix(sb, Util.join("system.approval.use.extern:", useExternApproval));



			deployInterfaceUrl  = "http://iip.mocomsys.com:8080/mint/op/deploy/interfaces/response";
			try {
				List<String> deployInterfaceUrlValues = environmentalValues.get("system.deploy.interface.url");
				if (deployInterfaceUrlValues == null || deployInterfaceUrlValues.size() == 0) {
					LogUtil.prefix(sb, "포털환경설정값[system.deploy.interface.url] 세팅중 예외발생하여 기본 값 기본값[",deployInterfaceUrl,"]으로 지정한다.");
				}else {
					deployInterfaceUrl = deployInterfaceUrlValues.get(0);
				}
			} catch (Throwable e) {
				LogUtil.prefix(sb, "포털환경설정값[system.deploy.interface.url] 세팅중 예외발생하여 기본 값 기본값[",deployInterfaceUrl,"]으로 지정한다.[exception:", e.getMessage(), "]");

			}
			Environments.deployInterfaceUrl = deployInterfaceUrl;
			LogUtil.prefix(sb, Util.join("system.deploy.interface.url:", deployInterfaceUrl));

			//interfaceTransferTestUrl  = "http://iip.mocomsys.com:8080";
			try {
				List<String> transferInterfaceUrlValues = environmentalValues.get("system.transfer.interface.test.url");
				if (transferInterfaceUrlValues == null || transferInterfaceUrlValues.size() == 0) {
					LogUtil.prefix(sb, "포털환경설정값[system.transfer.interface.test.url] 세팅중 예외발생하여 기본 값 기본값[",interfaceTransferTestUrl,"]으로 지정한다.");
				}else {
					interfaceTransferTestUrl = transferInterfaceUrlValues.get(0);
				}
			} catch (Throwable e) {
				LogUtil.prefix(sb, "포털환경설정값[system.transfer.interface.test.url] 세팅중 예외발생하여 기본 값 기본값[",interfaceTransferTestUrl,"]으로 지정한다.[exception:", e.getMessage(), "]");

			}
			//Environments.interfaceTransferTestUrl = interfaceTransferTestUrl;
			LogUtil.prefix(sb, Util.join("ssystem.transfer.interface.test.url:", interfaceTransferTestUrl));


			//interfaceTransferRealUrl  = "http://iip.mocomsys.com:8080";
			try {
				List<String> transferInterfaceUrlValues = environmentalValues.get("system.transfer.interface.real.url");
				if (transferInterfaceUrlValues == null || transferInterfaceUrlValues.size() == 0) {
					LogUtil.prefix(sb, "포털환경설정값[system.transfer.interface.real.url] 세팅중 예외발생하여 기본 값 기본값[",interfaceTransferRealUrl,"]으로 지정한다.");
				}else {
					interfaceTransferRealUrl = transferInterfaceUrlValues.get(0);
				}
			} catch (Throwable e) {
				LogUtil.prefix(sb, "포털환경설정값[system.transfer.interface.real.url] 세팅중 예외발생하여 기본 값 기본값[",interfaceTransferRealUrl,"]으로 지정한다.[exception:", e.getMessage(), "]");

			}
			//Environments.interfaceTransferTestUrl = interfaceTransferTestUrl;
			LogUtil.prefix(sb, Util.join("ssystem.transfer.interface.real.url:", interfaceTransferRealUrl));

			//-------------------------------------------------------------------------------------------
			// subject  : 사용자보안 유효성 체크로 추가 환경설정
			// date     : 20190130
			// contents :
			//  * 아시아나건으로 추가되었음
			//-------------------------------------------------------------------------------------------
			{
				//-----------------------------------------------
				// 로그인 히스토리 사용 여부
				//-----------------------------------------------
				historyYn  = false;
				try {
					List<String> historyEnv = environmentalValues.get("system.security.loginHistory.check");
					if (historyEnv == null || historyEnv.size() == 0) {
						historyYn = false;
						LogUtil.prefix(sb, "포털환경설정값[system.security.loginHistory.check] 이 등록되지 않았으므로 false 로 지정한다.");
					}else {
						historyYn = Boolean.parseBoolean(historyEnv.get(0));
					}
				} catch (Throwable e) {
					historyYn = false;
					LogUtil.prefix(sb, "포털환경설정값[system.security.loginHistory.check] 세팅중 예외발생하여 기본 값 false 로 지정한다.[exception:", e.getMessage(), "]");
				}
				LogUtil.prefix(sb, Util.join("system.security.loginHistory.check:", historyYn));

				//-----------------------------------------------
				// 중복 로그인 체크 여부
				//-----------------------------------------------
				duplicationLoginCheckYn  = false;
				try {
					List<String> duplicationEnv = environmentalValues.get("system.security.duplicationLogin.check");
					if (duplicationEnv == null || duplicationEnv.size() == 0) {
						duplicationLoginCheckYn = false;
						LogUtil.prefix(sb, "포털환경설정값[system.security.duplicationLogin.check] 이 등록되지 않았으므로 false 로 지정한다.");
					}else {
						duplicationLoginCheckYn = Boolean.parseBoolean(duplicationEnv.get(0));
					}
				} catch (Throwable e) {
					duplicationLoginCheckYn = false;
					LogUtil.prefix(sb, "포털환경설정값[system.security.duplicationLogin.check] 세팅중 예외발생하여 기본 값 false 로 지정한다.[exception:", e.getMessage(), "]");
				}
				LogUtil.prefix(sb, Util.join("system.security.duplicationLogin.check:", duplicationLoginCheckYn));

				//-----------------------------------------------
				// 중복 로그인 옵션 (1:키 값 userId, 2: 키 값 userId+ip)
				//-----------------------------------------------
				duplicationOption  = "1";
				try {
					List<String> duplicationEnv = environmentalValues.get("system.security.duplicationLogin.check.option");
					if (duplicationEnv == null || duplicationEnv.size() == 0) {
						duplicationOption = "1";
						LogUtil.prefix(sb, "포털환경설정값[system.security.duplicationLogin.check.option] 이 등록되지 않았으므로 90으로 지정한다.");
					}else {
						duplicationOption = duplicationEnv.get(0);
					}
				} catch (Throwable e) {
					duplicationOption = "1";
					LogUtil.prefix(sb, "포털환경설정값[system.security.duplicationLogin.check.option] 세팅중 예외발생하여 기본 값 90으로 지정한다.[exception:", e.getMessage(), "]");
				}
				LogUtil.prefix(sb, Util.join("system.security.duplicationLogin.check.option:", duplicationOption));

				//-----------------------------------------------
				// 패스워드 변경기한 체크
				//-----------------------------------------------
				passwordCheckYn  = false;
				try {
					List<String> duplicationEnv = environmentalValues.get("system.security.password.check");
					if (duplicationEnv == null || duplicationEnv.size() == 0) {
						passwordCheckYn = false;
						LogUtil.prefix(sb, "포털환경설정값[system.security.password.check] 이 등록되지 않았으므로 90으로 지정한다.");
					}else {
						passwordCheckYn = Boolean.parseBoolean(duplicationEnv.get(0));
					}
				} catch (Throwable e) {
					passwordCheckYn = false;
					LogUtil.prefix(sb, "포털환경설정값[system.security.password.check] 세팅중 예외발생하여 기본 값 90으로 지정한다.[exception:", e.getMessage(), "]");
				}
				LogUtil.prefix(sb, Util.join("system.security.password.check:", passwordCheckYn));

				//-----------------------------------------------
				// 패스워드 변경기한 일수
				//-----------------------------------------------
				passwordInterval  = 90;
				try {
					List<String> duplicationEnv = environmentalValues.get("system.security.password.check.interval");
					if (duplicationEnv == null || duplicationEnv.size() == 0) {
						passwordInterval = 90;
						LogUtil.prefix(sb, "포털환경설정값[system.security.password.check.interval] 이 등록되지 않았으므로 90으로 지정한다.");
					}else {
						passwordInterval = Integer.parseInt(duplicationEnv.get(0));
					}
				} catch (Throwable e) {
					passwordInterval = 90;
					LogUtil.prefix(sb, "포털환경설정값[system.security.password.check.interval] 세팅중 예외발생하여 기본 값 90으로 지정한다.[exception:", e.getMessage(), "]");
				}
				LogUtil.prefix(sb, Util.join("system.security.password.check.interval:", passwordInterval));

				//-----------------------------------------------
				// 패스워드 정규식
				//-----------------------------------------------
				passwordRgularex  = "";
				try {
					List<String> passwordRgularexEnv = environmentalValues.get("system.security.password.check.rgularex");
					if (passwordRgularexEnv == null || passwordRgularexEnv.size() == 0) {
						passwordRgularex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$";
						LogUtil.prefix(sb, "포털환경설정값[system.security.password.check.rgularex] 이 등록되지 않았으므로 [영문/숫자/특수문자 8자]로 지정한다.");
					}else {
						passwordRgularex = passwordRgularexEnv.get(0);
					}
				} catch (Throwable e) {
					passwordRgularex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$";
					LogUtil.prefix(sb, "포털환경설정값[system.security.password.check.rgularex] 세팅중 예외발생하여 기본 값 [영문/숫자/특수문자 8자]로 지정한다.[exception:", e.getMessage(), "]");
				}
				LogUtil.prefix(sb, Util.join("system.security.password.check.rgularex:", passwordRgularex));

				//-----------------------------------------------
				// 패스워드 정규식 메세지
				//-----------------------------------------------
				passwordRgularexMsg  = "";
				try {
					List<String> passwordRgularexEnv2 = environmentalValues.get("system.security.password.check.rgularex.msg");
					if (passwordRgularexEnv2 == null || passwordRgularexEnv2.size() == 0) {
						passwordRgularexMsg = "문자, 숫자, 특수문자로 이루어진 최소길이 8자 이상";
						LogUtil.prefix(sb, "포털환경설정값[system.security.password.check.rgularex.msg] 이 등록되지 않았으므로 [문자, 숫자, 특수문자로 이루어진 최소길이 8자 이상]로 지정한다.");
					}else {
						passwordRgularexMsg = passwordRgularexEnv2.get(0);
					}
				} catch (Throwable e) {
					passwordRgularexMsg = "문자, 숫자, 특수문자로 이루어진 최소길이 8자 이상";
					LogUtil.prefix(sb, "포털환경설정값[system.security.password.check.rgularex.msg] 세팅중 예외발생하여 기본 값 [문자, 숫자, 특수문자로 이루어진 최소길이 8자 이상]로 지정한다.[exception:", e.getMessage(), "]");
				}
				LogUtil.prefix(sb, Util.join("system.security.password.check.rgularex.msg:", passwordRgularexMsg));

				//-----------------------------------------------
				// 패스워드 입력 실패 체크
				//-----------------------------------------------
				passwordFailCheck  = false;
				try {
					List<String> passwordFailEnv = environmentalValues.get("system.security.password.fail.check");
					if (passwordFailEnv == null || passwordFailEnv.size() == 0) {
						passwordFailCheck  = false;
						LogUtil.prefix(sb, "포털환경설정값[system.security.password.fail.check] 이 등록되지 않았으므로 false으로 지정한다.");
					}else {
						passwordFailCheck = Boolean.parseBoolean(passwordFailEnv.get(0));
					}
				} catch (Throwable e) {
					passwordFailCheck  = false;
					LogUtil.prefix(sb, "포털환경설정값[system.security.password.fail.check] 세팅중 예외발생하여 기본 값 false으로 지정한다.[exception:", e.getMessage(), "]");
				}
				LogUtil.prefix(sb, Util.join("system.security.password.fail.check:", passwordFailCheck));

				//-----------------------------------------------
				// 패스워드 입력 실패 제한 횟수
				//-----------------------------------------------
				failCheckCount  = 5;
				try {
					List<String> passwordFailOption = environmentalValues.get("system.security.password.fail.check.count");
					if (passwordFailOption == null || passwordFailOption.size() == 0) {
						failCheckCount  = 5;
						LogUtil.prefix(sb, "포털환경설정값[system.security.password.fail.check.count] 이 등록되지 않았으므로 5로 지정한다.");
					}else {
						failCheckCount = Integer.parseInt(passwordFailOption.get(0));
					}
				} catch (Throwable e) {
					failCheckCount  = 5;
					LogUtil.prefix(sb, "포털환경설정값[system.security.password.fail.check.count] 세팅중 예외발생하여 기본 값 5로 지정한다.[exception:", e.getMessage(), "]");
				}
				LogUtil.prefix(sb, Util.join("system.security.password.fail.check.count:", failCheckCount));

				//-----------------------------------------------
				// 로그인시 IP 체크 여부
				//-----------------------------------------------
				ipCheck  = false;
				try {
					List<String> ipRestrictionOption = environmentalValues.get("system.security.ip.check");
					if (ipRestrictionOption == null || ipRestrictionOption.size() == 0) {
						ipCheck = false;
						LogUtil.prefix(sb, "포털환경설정값[system.security.ip.check] 이 등록되지 않았으므로 false로 지정한다.");
					}else {
						ipCheck = Boolean.parseBoolean(ipRestrictionOption.get(0));
					}
				} catch (Throwable e) {
					ipCheck = false;
					LogUtil.prefix(sb, "포털환경설정값[system.security.ip.check] 세팅중 예외발생하여 기본 값 false로 지정한다.[exception:", e.getMessage(), "]");
				}
				LogUtil.prefix(sb, Util.join("system.security.ip.check:", ipCheck));

				//-----------------------------------------------
				// 패스워드 저장시 암호화 여부
				//-----------------------------------------------
				passwordEncrypt  = false;
				try {
					List<String> additionalEncryptEnv = environmentalValues.get("system.security.password.encrypt");
					if (additionalEncryptEnv == null || additionalEncryptEnv.size() == 0) {
						passwordEncrypt = false;
						LogUtil.prefix(sb, "포털환경설정값[system.security.password.encrypt] 이 등록되지 않았으므로 false로 지정한다.");
					}else {
						passwordEncrypt = Boolean.parseBoolean(additionalEncryptEnv.get(0));
					}
				} catch (Throwable e) {
					passwordEncrypt = false;
					LogUtil.prefix(sb, "포털환경설정값[system.security.password.encrypt] 세팅중 예외발생하여 기본 값 false로 지정한다.[exception:", e.getMessage(), "]");
				}
				LogUtil.prefix(sb, Util.join("system.security.password.encrypt:", passwordEncrypt));
			}


			//-------------------------------------------------------------------------------------------
			// subject  : XSS Filtering
			// date     : 2020908
			// contents :
			//  * < or > tag 를 원천봉쇄 해달라는 형사통합 요구사항으로, FieldCheckUitl 에서 이관
			//  * Default Filter + 환경설정 Filter 체크
			//-------------------------------------------------------------------------------------------
			{
				try {
					List<String> includeFilter = environmentalValues.get("security.xss.filter.include");
					if (includeFilter == null || includeFilter.size() == 0) {
						frontScriptFilters = getXSSDefultFilter();
					} else {
						String[] filters = includeFilter.toArray(new String[0]);

						frontScriptFilters = new String[filters.length + getXSSDefultFilter().length];
						System.arraycopy(filters, 0, frontScriptFilters, 0, filters.length);
						System.arraycopy(getXSSDefultFilter(), 0, frontScriptFilters, filters.length, getXSSDefultFilter().length);
					}
				} catch (Throwable e) {
					LogUtil.prefix(sb, "포털환경설정값[security.xss.filter.include] 세팅중 예외발생하여 기본 값 기본값만 지정한다.[exception:", e.getMessage(), "]");
				}

				LogUtil.prefix(sb, Util.join("security.xss.filter", frontScriptFilters));
			}

			//-------------------------------------------------------------------------------------------
			// subject  : Layout ComplexType
			// date     : 20201202
			//-------------------------------------------------------------------------------------------
			{
				try {
					List<String> complextype = environmentalValues.get("layout.complextype");
					if (complextype == null || complextype.size() == 0) {
						complexType = new HashMap<String, Object>();//complexType = new HashMap<>(); jvm1.6 이하에서 지원하지 않는 문법이라 변경함.
						complexType.put("4", "complextype");
					} else {
						complexType = Util.jsonToMap(complextype.get(0));
					}
				} catch (Throwable e) {
					LogUtil.prefix(sb, "포털환경설정값[layout.complextype] 세팅중 예외발생하여 기본값만 지정한다.[exception:", e.getMessage(), "]");
				}

				LogUtil.prefix(sb, Util.join("layout.complextyper", complexType));
			}
			
			//-----------------------------------------------
			// 신한생명 DRM 해제 사용 여부 20201215
			//-----------------------------------------------
			drmApiUseYn  = "N";
			try {
				List<String> drmApiUseYnEnv = environmentalValues.get("inhouse.drm.api.used");
				if (drmApiUseYnEnv == null || drmApiUseYnEnv.size() == 0) {
					drmApiUseYn = "N";
					LogUtil.prefix(sb, "포털환경설정값[inhouse.drm.api.used] 이 등록되지 않았으므로 N으로 지정한다.");
				}else {
					drmApiUseYn = drmApiUseYnEnv.get(0);
				}
			} catch (Throwable e) {
				drmApiUseYn = "N";
				LogUtil.prefix(sb, "포털환경설정값[inhouse.drm.api.used] 세팅중 예외발생하여 기본 값 N으로 지정한다.[exception:", e.getMessage(), "]");
			}
			LogUtil.prefix(sb, Util.join("inhouse.drm.api.used:", drmApiUseYn));

		}finally {
			logger.info(sb.toString());
		}


	}

	/**
	 * XSS Default Filter
	 * @return
	 */
	private String[] getXSSDefultFilter() {
		String[] frontScriptFilters = {
				// "<"
				//,"&lt"
				//,">"
				//,"&gt"
				//,")"
				//,"&lpar"
				//,"("
				//,"&rpar"
				"<img"
				,"&lt;img"
				,"onstop"
				//,"layer"
				,"<javascript"
				,"&lt;javascript"
				,"javascript>"
				,"javascript&gt;"
				,"eval("
				,"eval&lpar"
				,"onactivate"
				,"onfocusin"
				,"<applet"
				,"&lt;applet"
				,"applet>"
				,"applet&gt;"
				//,"document"
				,"onclick"
				,"onkeydown"
				//,"xml"
				//,"create"
				,"onbeforecut"
				,"onkeyup"
				,"<link"
				,"&lt;link"
				,"link>"
				,"link&gt;"
				//,"binding"
				,"ondeactivate"
				,"onload"
				,"<script"
				,"&lt;script"
				,"script>"
				,"script&gt;"
				,"msgbox"
				,"ondragend"
				,"onbounce"
				,"<object"
				,"&lt;object"
				,"object>"
				,"object&gt;"
				,"<embed"
				,"&lt;embed"
				,"embed>"
				,"embed&gt;"
				,"ondragleave"
				,"onmovestart"
				,"<frame"
				,"&lt;frame"
				,"<applet"
				,"&lt;applet"
				,"applet>"
				,"applet&gt;"
				,"ondragstart"
				,"onmouseout"
				,"ilayer"
				,"onerror"
				,"onmouseup"
				,"bgsound"
				,"href"
				,"embed"
				,"onabort"
				//,"base"
				,"onstart"
				,"onfocus"
				,"onmovestart"
				,"onmove"
				,"onrowexit"
				,"onunload"
				,"onsubmit"
				,"innerHTML"
				,"onpaste"
				,"ondblclick"
				,"<vbscript"
				,"&lt;vbscript"
				,"vbscript>"
				,"vbscript&gt;"
				,"charset"
				,"onresize"
				,"ondrag"
				//,"expression"
				//,"string" //프로그램등록 서비스에서 cmdString 값이 전달되므로 뺀다.
				,"onselect"
				,"ondragenter"
				,"onchange"
				//,"append"
				,"onscroll"
				,"ondragover"
				,"<meta"
				,"&lt;meta"
				,"alert("
				,"alert&lpar"
				//,"title"
				,"ondrop"
				//,"void"
				//,"refresh"
				,"<iframe"
				,"&lt;iframe"
				,"oncopy"
				,"oncut"
				//,"ilayer"
				//,"blink"
				,"onfinish"
				,"<frameset"
				,"&lt;frameset"
				,"cookie"
				,"<style"
				,"&lt;style"
				,"style>"
				,"style&gt;"
				,"onreset"
				,"onselectstart"
			};
		return frontScriptFilters;
	}


	@SuppressWarnings("unchecked")
	public void apply(String key, Object envValue) throws Exception{

		if("frontLoggingYn".equals(key)) {

			frontLoggingYn = (String) envValue;
			logger.debug(Util.join("\n\nSet Portal environment frontLoggingYn value:",frontLoggingYn,"\n"));

		}else if("frontLogOptionMap".equals(key)) {

			frontLogOptionMap = (Map<String, FrontLogOption>) envValue;

		}else if("systemEncoding".equals(key)){

			systemEncoding = (String) envValue;

		}else if("interfaceDataAccessRoleList".equals(key)){

			interfaceDataAccessRoleList = (List<UserRole>) envValue;

		}else{
			String errorMsg =
				Util.join(
					"\n환경변수적용실패!",
					"\n입력된 환경변수[", key, "] 는 정의된 환경변수값이 아님매.",
					"\n 현재 적용가능한 환경변수는 요거야",
					"\n[String frontLoggingYn,",
					"\n Map<String, FrontLogOption> frontLogOption,",
					"\n String systemEncoding,",
					"\n List<UserRole> interfaceDataAccessRoleList");
			throw new Exception(errorMsg);
		}

	}

	/**
	 * @return the frontLoggingYn
	 */
	public String getFrontLoggingYn() {
		return frontLoggingYn;
	}

	/**
	 * @param frontLoggingYn the frontLoggingYn to set
	 */
	public void setFrontLoggingYn(String frontLoggingYn) {
		this.frontLoggingYn = frontLoggingYn;
	}

	/**
	 * @return the frontLogOptionMap
	 */
	public Map<String, FrontLogOption> getFrontLogOptionMap() {
		return frontLogOptionMap;
	}

	/**
	 * @param frontLogOptionMap the frontLogOptionMap to set
	 */
	public void setFrontLogOptionMap(Map<String, FrontLogOption> frontLogOptionMap) {
		this.frontLogOptionMap = frontLogOptionMap;
	}

	/**
	 * @return the systemEncoding
	 */
	public String getSystemEncoding() {
		return systemEncoding;
	}

	/**
	 * @param systemEncoding the systemEncoding to set
	 */
	public void setSystemEncoding(String systemEncoding) {
		this.systemEncoding = systemEncoding;
	}

	/**
	 * @return the interfaceDataAccessRoleList
	 */
	public List<UserRole> getInterfaceDataAccessRoleList() {
		return interfaceDataAccessRoleList;
	}

	/**
	 * @param interfaceDataAccessRoleList the interfaceDataAccessRoleList to set
	 */
	public void setInterfaceDataAccessRoleList(
			List<UserRole> interfaceDataAccessRoleList) {
		this.interfaceDataAccessRoleList = interfaceDataAccessRoleList;
	}


	public String getAttachFilePath() {
		return attachFilePath;
	}

	public void setAttachFilePath(String attachFilePath) {
		this.attachFilePath = attachFilePath;
	}


	public boolean isEntityHerstoryOn() {
		return entityHerstoryOn;
	}

	public void setEntityHerstoryOn(boolean entityHerstoryOn) {
		this.entityHerstoryOn = entityHerstoryOn;
		Environments.entityHerstoryOn = entityHerstoryOn;
	}


}
