package com.classifycandidatepro.storageobjects;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "companyinformation")
public class CompanyInformationVO {

	
	@Column
	private String companyURL;
	
	public String getCompanyURL() {
		return companyURL;
	}

	public void setCompanyURL(String companyURL) {
		this.companyURL = companyURL;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyImageURL() {
		return companyImageURL;
	}

	public void setCompanyImageURL(String companyImageURL) {
		this.companyImageURL = companyImageURL;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	@Column
	@Id
	private String companyName;
	
	@Column
	private String companyImageURL;
	
	@Column
	private String cluster;

}
