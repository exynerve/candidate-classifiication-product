package com.classifycandidatepro.model;

import java.io.Serializable;

public class ScoreGraph implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int iterationNo;
	
	public int getIterationNo() {
		return iterationNo;
	}

	public void setIterationNo(int iterationNo) {
		this.iterationNo = iterationNo;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getSessionObj() {
		return sessionObj;
	}

	public void setSessionObj(String sessionObj) {
		this.sessionObj = sessionObj;
	}

	private double score;
	
	private String sessionObj;
	
}
