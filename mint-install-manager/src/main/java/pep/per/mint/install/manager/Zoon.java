/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.per.mint.install.manager;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Log4jConfigurer;
import pep.per.mint.common.util.Util;
import static pep.per.mint.common.util.Util.jsonToObject;
import pep.per.mint.install.data.InstallInfo;
import pep.per.mint.install.data.Job;
import pep.per.mint.install.data.Task;
import pep.per.mint.install.job.DefaultJobExecutor;
import pep.per.mint.install.job.JobExecutor;

/**
 *
 * @author whoana
 */
public class Zoon {



    static Logger logger = null;

    final static String RUN_OPT_GET_DATA = "G";
    final static String RUN_OPT_INSTALL = "I";
    final static String RUN_OPT_MIG = "D";
    final static String RUN_OPT_INSTALL_IFM_SCHEMA = "M";
    final static String RUN_OPT_ALL = "A";
    /**
     *
     */
    static Scanner scanner = null;
    static String home;
    static Properties properties = new Properties();
    static String runOption = null;

    private static void loadProperties(String home) throws Exception{

        File propertiesFile = new File(home,"install.properties");
        FileInputStream fis = null;
		try{
			fis = new FileInputStream(propertiesFile);
			byte b[] = new byte[(int)propertiesFile.length()];
			fis.read(b);
            String config = new String(b);

            config.replaceAll("[$]\\{HOME\\}", home);
            InputStream stream = new ByteArrayInputStream(config.replaceAll("[$]\\{HOME\\}", home).getBytes("UTF-8"));

            properties.load(stream);

            System.out.println("properties:" + Util.toJSONString(properties));

		}finally{
			try{if(fis != null) fis.close();}catch(IOException e){}
		}
    }

    private static void check() {
        try{
            home = System.getProperty("ZOON_HOME");
            if(Util.isEmpty(home)){
                System.out.println(getLogBar(80,"="));
                System.out.println("설치관리자 실행시 ZOON_HOME 옵션이 필요합니다.");
                System.out.println("현재 설치관리자를 종료(n 입력)하고 아래 실행 예와 같이 ZOON_HOME 옵션을 주고 설치관리자를 재실행 해주거나");
                System.out.println("[실행 예] java -DZOON_HOME=/home/iip/zoon -jar mint-install-manager-2.0.0.jar");
                System.out.println("[참고] ZOON_HOME 은 IIP설치를 위해 필요한 100, 200번 데이터 스크립트 파일 들을 포함한 설치리소스 기본 위치를 의미합니다.");
                System.out.println("ZOON_HOME 환경변수 옵션을 지금 입력하여 설치를 계속 진행할 수 있습니다.");
                System.out.println(getLogBar(80,"-"));
                System.out.println("ZOON_HOME 옵션 값을 입력하고 설치를 계속해서 진행하시겠습니까? (ZOON_HOME 입력 : y, 종료 : n)");


                String input = scanner.nextLine();

                if(input.trim().equalsIgnoreCase("y")){
                    System.out.println("ZOON_HOME 값을 입력해주세요:");
                    String homeInput = null;
                    while(true){
                        homeInput = scanner.nextLine();
                        if(Util.isEmpty(homeInput)){
                            System.out.println("값이 입력되지 않았습니다. ZOON_HOME 값을 입력해주세요:");
                        }else{
                            if(checkHome(homeInput)){
                                break;
                            }else{
                                System.out.println("값이 유효하지 않습니다(존재하지 않는 위치 또는 파일 없음). ZOON_HOME 값을 입력해주세요:");
                            }
                        }
                    }
                    home = homeInput;
                    System.out.println(getLogBar(80,"-"));
                    System.out.println(Util.join("ZOON_HOME=", home, " 값으로 설치를 진행합니다." ));

                }else{
                    System.out.println(getLogBar(80,"-"));
                    System.out.println("설치관리자를 종료합니다.");
                    System.exit(0);
                }
            }
        }finally{
        }
    }

