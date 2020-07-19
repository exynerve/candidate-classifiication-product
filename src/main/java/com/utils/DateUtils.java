package com.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public Date convertGivenDate(String date) {

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = formatter.parse(date);
			return date1;
		} catch (ParseException e) {

			return null;
		}

	}
	
	
	

	public DateCompareEnum compareDates(Date date1, Date date2) {

		if (date1.after(date2)) {
			return DateCompareEnum.AFTER;
		}

		if (date1.before(date2)) {
			return DateCompareEnum.BEFORE;
		}

		if (date1.equals(date2)) {
			return DateCompareEnum.EQUAL;
		}
		return DateCompareEnum.COMPAREERROR;

	}

}
