package com.classifycandidatepro.model;

import java.io.Serializable;

public class SuggestionObj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int suggestionId;

	public int getSuggestionId() {
		return suggestionId;
	}

	public void setSuggestionId(int suggestionId) {
		this.suggestionId = suggestionId;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String suggestion;

	private String type;

}
