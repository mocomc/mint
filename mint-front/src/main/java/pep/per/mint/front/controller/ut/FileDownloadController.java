/**
 *
 */
package pep.per.mint.front.controller.ut;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.StringUtil;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.front.env.FrontEnvironments;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.MessageSourceUtil;

/**
 * The type File download controller.
 */
@Controller
@RequestMapping("/ut")
public class FileDownloadController {

	/**
	 * The Logger.
	 */
	Logger logger = LoggerFactory.getLogger(FileDownloadController.class);


	/**
	 * The Message source.
	 */
// 비지니스처리중 프론트까지 전달할 메시지들을 참조할 수 있는 다국어지원용 번들 객체
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

	@Autowired
	private ServletContext servletContext;

	/**
	 * The Env.
	 */
	@Autowired
	FrontEnvironments env;

	@Autowired
	CommonService commonService;
	//private static final int BUFFER_SIZE = 4906;


	/**
	 * Handle file download.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws IOException the iO exception
	 * @throws IOException the iO exception
	 * @throws IOException the iO exception
     */
	@RequestMapping(value="/download", method=RequestMethod.GET)
	//@RequestMapping(value = "/download", params = { "method=GET","isTest=true" }, method = RequestMethod.POST, headers = "content-type=application/json")
	public void handleFileDownload(
			HttpServletRequest request,
			//HttpSession httpSession,
			//@RequestBody ComMessage<Map<String,Object>, Object> comMessage,
			//Locale locale,
			HttpServletResponse response) throws IOException, Exception, ControllerException {


		FileInputStream fileInputStream = null;
		OutputStream outStream = null;

		try {

        	String attachFileRepPath = env.getAttachFilePath();

        	String realPath = attachFileRepPath;

			/*
			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();

			boolean checkSession = comMessage.getCheckSession();
			if(checkSession) {
				//--------------------------------------------------
				//데이터액세스권한용 사용자정보세팅
				//--------------------------------------------------
				{
					String errorCd = "";
					String errorMsg = "";

					User user = (User) httpSession.getAttribute("user");

					if (user == null) {
						errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.session.timeout", locale);
						String sessionInfo = Util.join(
								"\nsession id:", httpSession.getId(),
								"\nCreationTime:", new Date(httpSession.getCreationTime()),
								"\nLastAccessedTime:", new Date(httpSession.getLastAccessedTime()),
								"\nMaxInactiveInterval(sec):", httpSession.getMaxInactiveInterval()
						);
						Object[] errorMsgParams = {"RequirementController.getRequirementList", sessionInfo};
						errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.session.timeout", errorMsgParams, locale);
						logger.debug(sessionInfo);
						throw new ControllerException(errorCd, errorMsg);
					}

					params.put("userId", user.getUserId());
					params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());
				}
			}else{
				params.put("userId", comMessage.getUserId());
			}

			String fullPath = (String) params.get("filepath");
			logger.debug("\n\n\n  paramFullPath["+fullPath+"]");

			fullPath = new String(fullPath.getBytes("iso-8859-1"), "utf-8");
			logger.debug("\n\n\n  encodeFullPath["+fullPath+"]");

			String originalFileName = (String) params.get("filename");  // 임시 DB에서 구해와야 할것.
			logger.debug("\n\n\n  originalFileName["+originalFileName+"]");

			*/

			String paramPath = request.getParameter("filepath");
			logger.debug("\n\n\n  paramFullPath["+paramPath+"]");
			//fullPath = new String(fullPath.getBytes("iso-8859-1"), "utf-8");
			//logger.debug("\n\n\n  encodeFullPath["+fullPath+"]");
			if(checkDownloadPath(paramPath)){
				logger.error("download path invalidate. ["+paramPath+"]");
				throw new IOException("download path invalidate. ["+paramPath+"]");
			}

			String fullPath = realPath + File.separator + paramPath;
			logger.debug("fullPath.["+fullPath+"]");


			File downloadFile = new File(fullPath);
			if(checkDownloadPath2(realPath, downloadFile)){
				logger.error("download path invalidate2. ["+paramPath+"]");
				throw new IOException("download path invalidate2. ["+paramPath+"]");
			}


			if (!downloadFile.exists()) {
				logger.error("download file is Not Exist. ["+downloadFile.getPath()+"]");
				throw new IOException("download file is Not Exist. ["+downloadFile.getPath()+"]");
			}


			String originalFileName = request.getParameter("filename");
			//logger.debug("\n\n\n  paramFileName["+originalFileName+"]");
			//originalFileName = new String(originalFileName.getBytes("iso-8859-1"), "utf-8");
			//logger.debug("\n\n\n  encodeFileName["+originalFileName+"]");

/*
			logger.debug("\n\n\nfileName["+originalFileName+"]");

			logger.debug("utf-8 -> euc-kr        : " + new String(originalFileName.getBytes("utf-8"), "euc-kr"));
			logger.debug("utf-8 -> ksc5601       : " + new String(originalFileName.getBytes("utf-8"), "ksc5601"));
			logger.debug("utf-8 -> x-windows-949 : " + new String(originalFileName.getBytes("utf-8"), "x-windows-949"));
			logger.debug("utf-8 -> iso-8859-1    : " + new String(originalFileName.getBytes("utf-8"), "iso-8859-1"));
			logger.debug("iso-8859-1 -> euc-kr        : " + new String(originalFileName.getBytes("iso-8859-1"), "euc-kr"));
			logger.debug("iso-8859-1 -> ksc5601       : " + new String(originalFileName.getBytes("iso-8859-1"), "ksc5601"));
			logger.debug("iso-8859-1 -> x-windows-949 : " + new String(originalFileName.getBytes("iso-8859-1"), "x-windows-949"));
			logger.debug("iso-8859-1 -> utf-8         : " + new String(originalFileName.getBytes("iso-8859-1"), "utf-8"));
			logger.debug("euc-kr -> utf-8         : " + new String(originalFileName.getBytes("euc-kr"), "utf-8"));
			logger.debug("euc-kr -> ksc5601       : " + new String(originalFileName.getBytes("euc-kr"), "ksc5601"));
			logger.debug("euc-kr -> x-windows-949 : " + new String(originalFileName.getBytes("euc-kr"), "x-windows-949"));
			logger.debug("euc-kr -> iso-8859-1    : " + new String(originalFileName.getBytes("euc-kr"), "iso-8859-1"));
			logger.debug("ksc5601 -> euc-kr        : " + new String(originalFileName.getBytes("ksc5601"), "euc-kr"));
			logger.debug("ksc5601 -> utf-8         : " + new String(originalFileName.getBytes("ksc5601"), "utf-8"));
			logger.debug("ksc5601 -> x-windows-949 : " + new String(originalFileName.getBytes("ksc5601"), "x-windows-949"));
			logger.debug("ksc5601 -> iso-8859-1    : " + new String(originalFileName.getBytes("ksc5601"), "iso-8859-1"));
			logger.debug("x-windows-949 -> euc-kr     : " + new String(originalFileName.getBytes("x-windows-949"), "euc-kr"));
			logger.debug("x-windows-949 -> utf-8      : " + new String(originalFileName.getBytes("x-windows-949"), "utf-8"));
			logger.debug("x-windows-949 -> ksc5601    : " + new String(originalFileName.getBytes("x-windows-949"), "ksc5601"));
			logger.debug("x-windows-949 -> iso-8859-1 : " + new String(originalFileName.getBytes("x-windows-949"), "iso-8859-1"));
*/

			{
				response.setContentType("application/octet-stream; charset=utf-8;");
				response.setContentLength((int) downloadFile.length());

				String userAgent = request.getHeader("User-Agent");

				// 브라우저별 인코딩
				String fileName = null;
				if(userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1) {
					fileName = URLEncoder.encode(originalFileName, "UTF-8").replaceAll("\\+", "%20");
				}
				else if (userAgent.indexOf("Chrome") > -1) {

					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < originalFileName.length(); i++) {
						char c = originalFileName.charAt(i);
						if (c > '~') {
							sb.append(URLEncoder.encode("" + c, "UTF-8"));
						} else {
							sb.append(c);
						}
					}
					fileName = sb.toString();
				}
				else if (userAgent.indexOf("Opera") > -1) {

					fileName = "\"" + new String(originalFileName.getBytes("UTF-8"), "8859_1") + "\"";
				}
				else { // fireFox... etc...
					fileName = "\"" + new String(originalFileName.getBytes("UTF-8"), "8859_1") + "\"";
				}



				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
				response.setHeader("Content-Transfer-Encoding", "binary");


				outStream = response.getOutputStream();

				fileInputStream = new FileInputStream(downloadFile);
				FileCopyUtils.copy(fileInputStream, outStream);


				outStream.flush();


			}
		}
		catch (IOException ioex) {
			throw ioex;
		}
		catch (Exception ex) {
			throw ex;
		}
		finally {
			if (fileInputStream != null) { try { fileInputStream.close(); } catch (Exception e) { e.printStackTrace(); } }
			if (outStream != null) { try { outStream.close(); } catch (Exception e) { e.printStackTrace(); } }
		}

	}


	private boolean checkDownloadPath2(String userHomePath, File docFile) {
		File tmpHomeFile = new File(userHomePath);
		String tmpHomePath = tmpHomeFile.getAbsolutePath();
		String filePath = docFile.getAbsolutePath();
		if(tmpHomePath.equals(filePath.substring(0, tmpHomePath.length()))){
			return false;
		}else{
			return true;
		}
	}


	private boolean checkDownloadPath(String requestPath) {
		boolean isCheck= true;
		String[] strArr = StringUtil.strToStrArray(requestPath, File.separator);
		if(strArr.length==2){
			isCheck= false;
		}

		if(requestPath.indexOf("..")>=0){
			isCheck = true;
		}

		return isCheck;
	}
}
