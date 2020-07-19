package com.classifycandidatepro.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;



@Entity
@Table(name = "usermaintain")
public class UserMaintainence extends AuditModel {

	public UserMaintainence() {

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	@NotNull
	private int loginType;

	@Column
	@NotNull
	@Size(min = 8, max = 40)
	private String password;

	public int getLoginType() {
		return loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column
	@NotNull
	@Size(min = 10, max = 60)
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column
	@Size(max = 15)
	@NotNull
	private String phoneNo;

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@EmbeddedId
	private UserMaintainId userMaintainId;

	public UserMaintainId getUserMaintainId() {
		return userMaintainId;
	}

	public void setUserMaintainId(UserMaintainId userMaintainId) {
		this.userMaintainId = userMaintainId;
	}

	@Column
	private String dob;

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	@Column
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column
	private String country;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column
	private String city;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column
	private String streetAddressLine1;

	public String getStreetAddressLine1() {
		return streetAddressLine1;
	}

	public void setStreetAddressLine1(String streetAddressLine1) {
		this.streetAddressLine1 = streetAddressLine1;
	}

	@Column
	private String streetAddressLine2;

	public String getStreetAddressLine2() {
		return streetAddressLine2;
	}

	public void setStreetAddressLine2(String streetAddressLine2) {
		this.streetAddressLine2 = streetAddressLine2;
	}

	@Column
	private String pinCode;

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	@Column
	private String usnNumber;

	public String getUsnNumber() {
		return usnNumber;
	}

	public void setUsnNumber(String usnNumber) {
		this.usnNumber = usnNumber;
	}

	@Column
	private String collegeName;

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getSecretQuestion1() {
		return secretQuestion1;
	}

	public void setSecretQuestion1(String secretQuestion1) {
		this.secretQuestion1 = secretQuestion1;
	}

	public String getSecretQuestion2() {
		return secretQuestion2;
	}

	public void setSecretQuestion2(String secretQuestion2) {
		this.secretQuestion2 = secretQuestion2;
	}

	public String getSecretAnswer1() {
		return secretAnswer1;
	}

	public void setSecretAnswer1(String secretAnswer1) {
		this.secretAnswer1 = secretAnswer1;
	}

	public String getSecretAnswer2() {
		return secretAnswer2;
	}

	public void setSecretAnswer2(String secretAnswer2) {
		this.secretAnswer2 = secretAnswer2;
	}

	public String getIsAccountLocked() {
		return isAccountLocked;
	}

	public void setIsAccountLocked(String isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}

	public int getNoOfWrongPasswords() {
		return noOfWrongPasswords;
	}

	public void setNoOfWrongPasswords(int noOfWrongPasswords) {
		this.noOfWrongPasswords = noOfWrongPasswords;
	}

	@Column
	private String university;

	@Column
	private String secretQuestion1;

	@Column
	private String secretQuestion2;

	@Column
	private String secretAnswer1;

	@Column
	private String secretAnswer2;

	@Column
	private String isAccountLocked;

	@Column
	private int noOfWrongPasswords;

	@Column
	private String gender;

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column
	private int age;

	private transient AdditionalConfig additionalConfig;

	public AdditionalConfig getAdditionalConfig() {
		return additionalConfig;
	}

	public void setAdditionalConfig(AdditionalConfig additionalConfig) {
		this.additionalConfig = additionalConfig;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Column
	private String branch;

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	@Column
	private String projectTitle;

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	@Column
	private int yearOfProject;

	public int getYearOfProject() {
		return yearOfProject;
	}

	public void setYearOfProject(int yearOfProject) {
		this.yearOfProject = yearOfProject;
	}

	@SuppressWarnings("unused")
	private transient ArrayList<String> skillSetNames;

	public ArrayList<String> getSkillSetNames() {
		return skillSetNames;
	}

	public void setSkillSetNames(ArrayList<String> skillSetNames) {
		this.skillSetNames = skillSetNames;
	}

	private transient ArrayList<ServicesTempModel> services;

	public ArrayList<ServicesTempModel> getServices() {
		return services;
	}

	public void setServices(ArrayList<ServicesTempModel> services) {
		this.services = services;
	}

	@Column
	private double totalCostAllServices=0;

	public double getTotalCostAllServices() {
		return totalCostAllServices;
	}

	public void setTotalCostAllServices(double totalCostAllServices) {
		this.totalCostAllServices = totalCostAllServices;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column
	private String description;

	@Column
	private String companyName;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column
	private double totalRating;

	public double getTotalRating() {
		return totalRating;
	}

	public void setTotalRating(double totalRating) {
		this.totalRating = totalRating;
	}

	@Column
	private String serviceType;

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	@Column
	@Size(min = 0, max = 30)
	private String degree;

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	@Column
	@Size(min = 0, max = 300)
	private String specification;

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	@Column
	@Size(min = 0, max = 300)
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column
	@Size(min = 0, max = 30)
	private String fatherName;

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	@Column
	@Size(min = 0, max = 15)
	private String fatherNumber;

	public String getFatherNumber() {
		return fatherNumber;
	}

	public void setFatherNumber(String fatherNumber) {
		this.fatherNumber = fatherNumber;
	}

	@Column
	@Size(min = 0, max = 100)
	private String fatherEmail;

	public String getFatherEmail() {
		return fatherEmail;
	}

	public void setFatherEmail(String fatherEmail) {
		this.fatherEmail = fatherEmail;
	}

	@Column
	@Size(min = 0, max = 30)
	private String motherName;

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	@Column
	@Size(min = 0, max = 30)
	private String motherNumber;

	public String getMotherNumber() {
		return motherNumber;
	}

	public void setMotherNumber(String motherNumber) {
		this.motherNumber = motherNumber;
	}

	@Column
	@Size(min = 0, max = 100)
	private String motherEmail;

	public String getMotherEmail() {
		return motherEmail;
	}

	public void setMotherEmail(String motherEmail) {
		this.motherEmail = motherEmail;
	}

	@Column
	@Size(min = 0, max = 30)
	private String localGuardName;

	public String getLocalGuardName() {
		return localGuardName;
	}

	public void setLocalGuardName(String localGuardName) {
		this.localGuardName = localGuardName;
	}

	@Column
	@Size(min = 0, max = 15)
	private String localGuardNumber;

	public String getLocalGuardNumber() {
		return localGuardNumber;
	}

	public void setLocalGuardNumber(String localGuardNumber) {
		this.localGuardNumber = localGuardNumber;
	}

	@Column
	@Size(min = 0, max = 100)
	private String localGuardEmail;

	public String getLocalGuardEmail() {
		return localGuardEmail;
	}

	public void setLocalGuardEmail(String localGuardEmail) {
		this.localGuardEmail = localGuardEmail;
	}

	@Column
	@Size(min = 0, max = 100)
	private String challanNumber;

	public String getChallanNumber() {
		return challanNumber;
	}

	public void setChallanNumber(String challanNumber) {
		this.challanNumber = challanNumber;
	}

	@Column
	private double feePaid=0;

	public double getFeePaid() {
		return feePaid;
	}

	public void setFeePaid(double feePaid) {
		this.feePaid = feePaid;
	}

	@Column
	@Size(min = 0, max = 300)
	private String residentialAddress;

	public String getResidentialAddress() {
		return residentialAddress;
	}

	public void setResidentialAddress(String residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	@Column
	@Size(min = 0, max = 10)
	private String residenceStatus;

	public String getResidenceStatus() {
		return residenceStatus;
	}

	public void setResidenceStatus(String residenceStatus) {
		this.residenceStatus = residenceStatus;
	}

	public String getAdmissionType() {
		return admissionType;
	}

	public void setAdmissionType(String admissionType) {
		this.admissionType = admissionType;
	}

	@Column
	private String admissionType;

	@Column
	private double semesterMarks=0;

	public double getSemesterMarks() {
		return semesterMarks;
	}

	public void setSemesterMarks(double semesterMarks) {
		this.semesterMarks = semesterMarks;
	}

	public List<String> getActivities() {
		return activities;
	}

	public void setActivities(List<String> activities) {
		this.activities = activities;
	}

	private transient List<String> activities;
	
	private transient List<String> subjects;

	@Column
	private String department;

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public List<String> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}
}
