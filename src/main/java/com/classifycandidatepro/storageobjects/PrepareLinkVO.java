package com.classifycandidatepro.storageobjects;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "preparelink")
public class PrepareLinkVO {

	@Column
	@Id
	private String prepareName;
	
	public String getPrepareName() {
		return prepareName;
	}

	public void setPrepareName(String prepareName) {
		this.prepareName = prepareName;
	}

	public String getPrepareUrl() {
		return prepareUrl;
	}

	public void setPrepareUrl(String prepareUrl) {
		this.prepareUrl = prepareUrl;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	@Column
	private String prepareUrl;
	
	@Column
	private String questionType;
	
	

}
