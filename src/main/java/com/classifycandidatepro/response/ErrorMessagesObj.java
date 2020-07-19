package com.classifycandidatepro.response;

import java.io.Serializable;

public class ErrorMessagesObj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String errMessage;

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	
}
