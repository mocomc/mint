/*
 * Copyright © 2016 - 2030 TA MOCOMSYS, All rights reserved.
 */
package pep.per.mint.inhouse;

import com.mocomsys.iip.inhouse.excel.DefaultExcelUploadService;
import com.mocomsys.iip.inhouse.excel.InHouseExcelUploadService;
import com.mocomsys.iip.inhouse.excel.HdinsExcelUploadService;
import com.mocomsys.iip.inhouse.excel.SSHPExcelUploadService;
import com.mocomsys.iip.inhouse.excel.GSSPExcelUploadService;
import com.mocomsys.iip.inhouse.selector.hdins.HDINSIdSelector;
import com.mocomsys.iip.inhouse.selector.kics.KICSIdSelector;
import com.mocomsys.iip.inhouse.selector.shl.SHLIdSelector;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import pep.per.mint.database.service.inhouse.IExcelUploadService;
import pep.per.mint.database.service.selector.IdSelector;
import pep.per.mint.common.data.basic.VersionInfo;

import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.inhouse.mail.SendMailService;
import pep.per.mint.inhouse.mail.SimpleSendMailService;
import pep.per.mint.inhouse.mail.samsung.SamsungMailService;
import pep.per.mint.inhouse.selector.sprinting.SSHPIdSelector;
import pep.per.mint.inhouse.sms.LogSMSService;
import pep.per.mint.inhouse.sms.SendSMSService;
import pep.per.mint.inhouse.sms.kab.KABSMSService;
import pep.per.mint.inhouse.sms.nh.NHSMSService;
import pep.per.mint.inhouse.sso.ILoginService;
import pep.per.mint.inhouse.sso.hdins.LoginService;
import pep.per.mint.inhouse.sso.nh.NHLoginService;


/**
 * mint-inhouse 모듈 로딩을 위한 JavaConfiguration Class
 * 배포 사이트별로 달리 로딩될 빈을 다이나믹하게 결정할 수 있도록 하기위해 작성함.
 * @author whoana
 */
@Configuration
@ComponentScan({
    "pep.per.mint.database.service.co",
    "com.mocomsys.iip.inhouse.excel",
    "com.mocomsys.iip.inhouse.selector.hdins",
    "com.mocomsys.iip.inhouse.selector.shl",
    "com.mocomsys.iip.inhouse.sms.nh",
    "com.mocomsys.iip.inhouse.sms.kab",
    "pep.per.mint.inhouse.selector.sprinting",
    "com.mocomsys.iip.inhouse.selector.kics"})
public class InhouseConfig {

    public static final String SITE_CD_DEFAULT = "DEFAULT";
    public static final String SITE_CD_KICS = "KICS";
    public static final String SITE_CD_HDINS = "HDINS";
    public static final String SITE_CD_SSHP = "SSHP";
    public static final String SITE_CD_GSSP = "GSSP";
    public static final String SITE_CD_NHXX = "NH";
    public static final String SITE_CD_KAB = "KAB";
    public static final String SITE_CD_SHL = "SHL";

    Logger logger = LoggerFactory.getLogger(InhouseConfig.class);

    @Autowired
    CommonService commonService;

    String versionFilePath = "/versions/version.properties";

    VersionInfo version = null;

    String systemSiteCode;
    /**
     *
     * @throws Exception
     */
    public InhouseConfig() throws Exception{
        logger.info("-------------------------------------");
        logger.info("inhouseConfig creating...");
        logger.info("-------------------------------------");
        version = new VersionInfo(versionFilePath);
    }

