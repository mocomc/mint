package pep.per.mint.inhouse.selector.sprinting;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pep.per.mint.common.data.basic.Channel;
import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.exception.IdSelectorException;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.selector.IdSelector;

/**
 * <pre>
 * SPrinting 인터페이스ID 채번 클래스
 *
 *
 * 0.리소스 정보
 * 	CD	NM		COMMENTS
 * --------------------------------------------
 *	0	DB		개발-리소스-DB
 *	1	File	개발-리소스-File		(*)
 *	2	RFC		개발-리소스-RFC 		(*)
 *	4	WS		개발-리소스-WS			(*)
 *	6	HTTP	개발-리소스-HTTP
 *	7	Direct	개발-리소스-Direct
 *	8	DB-Link 개발-리소스-DB-Link
 *	9	EDI 	개발-리소스-EDI
 *	10	RN		개발-리소스-RN
 *	11	필수입력	개발-리소스-필수입력
 *
 *
 * 1.연계 솔루션 코드
 *	CHANNEL_CD  CHANNEL_NM
 * --------------------------------------------
 *		U		미확정
 *		D		Direct
 *		I		IIB
 *		W		Webmethod
 *
 * 2.채번로직
 * 지난번에 문의 주셨던 내용 그대로 채번 로직 적용해주시면 감사하겠습니다.
 * -------------------------------------------
 * 표준화 팀에서 정의한 인터페이스 채번 룰은 아래와 같습니다.
 * 	-.연계 솔루션이 IIB 인 경우
 *	if [ 송신, 수신 리소스 중 RFC를 하나라도 포함할 경우]  송수신 기준 상관 없이   "SIFSR" + MAX순번(6자리)
 *	if [ 송신, 수신 리소스 중 WS를  하나라도 포함할 경우] 송수신 기준 상관 없이 "SIFWS" + MAX순번(6자리)    (<-- 송수신 어느쪽에도 RFC 미포함)
 *	if [송신, 수신 리소스 모두 파일일 경우] "SIFFL" + MAX순번(6자리)
 *	else 인터페이스 ID 발번 실패로 처리함.
 *
 * 	-.연계 솔루션이 webMethod 인 경우
 * 	송수신 관계 없이 "SIFWM" + MAX순번(6자리)
 *
 * 	-.연계 솔루션이 직접연결 인 경우
 * 	송수신 관계 없이 "SIFDR" + MAX순번(6자리)
 *
 * 1항에 대해서는 송신 수신이 1: N, N:1 관계로 등록이 가능하므로 (SPinrinting 의 경우는 1:1 데이터만 있다고 하더라도)
 * 송, 수신 리소스 체크시 등록된 모든 시스템에 대한 리소스를 체크하여 인터페이스 아이디를 발번하겠습니다.
 *
 * 위와 같이 개발진행되어도 되는지 최종 확인해 주세요.
 * </pre>
 * @author whoana
 *
 */
public class SSHPIdSelector implements IdSelector {

	Logger logger = LoggerFactory.getLogger(SSHPIdSelector.class);

	@Autowired
	CommonService commonService;

	public final static int ID_LENGTH = 11;
	public final static int SEQ_LENGTH = 6;
	public final static int SEQ_BEGIN_INDEX = 6;
	public final static int SEQ_END_INDEX = 11;


	public final static String CHANNEL_CD_DIRECT = "D";
	public final static String CHANNEL_CD_IIP = "I";
	public final static String CHANNEL_CD_WEBMETHOD = "W";
	public final static String CHANNEL_CD_UNDEFINE = "U";

	public final static String RESOURCE_CD_FILE = "1";
	public final static String RESOURCE_CD_RFC 	= "2";
	public final static String RESOURCE_CD_WS  	= "4";

	public final static String CPF = "SIF";
	public final static String PF_SR = "SR";
	public final static String PF_WS = "WS";
	public final static String PF_FL = "FL";
	public final static String PF_WM = "WM";
	public final static String PF_DR = "DR";


