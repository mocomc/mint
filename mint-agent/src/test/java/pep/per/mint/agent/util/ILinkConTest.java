/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.agent.util;

/*

소스 설명: 
	ilink5,6 버전을 번갈아가며 접속하여 채널 정보를 가져오는 절차를 무한 반복하는 테스트 코드 
예외 :  
큐매니저연결 - 버전 가져오기 -  채널 가져오기 - 큐매니저 연결 닫기 를 반복하면 아래 예외 발생.  
73 line String version = api.getVersion(); 에서 NullPointerException 예외 발생

특이사항 : 
109 line api.disConnect(); 부분을 주석 처리하면 예외는 한동안(230회 테스트) 발생되지 않음. 문제가 없는지 서버측 로직 확인 필요. 

=============================================
try(92), version:5.2.20.20190225 
---------------------------------------------
SSL.CHL:4
ILink5.ILink6:4
TEST.CHL:1
=============================================
try(93), version:6.2.4.0904 JetStream
---------------------------------------------
T1.T2:0
T1.T2.R:0
W.L:1
=============================================
try(94), version:5.2.20.20190225 
---------------------------------------------
SSL.CHL:4
ILink5.ILink6:4
TEST.CHL:1
=============================================
try(95), version:6.2.4.0904 JetStream
---------------------------------------------
T1.T2:0
T1.T2.R:0
W.L:1
java.lang.NullPointerException
	at com.mococo.ILinkAPI.manager.QManagerAPI.getVersion(QManagerAPI.java:486)
	at pep.per.mint.agent.util.ILinkConTest.main(ILinkConTest.java:73)
java.lang.NullPointerException
	at com.mococo.ILinkAPI.manager.QManagerAPI.getVersion(QManagerAPI.java:486)
	at pep.per.mint.agent.util.ILinkConTest.main(ILinkConTest.java:73)
java.lang.NullPointerException
	at com.mococo.ILinkAPI.manager.QManagerAPI.getVersion(QManagerAPI.java:486)
	at pep.per.mint.agent.util.ILinkConTest.main(ILinkConTest.java:73)
java.lang.NullPointerException
	at com.mococo.ILinkAPI.manager.QManagerAPI.getVersion(QManagerAPI.java:486)
	at pep.per.mint.agent.util.ILinkConTest.main(ILinkConTest.java:73)
java.lang.NullPointerException
	at com.mococo.ILinkAPI.manager.QManagerAPI.getVersion(QManagerAPI.java:486)
	at pep.per.mint.agent.util.ILinkConTest.main(ILinkConTest.java:73)
java.lang.NullPointerException
	at com.mococo.ILinkAPI.manager.QManagerAPI.getVersion(QManagerAPI.java:486)
	at pep.per.mint.agent.util.ILinkConTest.main(ILinkConTest.java:73)	
*/

import java.util.LinkedList;
import com.mococo.ILinkAPI.manager.ChannelProperty;
import com.mococo.ILinkAPI.manager.QManagerAPI;
import com.mococo.ILinkAPI.manager.ReceiverChannelProperty;
import com.mococo.ILinkAPI.manager.TransmitterChannelProperty;

public class ILinkConTest {

	
	public static void main(String[] args)  {
//		version 5
		String ip = "10.10.1.66";
		int port = 11111;
		String qmgr = "TEST";
		//채널정보
		/*
		 SSL.CHL:4
		 ILink5.ILink6:4
		 TEST.CHL:1
		 */
//		
//		version 6
//		String ip = "10.10.1.66";
//		int port = 10010;
//		String qmgr = "T1";		
		//채널정보
		/*
		 * T1.T2:0
			T1.T2.R:0
			W.L:1
		 */
		
		int tryCnt = 0;
		while(true) {		
			QManagerAPI api = null;
			try {
				Thread.sleep(500);
				
				if(tryCnt % 2 == 0) {
					ip = "10.10.1.66";
					port = 11111;
					qmgr = "TEST";					
				}else {
					ip = "10.10.1.66";
					port = 10010;
					qmgr = "T1";		
				}
				
				api = new QManagerAPI(ip, port, qmgr);
				
				String version = api.getVersion();
				System.out.println("=============================================");
				System.out.println("try(" + tryCnt + "), version:" + version);
				System.out.println("---------------------------------------------");
				if(version.startsWith("5")) {//ILink Version 5 이하 
					
					LinkedList tcl= api.getTransmitterChannelList();
					
					for( int k = 0 ;k < tcl.size() ;  k ++){
						TransmitterChannelProperty ch = (TransmitterChannelProperty)tcl.get(k);
						System.out.println(ch.getName() + ":" + ch.getStatus());
					} 
					
					LinkedList rcl= api.getReceiverChannelList();
					for( int k = 0 ;k < rcl.size() ; k ++){
						ReceiverChannelProperty ch = (ReceiverChannelProperty)rcl.get(k);
						System.out.println(ch.getName() + ":" + ch.getStatus());
					}
					
					
				}else {
					LinkedList cl = api.getChannelList();
					for (Object object : cl) {
						ChannelProperty ch = (ChannelProperty)object;
						System.out.println(ch.getName() + ":" + ch.getStatus());
					}				
				}
				
				tryCnt ++;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if(api != null)
					try {
						api.disConnect();
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
	}
	 
}

