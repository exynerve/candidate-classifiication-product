package com.classifycandidatepro.response;

import java.io.Serializable;
import java.util.List;

import com.classifycandidatepro.model.UserMaintainence;


public class AJAXResponseLogin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean status;
	
	private List<ErrorMessagesObj> errMessages;
	
	private UserMaintainence model;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}


	

	public List<ErrorMessagesObj> getErrMessages() {
		return errMessages;
	}

	public void setErrMessages(List<ErrorMessagesObj> errMessages) {
		this.errMessages = errMessages;
	}

	
	public String getSucessMsg() {
		return sucessMsg;
	}

	public void setSucessMsg(String sucessMsg) {
		this.sucessMsg = sucessMsg;
	}


	public UserMaintainence getModel() {
		return model;
	}

	public void setModel(UserMaintainence model) {
		this.model = model;
	}


	private String sucessMsg;

}