	@Override
	public String getInterfaceId(Object[] params) throws Throwable {

		StringBuffer integrationId = new StringBuffer(CPF);

		Interface interfaze = (Interface) params[0];
		if(interfaze == null) {
			throw new IdSelectorException("The IIP is fail to generate interface id authomtically because there is no interface info");
		}

		logger.debug(
				Util.join(
					 "\n------------------------------------------"
					,"\n-- Generate IntegrationId "
					,"\n------------------------------------------"
					,"\n-- interface info:", Util.toJSONString(interfaze)

				));

		Channel channel  = interfaze.getChannel();
		if(channel == null || channel.getChannelCd() == null) {
			throw new IdSelectorException(Util.join("The IIP is fail to generate interface[",interfaze.getInterfaceNm(),"] id authomtically because there is no channel info or channel cd"));
		}

		String channelCd = channel.getChannelCd();
		if(CHANNEL_CD_IIP.equals(channelCd)){
			List<pep.per.mint.common.data.basic.System> systems  = interfaze.getSystemList();
			if(systems == null || systems.size() == 0){
				throw new IdSelectorException(Util.join("The IIP is fail to generate interface[",interfaze.getInterfaceNm(),"] id authomtically because there is no system info"));
			}
			boolean hasRFC = false;
			boolean hasWS = false;
			boolean isSendResourceFile = false;
			boolean isRecvResourceFile = false;
			for(pep.per.mint.common.data.basic.System system : systems){
				if(RESOURCE_CD_RFC.equalsIgnoreCase(system.getResourceCd())){
					hasRFC = true;
					break;
				}
				if(RESOURCE_CD_WS.equalsIgnoreCase(system.getResourceCd())){
					hasWS = true;
				}
				if(RESOURCE_CD_FILE.equalsIgnoreCase(system.getResourceCd())
						&& pep.per.mint.common.data.basic.System.NODE_TYPE_SENDER.equalsIgnoreCase(system.getNodeType())){
					isSendResourceFile = true;
				}
				if(RESOURCE_CD_FILE.equalsIgnoreCase(system.getResourceCd())
						&& pep.per.mint.common.data.basic.System.NODE_TYPE_RECEIVER.equalsIgnoreCase(system.getNodeType())){
					isRecvResourceFile = true;
				}
			}

			if(hasRFC){
				integrationId.append(PF_SR);
			}else if(hasWS){
				integrationId.append(PF_WS);
			}else if(isSendResourceFile && isRecvResourceFile){
				integrationId.append(PF_FL);
			}else{
				throw new IdSelectorException(Util.join("The IIP is fail to generate interface[",interfaze.getInterfaceNm(),"] id authomtically because the unmatched RESOURCE CD"));
			}


		}else if(CHANNEL_CD_WEBMETHOD.equals(channelCd)){
			integrationId.append(PF_WM);
		}else if(CHANNEL_CD_DIRECT.equals(channelCd)){
			integrationId.append(PF_DR);
		}else{
			throw new IdSelectorException(
					Util.join(
							"The IIP is fail to generate interface[",interfaze.getInterfaceNm(),"] id authomtically because the unknown CHANNEL CD :"
						    ,channelCd
						    ,"\n"
						    ,"The channel cd value is one of ["
						    ,CHANNEL_CD_IIP,":IIP, "
						    ,CHANNEL_CD_WEBMETHOD,":Webmethod, "
						    ,CHANNEL_CD_DIRECT,":Direct]"
					));
		}

		String prefix = integrationId.toString();
		String index = commonService.generateIntegrationId(prefix, SEQ_BEGIN_INDEX, SEQ_END_INDEX, SEQ_LENGTH);

		integrationId.append(index);

		logger.debug(Util.join("The interface id generated by IIP is :",integrationId.toString()));

		if(integrationId.toString().length() != ID_LENGTH){
			throw new IdSelectorException(Util.join("The IIP is fail to generate interface[",interfaze.getInterfaceNm(),"] id authomtically because the length[", integrationId.toString().length() ,"] of generated interface id is not valid.[The length must be ", ID_LENGTH ,"]"));
		}

		return integrationId.toString();
	}

}
