package com.classifycandidatepro.model;

import java.io.Serializable;

public class ClassifyUserCompute implements Serializable{

	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	
	private String userId;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFeatureType() {
		return featureType;
	}

	public void setFeatureType(String featureType) {
		this.featureType = featureType;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	private String featureType;
	
	private double score;
	
	private String sessionId;

}
