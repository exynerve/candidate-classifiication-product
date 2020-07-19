package com.classifycandidatepro.model;

import java.io.Serializable;

public class RatingValues implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int rating;


	public int getRating() {
		return rating;
	}


	public void setRating(int rating) {
		this.rating = rating;
	}

}
