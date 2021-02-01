/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.service.deploy.data.description;

/**
 * @author whoana
 * @since Jul 30, 2020
 */
public class SystemInfo {

	String cd;
	String name;
	String type;
	int seq;
	
	public SystemInfo() {

	}
	
	
	/**
	 * @param cd
	 * @param name
	 * @param type
	 * @param seq
	 */
	public SystemInfo(String cd, String name, String type, int seq) {
		this();
		this.cd = cd;
		this.name = name;
		this.type = type;
		this.seq = seq;
	}

	/**
	 * @return the cd
	 */
	public String getCd() {
		return cd;
	}

	/**
	 * @param cd the cd to set
	 */
	public void setCd(String cd) {
		this.cd = cd;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	

}
