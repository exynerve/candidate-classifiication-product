package com.classifycandidatepro.response;

import java.io.Serializable;
import java.util.List;


public class AJAXResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean status;
	
	private List<ErrorMessagesObj> errMessages;
	
	private Object model;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
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


	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}


	private String sucessMsg;
	
	private int total;

}
