package pep.per.mint.migration;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pep.per.mint.common.util.Util;


public class MigrationGenerator {

	public static final Logger logger = LoggerFactory.getLogger(MigrationGenerator.class);

	ApplicationContext ac;

	MigrationManager imgr;


	String from = null;

	String to = null;

	public MigrationGenerator() {

	}

	public static void main(String[] args){
		try {
			MigrationGenerator mg = new MigrationGenerator();
			mg.checkParams(args);
			mg.start();
		} catch (Exception e) {
			logger.error("",e);
		}
	}

	private void checkParams(String[] args) throws Exception{
		logger.info("-----------------------------------------");
		if(args.length == 0) {
			help();
			String msg = "MigrationGenerator has no args.";
			throw new Exception(msg);
		}

		String from = null;
		String to = null;

		for(int i = 0 ; i < args.length ; i ++){
			if("-from".equalsIgnoreCase(args[i])){
				try{
					from = args[i + 1];
				}catch(ArrayIndexOutOfBoundsException e){
					help();
					String msg = "MigrationGenerator has no from value.";
					throw new Exception(msg);
				}
			}
			if("-to".equalsIgnoreCase(args[i])){
				try{
					to = args[i + 1];
				}catch(ArrayIndexOutOfBoundsException e){
					help();
					String msg = "MigrationGenerator has no to value.";
					throw new Exception(msg);
				}
			}
		}

		if(Util.isEmpty(from)){
			help();
			String msg = "MigrationGenerator has no from value.";
			throw new Exception(msg);
		}
		if(Util.isEmpty(to)){
			help();
			String msg = "MigrationGenerator has no to value.";
			throw new Exception(msg);
		}


		logger.info(Util.join("input from:", from));
		logger.info(Util.join("input to:", to));
		this.from = from;
		this.to = to;
	}

	private void help() {
		// TODO Auto-generated method stub
		logger.info("*usage:");
		logger.info("\tmig -from [시작일:yyyymmdd]  -to [종료일:yyyymmdd]");
	}

	public void start(){



		try {
			ac = new ClassPathXmlApplicationContext("classpath:/config/migration-context.xml");
			imgr = ac.getBean("migrationManager", pep.per.mint.migration.MigrationManager.class);
			logger.info("//--------------------------------------------");
			logger.info("// start data migration.");
			logger.info("//--------------------------------------------");
			imgr.migrate(from, to);

		} catch (Exception e) {
		     logger.error("error data migration",e);
		} finally{
			logger.info("//--------------------------------------------");
			logger.info("// end data migration.");
			logger.info("//--------------------------------------------");
		}
	}


	public void stop(){
	}


}
