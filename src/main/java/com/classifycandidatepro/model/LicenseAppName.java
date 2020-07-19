package com.classifycandidatepro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "license")
public class LicenseAppName extends AuditModel {
	
	public LicenseAppName(){
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotBlank
	@Size(min = 15, max = 20)
	@Column(unique=true,nullable=false)
	private String appName;


	@NotBlank
	@Size(min=10,max=10)
	@Column(nullable=false)
	private String expiryDate;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

}
