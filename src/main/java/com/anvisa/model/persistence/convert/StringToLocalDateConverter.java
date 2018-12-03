package com.anvisa.model.persistence.convert;

import java.time.LocalDate;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class StringToLocalDateConverter implements Converter<String, LocalDate> {
	@Override
	public LocalDate convert(String source) {
		
		String[] dateStr = source.split("/");
		LocalDate date = null;
		if (dateStr.length==3) {
			date = LocalDate.parse(dateStr[2]+"-"+dateStr[1]+"-"+dateStr[0]);
			return date;
		}
		return null;
	}
}