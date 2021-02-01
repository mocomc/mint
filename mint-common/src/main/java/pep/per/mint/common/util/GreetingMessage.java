/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
 

/**
 * @author whoana
 * @since 2020. 12. 4.
 */
public class GreetingMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	byte[] msg;
	
	public GreetingMessage() {
		super();
	}
	
	
	public static void main(String args[]) {
		 
		try {
			String sourceFile = "/Users/whoana/DEV/workspace/mint/greeting.txt";
			String targetFile = "/Users/whoana/DEV/workspace/mint/greeting.dat";
			GreetingMessage gm = new GreetingMessage();
			gm.create(sourceFile, targetFile);
			
			String greeting = gm.read(targetFile);
			System.out.println("greeting msg:\n" + greeting);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
	/**
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public String read(String msgFile) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = null;
		String msg = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(new File(msgFile)));
			Object obj = ois.readObject();
			
			if(obj instanceof GreetingMessage) {
				GreetingMessage gm = (GreetingMessage)obj;
				msg = new String(gm.msg);
			}
			return msg;
		}finally {
			if(ois!=null) {
				try {
					ois.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}


	/**
	 * @param string
	 * @param string2
	 * @throws IOException 
	 */
	public void create(String sourceFile, String targetFile) throws IOException {
		BufferedInputStream source = null; 
		ObjectOutputStream target = null;
		try {
			source = new BufferedInputStream(new FileInputStream(new File(sourceFile)));
			byte []out = new byte[source.available()];
			source.read(out);
			this.msg = out;
			System.out.println("read data:\n" + new String(this.msg));
			
			target = new ObjectOutputStream(new FileOutputStream(new File(targetFile)));
			target.writeObject(this);
			target.flush();

		}finally {
			if(source!=null) {
				try {
					source.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(target != null) {
				try {
					target.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
 
}