    /**
     *
     * @param args
     */
    public static void main(String args[]){
        try {
            scanner = new Scanner(System.in);
            check();
            System.setProperty("ZOON_HOME", home);

            //----------------------------------------------------
            //로그위치잡는 환경변수는 먹히지 않는 구나. 찾아보자 시간날때.
            //----------------------------------------------------
            logger = LoggerFactory.getLogger(Zoon.class);
            Log4jConfigurer.setWorkingDirSystemProperty("ZOON_HOME");

            loadProperties(home);
            checkDatabase();
            Zoon zoon = new Zoon();
            zoon.install(args);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally{
            if(scanner != null) scanner.close();
        }
    }

    /**
     * JOB->TASK 호출 패턴의 설치 작업의 엔트리 함수
     * @param args
     */
    public void install(String args[]){
        try {

            String siteCode = (String)properties.get("install.site.code");


            logger.info(getLogBar(80,"="));
            logger.info(Util.join(" ","IIP 설치 작업을 진행한다."));
            logger.info(Util.join(" ","INSTALL SITE CODE :", siteCode));
            logger.info(Util.join(" ","date : ", Util.getFormatedDate()));
            logger.info(getLogBar(80,"-"));

            logger.info(Util.join(" ","args list:"));
            logger.info(getLogBar(80,"-"));
            for(int i = 0 ; i < args.length ; i ++){
               logger.info(Util.join(" ","args[", i ,"]:", args[i]));
               runOption = args[0];
            }

            if(Util.isEmpty(runOption)){
                runOption = question("설치작업 유형을 입력해 주세요.(D[마이그레이션(농협)], I[설치], G[데이터빌드], M[MTE스키마생성]):", Arrays.asList("D","I","G","M"));
            }


            logger.info(Util.join(" ","runOption:", runOption));
            logger.info(getLogBar(80,"-"));

            String ccsid = "UTF-8";
            Class clazz = InstallInfo.class;
            File dest = new File(Util.join(home,File.separator),"install-info.json");
            InstallInfo installInfo = (InstallInfo) readObjectFromJson(dest, clazz, ccsid, "[$]\\{HOME\\}", home);

            replacePropertyValues(installInfo);

            List<Job> jobs = installInfo.getJobs();
            List<JobExecutor> jobExecutors = null;

            if(jobs != null){
                try{
                    //실행 옵션 컨트롤
                    {
                        if(RUN_OPT_GET_DATA.equals(runOption)){
                            for(Job job : jobs){
                                if("GetInstallData".equals(job.getName())){
                                    job.setUse(true);
                                } else {
                                    job.setUse(false);
                                }
                            }
                            if(!question("데이터빌드를 진행하시겠습니까?(y/n):", "y", "n")){
                                System.out.println("설치관리자를 종료합니다.");
                                System.exit(0);
                            }
                        }else if(RUN_OPT_INSTALL.equals(runOption)){
                            for(Job job : jobs){
                                if("GetInstallData".equals(job.getName()) || "CreateMteSchema".equals(job.getName())){
                                    job.setUse(false);
                                }
                            }
                            if(!question("IIP Install을 진행하시겠습니까?(y/n):", "y", "n")){
                                System.out.println("설치관리자를 종료합니다.");
                                System.exit(0);
                            }
                        }else if(RUN_OPT_INSTALL_IFM_SCHEMA.equals(runOption)){
                            for(Job job : jobs){
                                if("CreateMteSchema".equals(job.getName())){
                                    job.setUse(true);
                                } else {
                                    job.setUse(false);
                                }
                            }
                            if(!question("트레킹 테이블 생성을 진행하시겠습니까?(y/n):", "y", "n")){
                                System.out.println("설치관리자를 종료합니다.");
                                System.exit(0);
                            }
                        }else if(RUN_OPT_MIG.equals(runOption)){
                            for(Job job : jobs){
                                if("MigrationNHData".equals(job.getName())){
                                    job.setUse(true);
                                } else {
                                    job.setUse(false);
                                }
                            }
                            if(!question("데이터 마이그레이션(농협)을 진행하시겠습니까?(y/n):", "y", "n")){
                                System.out.println("마이그레이션 작업을 종료합니다.");
                                System.exit(0);
                            }
                        }else if(RUN_OPT_ALL.equals(runOption)){

                        }else{

                        }
                    }

                    logger.info(Util.join(" ","JOB 설정을 시작한다."));
                    logger.info(getLogBar(80,"-"));
                    jobExecutors = new ArrayList<JobExecutor>();
                    for(Job job : jobs){
                        if(job.isUse()){
                            DefaultJobExecutor dje = new DefaultJobExecutor(job);
                            jobExecutors.add(dje);
                        }else{

                        }
                    }
                    logger.info(getLogBar(80,"-"));
                    logger.info(Util.join(" ","JOB 설정을 성공적으로 완료함."));
                    logger.info(Util.join(" ","JOB 설정 개수:", jobExecutors.size()));
                    logger.info(Util.join(" ","실행할 JOB 리스트:"));

                    for(JobExecutor je : jobExecutors){
                        logger.info(Util.join(" ","JobExecutor:", je.getJob().getName()));
                    }


                }finally{
                    logger.info(getLogBar(80,"-"));
                    logger.info(Util.join(" ","JOB 설정을 완료한다."));
                    logger.info(" ");
                    sleep(1000);
                }

                if(!Util.isEmpty(jobExecutors)){

                    try{
                        logger.info(Util.join(" ","설치 또는 마이그레이션을 시작합니다(소요시간은 환경에 따라 달라질 수 있습니다.)"));
                        logger.info(getLogBar(80,"-"));

                        Map params = new HashMap();
                        params.put("args", args);
                        params.put("properties", properties);

                        for(JobExecutor executor : jobExecutors){
                            sleep(1000);
                            Job job = executor.getJob();
                            if(job.isDoNextOnError()){
                                try{
                                    executor.run(params);
                                }catch(Exception e){
                                    logger.info(getLogBar(80,"-"));
                                    logger.error(Util.join(" ","JOB[", job.getName(),"] 실행시 예외가 발생되었으나 옵션에 따라 다음 JOB을 계속 수행합니다.: "), e);
                                    continue;
                                }finally{
                                    logger.info("");
                                }
                            }else{
                                try{
                                    executor.run(params);
                                }finally{
                                    logger.info("");
                                }
                            }
                        }


                        logger.info(getLogBar(80,"-"));
                        logger.info(Util.join(" ","축하합니다. 설치를 성공적으로 마쳤습니다."));

                    }finally{
                        logger.info(getLogBar(80,"-"));
                        logger.info(Util.join(" ","date : ", Util.getFormatedDate()));
                        logger.info(Util.join(" ","설치를 종료합니다."));
                    }
                }
            }
        } catch (Exception ex) {
            logger.error(getLogBar(80,"-"));
            logger.error(Util.join(" ","설치시 다음과 같은 예외가 발생하여 설치 실패하였습니다.: "), ex);
        }
    }


    private void replacePropertyValues(InstallInfo installInfo) {
        List<Job> jobs = installInfo.getJobs();
        if(jobs != null){
            for(Job job : jobs){
                List<Task> tasks = job.getTasks();
                if(!Util.isEmpty(tasks)){
                    for(Task task : tasks){
                        Map params = task.getParams();
                        if(params != null){
                            Iterator ito = params.keySet().iterator();
                            while(ito.hasNext()){
                                String key = (String)ito.next();

                                Object obj = params.get(key);

                                if(obj instanceof String ){
                                    String value = (String)obj;
                                    if(!Util.isEmpty(value) && value.startsWith("${") && value.endsWith("}")){
                                        String pk = value.substring(2, value.length() - 1);
                                        //String rv = (String)installInfo.getProperties().get(pk);
                                        String rv = (String)properties.get(pk);
                                        if(!Util.isEmpty(rv)){
                                            params.put(key, rv);
                                        }
                                    }
                                }else if(obj instanceof ArrayList ){
                                    //배열일경우 나중에 개발하도록 하자. 일단 문자까지만 리플레이스 허용.
                                    continue;
                                }else{
                                    //문자나, 1차 문자 배열까지만 허용한다.
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Json 파일로 부터 자바 객체를 마샬링하여 리턴한다.
     * 객체 마샬링전 파일로 부터 읽어들인 내용중 입력(find)으로 받아 들인 내용을 또 다른 입력(replace) 으로 대체한다.
     * @param dest
     * @param clazz
     * @param ccsid
     * @param find
     * @param replace
     * @return
     * @throws Exception
     */
    public static Object readObjectFromJson(File dest, Class clazz, String ccsid, String find , String replace) throws Exception{

		FileInputStream fis = null;
		try{
			fis = new FileInputStream(dest);
			byte b[] = new byte[(int)dest.length()];
			fis.read(b);
            String config = new String(b);

			return jsonToObject(config.replaceAll(find, replace).getBytes(), clazz);
		}finally{
			try{if(fis != null) fis.close();}catch(IOException e){}
		}
	}

    /**
     * 입력 길이(len) 만큼 문자열(bar)를 반복적으로 Concat하여 리턴한다.
     * @param len
     * @param bar
     * @return
     */
    public static String getLogBar(int len, String bar){
        StringBuffer sb = new StringBuffer();
        for(int i = 0 ; i < len ; i ++) sb.append(bar);
        return sb.toString();
    }

    public static boolean question(String question, String yes, String no) {
        try{
            String answer = "";
            while(true){
                System.out.println(question);
                answer = scanner.nextLine();

                if(yes.equalsIgnoreCase(answer.trim()) || no.equalsIgnoreCase(answer.trim())){
                    break;
                }else{
                    System.out.println(Util.join("답변은 ", yes , " 또는 ", no, " 으로 해주세요."));
                }
            }
            return answer.equalsIgnoreCase(yes) ? true : false ;
        }finally{
        }

    }

    public static String question(String question, List<String> domains) {
        try{
            String answer = "";
            while(true){
                System.out.println(question);
                answer = scanner.nextLine();

                if(domains.contains(answer)){
                    break;
                }else{
                    System.out.println(Util.join("답변은 아래 값중 하나로 입력해 주세요."));
                    for(String value : domains){
                        System.out.println(value);
                    }
                }
            }
            return answer ;
        }finally{
        }

    }




    private static boolean checkHome(String input) {
        File zoon = new File(input);
        if(zoon.exists()){
            File dist = new File(zoon, "dist");
            if(!dist.exists()) {
                System.out.println(Util.join("설치 리소스 폴더[",dist.getPath(),"]가 존재하지 않습니다."));
                return false;
            }
            return true;
        }else{
            System.out.println(Util.join("설치 리소스 폴더[",input,"]가 존재하지 않습니다."));
            return false;
        }
    }

    private static void checkDatabase() {
        Connection con = null;
        String jdbcUrl = null;
        try{


            System.out.println(getLogBar(80,"="));
            System.out.println("install.jdbc 프로퍼티 체크합니다.");
            System.out.println(getLogBar(80,"-"));

            String jdbcDriver = (String)properties.get("install.jdbc.driver.class.name");
            jdbcUrl = (String)properties.get("install.jdbc.url");
            String jdbcUsername = (String)properties.get("install.jdbc.username");
            String jdbcPassword = (String)properties.get("install.jdbc.password");


            if(Util.isEmpty(jdbcDriver)){
                System.out.println("프로퍼티 값[install.jdbc.driver.class.name]이 존재하지 않습니다.");
            }


            if(Util.isEmpty(jdbcUrl)){
                System.out.println("프로퍼티 값[install.jdbc.url]이 존재하지 않습니다.");
            }

            if(Util.isEmpty(jdbcUsername)){
                System.out.println("프로퍼티 값[install.jdbc.username]이 존재하지 않습니다.");
            }


            if(Util.isEmpty(jdbcPassword)){
                System.out.println("프로퍼티 값[install.jdbc.password]이 존재하지 않습니다.");
            }

            System.out.println(Util.join(" ","jdbcDriver:[", jdbcDriver, "]"));
            System.out.println(Util.join(" ","jdbcUrl:[", jdbcUrl, "]"));
            System.out.println(Util.join(" ","jdbcUsername:[", jdbcUsername, "]"));
            System.out.println(Util.join(" ","jdbcPassword:[", jdbcPassword, "]"));

            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
            System.out.println(Util.join(" ","jdbc connection[",jdbcUrl,"] 체크 성공하였습니다."));

        }catch(Exception e){
            System.out.println(Util.join(" ","아래와 같은 사유로 jdbc connection[",jdbcUrl,"] 체크 실패하여 설치 작업을 종료합니다."));
            System.out.println("install.jdbc 프로퍼티를 수정 후 재실행해 주십시요.");
            e.printStackTrace();
            System.out.println(getLogBar(80,"-"));
            System.out.println("설치관리자를 종료합니다.");
            System.exit(0);
        }finally{

            if(con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
