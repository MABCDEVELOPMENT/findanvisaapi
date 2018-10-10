package com.anvisa.core.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UtilDate {
	
	static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public static LocalDate getDateFormatDayMonthYear(LocalDate localDate) {
		if (localDate==null) {
			return null;
		}
		return LocalDate.parse(localDate.format(dateTimeFormatter));
	}
}