    /**
     * 서버로그 검색시 키워드 : inhouseConfig:getExcelUploadService(
     * [ Excel Upload Service Define ]
     *		- COMMON :: com.mocomsys.iip.inhouse.excel.DefaultExcelUploadService
     *  	- KICS   :: com.mocomsys.iip.inhouse.excel.InHouseExcelUploadService
     *  	- HDINS  :: com.mocomsys.iip.inhouse.excel.HdinsExcelUploadService
     *		- SSHP   :: com.mocomsys.iip.inhouse.excel.SSHPExcelUploadService
     * @return
     * @throws Exception
     */
    @Bean
    public IExcelUploadService getExcelUploadService() throws Exception{
        Map<String, List<String>> environmentalValues = commonService.getEnvironmentalValues();
        try {
            List<String> siteCodeValues = environmentalValues.get("system.site.code");
            if (siteCodeValues == null || siteCodeValues.isEmpty()) {
                logger.debug(Util.join("포털환경설정값[system.site.code] 세팅값이 등록되지 않아 기본 값 기본값[",SITE_CD_DEFAULT,"]으로 지정한다."));
            }else {
                systemSiteCode = siteCodeValues.get(0);
            }
        } catch (Throwable e) {
            logger.error(Util.join("포털환경설정값[system.site.code] 세팅중 예외발생하여 기본 값 기본값[",SITE_CD_DEFAULT,"]으로 지정한다."), e);
        }

        IExcelUploadService res = null;
        String cd = systemSiteCode == null ? version.getSiteCode() : systemSiteCode;
        if(!Util.isEmpty(cd)){
            if(SITE_CD_KICS.equals(cd)){
                res = new InHouseExcelUploadService();
            }else if(SITE_CD_HDINS.equals(cd)){
                res = new HdinsExcelUploadService();
            }else if(SITE_CD_SSHP.equals(cd)){
                res = new SSHPExcelUploadService();
            }else if(SITE_CD_GSSP.equals(cd)){
                res = new GSSPExcelUploadService();
            }else{
                res = new DefaultExcelUploadService();
                //throw new Exception(Util.join("Exception occurred at InhouseConfig.getExcelUploadService : version.siteCode[",cd,"] invalid site cd"));
            }
        }else{
            res = new DefaultExcelUploadService();
        }
        logger.info(Util.join("inhouseConfig:getExcelUploadService(",cd,")->", res.getClass().getName()));
        return res;
    }

    /**
     * 서버로그 검색시 키워드 : inhouseConfig:getIDSelectorService(
     * 		[ Interface Id Selector Service Define ]
     *          - KICS,SSHP   :: com.mocomsys.iip.inhouse.selector.kics.KICSIdSelector
     *          - HDINS       :: com.mocomsys.iip.inhouse.selector.hdins.HDINSIdSelector
     *          - SSHP	      :: pep.per.mint.inhouse.selector.sprinting.SSHPIdSelector
     * @return
     * @throws Exception
     */
    @Bean
    public IdSelector getIDSelectorService() throws Exception{

        Map<String, List<String>> environmentalValues = commonService.getEnvironmentalValues();
        try {
            List<String> siteCodeValues = environmentalValues.get("system.site.code");
            if (siteCodeValues == null || siteCodeValues.isEmpty()) {
                logger.debug(Util.join("포털환경설정값[system.site.code] 세팅값이 등록되지 않아 기본 값 기본값[",SITE_CD_DEFAULT,"]으로 지정한다."));
            }else {
                systemSiteCode = siteCodeValues.get(0);
            }
        } catch (Throwable e) {
            logger.error(Util.join("포털환경설정값[system.site.code] 세팅중 예외발생하여 기본 값 기본값[",SITE_CD_DEFAULT,"]으로 지정한다."), e);
        }



        IdSelector res = null;
        String cd = Util.isEmpty(systemSiteCode) ? version.getSiteCode() : systemSiteCode;
        if(!Util.isEmpty(cd)){
            if(SITE_CD_KICS.equals(cd)){
                res = new KICSIdSelector();
            }else if(SITE_CD_HDINS.equals(cd)){
                res = new HDINSIdSelector();
            }else if(SITE_CD_SSHP.equals(cd)){
                res = new SSHPIdSelector();
            }else if(SITE_CD_SHL.equals(cd)){
                res = new SHLIdSelector();
            }else{
                res = new KICSIdSelector();
                //throw new Exception(Util.join("Exception occurred at InhouseConfig.getIDSelectorService : version.siteCode[",cd,"] invalid site cd"));            }
            }
        }else{
            res = new KICSIdSelector();
        }
        logger.info(Util.join("inhouseConfig:getIDSelectorService(",cd,")->", res.getClass().getName()));
        return res;
    }

