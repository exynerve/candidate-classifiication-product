package com.classifycandidatepro.model;

import java.io.Serializable;

public class RatingTypesNames implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ratingname;
	
	public String getRatingname() {
		return ratingname;
	}

	public void setRatingname(String ratingname) {
		this.ratingname = ratingname;
	}

	public String getRatingdesc() {
		return ratingdesc;
	}

	public void setRatingdesc(String ratingdesc) {
		this.ratingdesc = ratingdesc;
	}

	private String ratingdesc;



}
