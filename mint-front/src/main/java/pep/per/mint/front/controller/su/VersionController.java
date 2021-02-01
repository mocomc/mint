package pep.per.mint.front.controller.su;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.VersionInfo;
import pep.per.mint.common.util.Util;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.MessageSourceUtil;

/**
 * <pre>
 * 향후 사이트별 버전 컨트롤을 위한 용도로 확장 개발한다.
 * 예를 들어 현재 inhouse-context.xml에 설정된 값이나
 * 기타 사이트별 배포시 달라지는 환경값들을 관리하거나
 * 원격에서 올바른 배포가 이루어 졌는지 확인하는 기능들을 추가하도록 한다.
 * <pre>
 * @author whoana
 *
 */
@Controller
@RequestMapping("/su")
public class VersionController {

    Logger logger = LoggerFactory.getLogger(VersionController.class);

    @Autowired
    ReloadableResourceBundleMessageSource messageSource;

    @Autowired
    private ServletContext servletContext;

    String versionFilePath = "/versions/version.properties";

    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/version", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody
    ComMessage<Map<String, Object>, VersionInfo> getVersionInfo(
            HttpSession httpSession,
            @RequestBody ComMessage<Map<String, Object>, VersionInfo> comMessage,
            Locale locale, HttpServletRequest request) throws Exception, ControllerException {

        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            VersionInfo version = new VersionInfo(versionFilePath);  
            {
                String ip = null;
                try{
                     ip = InetAddress.getLocalHost().getHostAddress();
                }catch(UnknownHostException e){
                    logger.error("버전정보중 IP 얻기 실패 , 무시해도 되는 예외:",e);
                    ip = request.getServerName();
                }
                version.setServerAddress(Util.join(ip,":",request.getServerPort()));            
            }
            logger.debug("\n\nversionInfo:" + Util.join(Util.toJSONString(version)));

            //--------------------------------------------------
            // 서비스 처리 종료시간을 얻어 CM에 세팅한다.
            //--------------------------------------------------
            {
                comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
            }
            //--------------------------------------------------
            // 통신메시지에 처리결과 코드/메시지를 등록한다.
            //--------------------------------------------------
            {
                comMessage.setResponseObject(version);
                comMessage.setErrorCd(MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale));
                comMessage.setErrorMsg(MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale));
            }
            return comMessage;
        }
    }

}