    @Bean(initMethod = "init")
    public ILoginService getLoginService() throws Exception{
    	Map<String, List<String>> environmentalValues = commonService.getEnvironmentalValues();
        try {
            List<String> siteCodeValues = environmentalValues.get("system.site.code");
            if (siteCodeValues == null || siteCodeValues.isEmpty()) {
                logger.debug(Util.join("포털환경설정값[system.site.code] 세팅값이 등록되지 않아 기본 값 기본값[",SITE_CD_DEFAULT,"]으로 지정한다."));
            }else {
                systemSiteCode = siteCodeValues.get(0);
            }
        } catch (Throwable e) {
            logger.error(Util.join("포털환경설정값[system.site.code] 세팅중 예외발생하여 기본 값 기본값[",SITE_CD_DEFAULT,"]으로 지정한다."), e);
        }

        ILoginService res = null;
        String cd = Util.isEmpty(systemSiteCode) ? version.getSiteCode() : systemSiteCode;
        if(!Util.isEmpty(cd)){
            if(SITE_CD_NHXX.equals(cd)){
                res = new NHLoginService();
            }else{
            	res = new LoginService();
            }
        }else{
        	res = new LoginService();
        }
        logger.info(Util.join("inhouseConfig:getLoginService(",cd,")->", res.getClass().getName()));
        return res;
    }

    @Bean(initMethod = "init", name = {"trackingMailService"})
    public SendMailService getTrackingMailService() throws Exception{
        return new SamsungMailService();
    }

    @Bean(initMethod = "init", name = {"simpleSendMailService"})
    public SendMailService getSimpleSendMailService() throws Exception{
        return new SimpleSendMailService();
    }

    /**
     * 서버로그 검색시 키워드 : inhouseConfig:getSendSMSService(
     * 		[ Interface SMS Service Define ]
     *          - NHXX   :: com.mocomsys.iip.inhouse.sms.nh.NHSMSService
     * @return
     * @throws Exception
     */
    @Bean
    public SendSMSService getSendSMSService() throws Exception{

        Map<String, List<String>> environmentalValues = commonService.getEnvironmentalValues();
        try {
            List<String> siteCodeValues = environmentalValues.get("system.site.code");
            if (siteCodeValues == null || siteCodeValues.isEmpty()) {
                logger.debug(Util.join("포털환경설정값[system.site.code] 세팅값이 등록되지 않아 기본 값 기본값[",SITE_CD_DEFAULT,"]으로 지정한다."));
            }else {
                systemSiteCode = siteCodeValues.get(0);
            }
        } catch (Throwable e) {
            logger.error(Util.join("포털환경설정값[system.site.code] 세팅중 예외발생하여 기본 값 기본값[",SITE_CD_DEFAULT,"]으로 지정한다."), e);
        }



        SendSMSService res = null;
        String cd = Util.isEmpty(systemSiteCode) ? version.getSiteCode() : systemSiteCode;
        if(!Util.isEmpty(cd)){
            if(SITE_CD_NHXX.equals(cd)){
                res = new NHSMSService();
            }else if(SITE_CD_KAB.equals(cd)){
            	res = new KABSMSService();
            }else{
            	res = new LogSMSService();
            }
        }else{
        	res = new LogSMSService();
        }
        logger.info(Util.join("inhouseConfig:getSendSMSService(",cd,")->", res.getClass().getName()));
        return res;
    }


}
