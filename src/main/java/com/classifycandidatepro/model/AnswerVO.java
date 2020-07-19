package com.classifycandidatepro.model;

public class AnswerVO {

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPatName() {
		return patName;
	}

	public void setPatName(String patName) {
		this.patName = patName;
	}

	public int getTotalRating() {
		return totalRating;
	}

	public void setTotalRating(int totalRating) {
		this.totalRating = totalRating;
	}

	

	public int getPageNumberGlobal() {
		return pageNumberGlobal;
	}

	public void setPageNumberGlobal(int pageNumberGlobal) {
		this.pageNumberGlobal = pageNumberGlobal;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	
	

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}




	private String patName;

	private int totalRating;

	private Question question;

	private int pageNumberGlobal;
	
	private String actionCode;
	
	private String currentTime;

}
