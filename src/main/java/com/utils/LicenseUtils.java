package com.utils;

import java.util.Date;

public class LicenseUtils {

	public boolean checkLicenseExpiration(String date) {

		if (null == date || date != null && date.isEmpty()) {
			return true;
		}

		// Conversion
		try {

			DateUtils dateUtils = new DateUtils();

			Date expiryDate = dateUtils.convertGivenDate(date);

			if (null == expiryDate || expiryDate != null && expiryDate.toString().isEmpty()) {
				return true;
			}

			Date todaysDate = new Date();

			DateCompareEnum dateCompareEnum = dateUtils.compareDates(expiryDate, todaysDate);

			if (dateCompareEnum == DateCompareEnum.AFTER || dateCompareEnum == DateCompareEnum.EQUAL) {

				return false;
			} else {
				return true;
			}

		} catch (Exception e) {
			return true;
		}

	}

}
