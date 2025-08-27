package com.ss.hanarowa.global.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Format {

	//생일 포맷팅
	public static LocalDate getBirthAsLocalDate(String birth) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		return LocalDate.parse(birth, formatter);
	}
}
