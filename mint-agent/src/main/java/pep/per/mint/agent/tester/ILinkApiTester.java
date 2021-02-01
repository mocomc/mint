/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.agent.tester;

import java.util.LinkedList;

import com.mococo.ILinkAPI.manager.ILinkException;
import com.mococo.ILinkAPI.manager.QManagerAPI;
import com.mococo.ILinkAPI.manager.ReceiverChannelProperty;
import com.mococo.ILinkAPI.manager.TransmitterChannelProperty;

import pep.per.mint.common.util.Util;

/**
 * <pre>
 * pep.per.mint.agent.tester
 * ILinkApiTester.java
 * </pre>
 * @author whoana
 * @date Jul 1, 2019
 */
public class ILinkApiTester {


	public static void main(String args[]) {
		for(int i = 0 ; i < 10 ; i++) {
			test();
			System.out.println(i + "times");
		}

	}
	public static void test() {
		QManagerAPI api = null;
		try {

			String ip = "10.10.1.15";
			int port = 9999;
			String qmgrName = "";
			api = new QManagerAPI(ip, port, qmgrName);


			//Map<String,Integer> csm = new HashMap<String, Integer>();

			System.out.println("-------------------------------");
			System.out.println("--transmit channel list");
			System.out.println("-------------------------------");
			LinkedList tcl= api.getTransmitterChannelList();
			for( int k = 0 ;k < tcl.size() ;  k ++){
				TransmitterChannelProperty ch = (TransmitterChannelProperty)tcl.get(k);
				//csm.put(ch.getName(), ch.getStatus());
				System.out.println(Util.join(ch.getName(),":", ch.getStatus()));

			}

			System.out.println("-------------------------------");
			System.out.println("--receiver channel list");
			System.out.println("-------------------------------");
			LinkedList rcl= api.getReceiverChannelList();
			for( int k = 0 ;k < rcl.size() ; k ++){
				ReceiverChannelProperty ch = (ReceiverChannelProperty)rcl.get(k);
				//csm.put(ch.getName(), ch.getStatus());
				System.out.println(Util.join(ch.getName(),":", ch.getStatus()));
			}




		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(api != null)
				try {
					api.disConnect();
				} catch (ILinkException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
