package com.classifycandidatepro.model;

import java.io.Serializable;

public class ForgotPasswordFree implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String appName;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public String getMessageToBeSend() {
		return messageToBeSend;
	}

	public void setMessageToBeSend(String messageToBeSend) {
		this.messageToBeSend = messageToBeSend;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	private String messageToBeSend;
	
	private String mailId;

}
