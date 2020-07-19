package com.classifycandidatepro.model;

import java.io.Serializable;

public class DataSet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int clusterNo;
	
	public int getClusterNo() {
		return clusterNo;
	}

	public void setClusterNo(int clusterNo) {
		this.clusterNo = clusterNo;
	}

	public double getAptitude() {
		return aptitude;
	}

	public void setAptitude(double aptitude) {
		this.aptitude = aptitude;
	}

	public double getGeneral() {
		return general;
	}

	public void setGeneral(double general) {
		this.general = general;
	}

	public double getTechnical() {
		return technical;
	}

	public void setTechnical(double technical) {
		this.technical = technical;
	}

	public double getTotalmarks() {
		return totalmarks;
	}

	public void setTotalmarks(double totalmarks) {
		this.totalmarks = totalmarks;
	}

	private double aptitude;
	
	private double general;
	
	private double technical;
	
	private double totalmarks;

}
