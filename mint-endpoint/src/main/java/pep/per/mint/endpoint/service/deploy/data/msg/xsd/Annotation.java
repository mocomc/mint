/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.service.deploy.data.msg.xsd;

/**
 * @author whoana
 * @since Aug 5, 2020
 */
public class Annotation {
	
	AppInfo appInfo;
	
	Documentation documentation;

	/**
	 * @return the appInfo
	 */
	public AppInfo getAppInfo() {
		return appInfo;
	}

	/**
	 * @param appInfo the appInfo to set
	 */
	public void setAppInfo(AppInfo appInfo) {
		this.appInfo = appInfo;
	}

	/**
	 * @return the documentation
	 */
	public Documentation getDocumentation() {
		return documentation;
	}

	/**
	 * @param documentation the documentation to set
	 */
	public void setDocumentation(Documentation documentation) {
		this.documentation = documentation;
	}

	

}
