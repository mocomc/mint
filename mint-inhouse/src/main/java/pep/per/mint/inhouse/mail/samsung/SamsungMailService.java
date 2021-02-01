package pep.per.mint.inhouse.mail.samsung;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


import org.springframework.web.client.RestTemplate;

import pep.per.mint.common.data.basic.TRLogDetail;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.inhouse.mail.SendMailService;


public class SamsungMailService implements SendMailService {

	Logger logger = LoggerFactory.getLogger(SamsungMailService.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	CommonService commonService;

	String sendMailAddress;

	String authToken;

	String sendMailServiceUrl;

	String templatePath = "/template/email.samsung.template.tpl";

	public void init() throws Exception{

		Map<String, List<String>> environmentalValues = commonService.getEnvironmentalValues();

		boolean useSamsungMail = false;
    	{
	    	String useSamsungMailKey = "inhouse.mail.samsung.use";
	    	List<String> values = environmentalValues.get(useSamsungMailKey);
			if(!Util.isEmpty(values)){
				useSamsungMail = Boolean.parseBoolean((values.get(0)));
			}
    	}

    	if(useSamsungMail){
			{
			  	String authTokenKey = "inhouse.mail.samsung.auth.token";
				List<String> values = environmentalValues.get(authTokenKey);
				if (values != null && values.size() > 0) {
					authToken = values.get(0);
					logger.debug(Util.join(authTokenKey,":",authToken));
				}else{
					throw new Exception(Util.join("Can't initialize ", this.getClass(), " because there is no values of ", authTokenKey));
				}
			}

			{
				String sendMailAddressKey = "inhouse.mail.samsung.send.mail.address";
				List<String> values = environmentalValues.get(sendMailAddressKey);
				if (values != null && values.size() > 0) {
					sendMailAddress = values.get(0);
					logger.debug(Util.join(sendMailAddressKey,":",sendMailAddress));
				}else{
					throw new Exception(Util.join("Can't initialize ", this.getClass(), " because there is no values of ", sendMailAddressKey));
				}
			}

			{
				String sendMailServiceUrlKey = "inhouse.mail.samsung.send.mail.service.url";
				List<String> values = environmentalValues.get(sendMailServiceUrlKey);
				if (values != null && values.size() > 0) {
					sendMailServiceUrl = values.get(0);
					logger.debug(Util.join(sendMailServiceUrlKey,":",sendMailServiceUrl));
				}else{
					throw new Exception(Util.join("Can't initialize ", this.getClass(), " because there is no values of ", sendMailServiceUrlKey));
				}
			}
    	}
	}

	public final static String DEFAULT_HEADER_ACCEPT = "application/json";

	/**
	 * <p>samsung square mail 서비스 연동 스펙 </p>
	 * <pre>
	 * <code>
	 * mail service url : POST https://openapi.portal.samsungsquare.com/mail/sub/api/basic/v1.0/sendMail
	 * http headers :
	 * 	Accept : application/json
	 * 	Content-Type : application/json
	 * 	Authorization : Bearer 1992e2fca4d7638dbebc15d80b5a198a
	 * http body :
	 * {
     *    "resourceVO":{
     *    	"email":"inah.yoo@samsungsquare.com",
     *      "localeStr":"ko_KR",
     *      "encoding":"utf-8",
     *      "timeZone": "GMT+9"
     *    },
     *    "sendMailVO" : {
     *      "contentText":"<STRING><H1>추가된 사용자 계정으로 메일전송 테스트해봅니다.</H1></STRING><P>잘 받았는지 아래 주소로 답장 한번 주세요.</P><P><a href='mailto:whoana@gmail.com?Subject=주말 잘 보내세요.' target='_top'>Reply Mail</a></P>",
     *      "subject":"S-Printing 이메일 연동 건",
     *      "docSecuType":"PERSONAL",
     *      "contentType":"html",
     *      "from":{"email":"inah.yoo@samsungsquare.com"},
     *      "attachs":[],
     *      "recipients":[
     *        {"email":"whoana@gmail.com","recipientType":"TO"},
     *        {"email":"inah.yoo@samsungsquare.com","recipientType":"TO"}
     *      ]
     *    }
	 * }
	 *
	 * </code>
	 * </pre>
	 */
	public void sendMail(Map<String, ?> params) throws Exception {

		HttpHeaders headers = new HttpHeaders();
		{
			//headers.setContentType(new MediaType("application", "json", Charset.forName("utf-8")));
			headers.add("Content-Type", DEFAULT_HEADER_ACCEPT);
			headers.add("Accept", DEFAULT_HEADER_ACCEPT);
			headers.add("Authorization", authToken);
		}

		//--------------------------------------------------------------------
		//http body setting
		//--------------------------------------------------------------------
		MailMessage msg = new MailMessage();

		ResourceVO resourceVO = new ResourceVO();
		resourceVO.setEmail(sendMailAddress);

		SendMailVO sendMailVO = new SendMailVO();
		String contents = (String)params.get("contents");
		sendMailVO.setContentText(contents);
		String subject = (String)params.get("subject");
		sendMailVO.setSubject(subject);
		FromInfo from = new FromInfo();
		from.setEmail(sendMailAddress);
		sendMailVO.setFrom(from);

		List<String> recipients = (List<String>)params.get("recipients");
		List<RecipientInfo> recipientInfos = new ArrayList<RecipientInfo>();
		{
			for (String email : recipients) {
				RecipientInfo recipient =  new RecipientInfo();
				recipient.setEmail(email);
				recipientInfos.add(recipient);
			}
		}
		sendMailVO.setRecipients(recipientInfos);

		msg.setResourceVO(resourceVO);
		msg.setSendMailVO(sendMailVO);


		//--------------------------------------------------------------------
		//http request send
		//--------------------------------------------------------------------
		HttpEntity<MailMessage> requestEntity = null;
		//HttpHeaders responseHeaders = null;
		ResponseEntity<Map> responseEntity = null;
		{
			requestEntity = new HttpEntity<MailMessage>(msg, headers);
			responseEntity = restTemplate.exchange(sendMailServiceUrl, HttpMethod.POST, requestEntity, Map.class);
			//responseHeaders = responseEntity.getHeaders();
			if(logger != null && logger.isDebugEnabled()) {
				try{
					logger.debug(Util.join("responseEntity:",Util.toJSONString(responseEntity)));
				}catch(Exception e){}
			}
			/*
			 * {
			 *	"attachVO": null,
			 *	"success": true,
			 *	"successCount": 0,
			 *	"failList": null,
			 *	"failKeyList": null,
			 *	"resultStr": "20170811010352an2s1a04a712c5a3ffa2e76b2cb408985138a4b6",
			 *	"resultInt": 0,
			 *	"resultLong": 0,
			 *	"resultStrArray": null,
			 *	"resultMap": null,
			 *	"successMessage": null,
			 *	"resultBaos": null,
			 *	"resultStrList": null,
			 *	"sendMailVO": null
			 *	}
			 */
			Map res = responseEntity.getBody();
			boolean success = (Boolean) res.get("success");
			String resultStr = (String) res.get("resultStr");

			String successMessage = (String) res.get("successMessage");

			if(!success){
				throw new Exception(Util.join("fail to send Eamil:{\"success\":",success,"\",resultStr\":",resultStr,"\",successMessage\":",successMessage,"}"));
			}

		}
	}



	@Override
	public String buildContents(Map<String, ?> params) throws Exception {
		URL template = null;
		InputStream is = null;
		String contents = "";
		try{

			template = getClass().getResource(templatePath);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			is = template.openStream();
			while(true){
				byte[] b = new byte[1024];
				int res = is.read(b);
				if(res == -1) break;
				baos.write(b);
			}
			contents = new String(baos.toByteArray());

			/* replace template var
				${integrationId}
				${interfaceNm}
				${channelNm}
				${businessNm}
				${dataPrMethodNm}
				${dataPrDirNm}
				${appPrMethodNm}
				${dataFrequency}
				${systemList}
				${nodeList}
				${msgContents}
				${errorContents}
			*/

			String integrationId = (String)params.get("integrationId");
			String interfaceNm = (String)params.get("interfaceNm");
			String channelNm = (String)params.get("channelNm");
			String businessNm = (String)params.get("businessNm");
			String dataPrMethodNm = (String)params.get("dataPrMethodNm");
			String dataPrDirNm = (String)params.get("dataPrDirNm");
			String appPrMethodNm = (String)params.get("appPrMethodNm");
			String dataFrequency = (String)params.get("dataFrequency");

			List<pep.per.mint.common.data.basic.System> systemInfoList = (List<pep.per.mint.common.data.basic.System>)params.get("systemList");
			String systemList = "";
			if(!Util.isEmpty(systemInfoList)){
				StringBuffer items = new StringBuffer();
				for (pep.per.mint.common.data.basic.System systemInfo : systemInfoList) {
					items.append("<tr style='height: 30px;'>");
					items.append("<td align='center'>").append(systemInfo.getNodeTypeNm()).append("</td>");
					items.append("<td align='center'>").append(systemInfo.getSystemNm()).append("</td>");
					items.append("<td align='center'>").append(systemInfo.getResourceNm()).append("</td>");
					items.append("<td align='center'>").append(systemInfo.getSystemNm()).append("</td>");
					items.append("</tr>\n\t");
				}
				systemList = items.toString();
			}

			List<TRLogDetail> detailList = (List<TRLogDetail>)params.get("nodeList");
			String nodeList = "";
			StringBuffer msgContents = new StringBuffer();
			StringBuffer errorContents = new StringBuffer();

			if(!Util.isEmpty(detailList)){
				StringBuffer items = new StringBuffer();
				for (TRLogDetail trLogDetail : detailList) {

					items.append("<tr style='height: 30px;'>");
					items.append("<td align='center'>").append(trLogDetail.getProcessMode()).append("</td>");
					items.append("<td align='center'>").append(trLogDetail.getHostId()).append("</td>");
					items.append("<td align='center'>").append(trLogDetail.getProcessId()).append("</td>");
					items.append("<td align='center'>").append(trLogDetail.getProcessTime()).append("</td>");
					items.append("<td align='center'>").append(trLogDetail.getStatusNm()).append("</td>");
					items.append("<td align='center'>").append(trLogDetail.getDataSize()).append("</td>");
					items.append("<td align='center'>").append(trLogDetail.getCompressYn()).append("</td>");
					items.append("<td align='center'>").append(trLogDetail.getDirectoryNm()).append("</td>");
					items.append("<td align='center'>").append(trLogDetail.getFileNm()).append("</td>");
					items.append("</tr>\n\t");

					String errMsg = trLogDetail.getErrorMsg();
					if(!Util.isEmpty(errMsg)){
						errorContents.append(trLogDetail.getProcessMode()).append(" error:").append(errMsg).append("\n");
					}
					String data = trLogDetail.getMsgToString();
					//logger.debug("whowho data:" + data);
					if(!Util.isEmpty(data)){
						msgContents.append(trLogDetail.getProcessMode()).append(" data:").append(data).append("\n");
					}
				}
				nodeList = items.toString();
			}



			contents = contents.replaceAll(Pattern.quote("${integrationId}"), Util.isEmpty(integrationId) ? "&nbsp;" : Matcher.quoteReplacement(integrationId));
			contents = contents.replaceAll(Pattern.quote("${interfaceNm}"), Util.isEmpty(interfaceNm) ? "&nbsp;" : Matcher.quoteReplacement(interfaceNm));
			contents = contents.replaceAll(Pattern.quote("${channelNm}"), Util.isEmpty(channelNm) ? "&nbsp;" : Matcher.quoteReplacement(channelNm));
			contents = contents.replaceAll(Pattern.quote("${businessNm}"), Util.isEmpty(businessNm) ? "&nbsp;" : Matcher.quoteReplacement(businessNm));
			contents = contents.replaceAll(Pattern.quote("${dataPrMethodNm}"), Util.isEmpty(dataPrMethodNm) ? "&nbsp;" : Matcher.quoteReplacement(dataPrMethodNm));
			contents = contents.replaceAll(Pattern.quote("${dataPrDirNm}"), Util.isEmpty(dataPrDirNm) ? "&nbsp;" : Matcher.quoteReplacement(dataPrDirNm));
			contents = contents.replaceAll(Pattern.quote("${appPrMethodNm}"), Util.isEmpty(appPrMethodNm) ? "&nbsp;" : Matcher.quoteReplacement(appPrMethodNm));
			contents = contents.replaceAll(Pattern.quote("${dataFrequency}"), Util.isEmpty(dataFrequency) ? "&nbsp;" : Matcher.quoteReplacement(dataFrequency));
			contents = contents.replaceAll(Pattern.quote("${systemList}"), Util.isEmpty(systemList) ? "&nbsp;" : Matcher.quoteReplacement(systemList));
			contents = contents.replaceAll(Pattern.quote("${nodeList}"), Util.isEmpty(nodeList) ? "&nbsp;" : Matcher.quoteReplacement(nodeList));
			contents = contents.replaceAll(Pattern.quote("${errorContents}"), Util.isEmpty(errorContents) ? "&nbsp;" : Matcher.quoteReplacement(errorContents.toString()));
            //삼성전자S-Printing 요구사항 
            //메시지 내용은 불필요함.
            //contents = contents.replaceAll(Pattern.quote("${msgContents}"), Util.isEmpty(msgContents) ? "&nbsp;" : Matcher.quoteReplacement(msgContents.toString()));

			return contents;

		}finally{
			if(is != null) is.close();
		}
	}

	@Override
	public String getSendMailAddress() {
		return sendMailAddress;
	}

	@Override
	public String getSubject(Map<String, ?> params) {
		String interfaceNm = (String)params.get("interfaceNm");
		String integrationId = (String)params.get("integrationId");
		String subject = Util.join("인터페이스 에러발생 알림[",interfaceNm,"(",integrationId,")]");
		return subject;
	}

}
