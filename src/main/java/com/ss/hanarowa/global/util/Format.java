package com.ss.hanarowa.global.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Format {

	//생일 포맷팅
	public static LocalDate getBirthAsLocalDate(String birth) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		return LocalDate.parse(birth, formatter);
	}

	//2025-09-04 11:00:00 -> 9월 4일 (목) 오전 11시
	public static String getFormattedDate(LocalDateTime time) {
		try {
			DateTimeFormatter dateFormatter = DateTimeFormatter
				.ofPattern("M월 d일 (E)")
				.withLocale(Locale.KOREAN);
			String formattedDate = time.format(dateFormatter);

			DateTimeFormatter timeFormatter = DateTimeFormatter
				.ofPattern("a h:mm")
				.withLocale(Locale.KOREAN);
			String formattedTime = time.format(timeFormatter);

			return formattedDate + " " + formattedTime;

		} catch (Exception e) {
			return "날짜/시간 형식 오류";
		}
	}

	// 인자로 준 time 이 현재보다 과거면 isUsed 는 true
	public static boolean isUsed(LocalDateTime time) {
		LocalDateTime today = LocalDateTime.now();
		return today.isAfter(time);
	}

	//2025-09-04 11:00:00 -> 2025.09.04
	public static String reservedTime(LocalDateTime time) {
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy.MM.dd");

		return time.format(formatter2);
	}


}
