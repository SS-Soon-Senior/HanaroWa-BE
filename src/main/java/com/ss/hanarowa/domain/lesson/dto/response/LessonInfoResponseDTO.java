package com.ss.hanarowa.domain.lesson.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LessonInfoResponseDTO {

	private long lessonId; //Lesson의 아이디
	private long lessonGisuId;  //LessonGisu의 아이디
	private String lessonName; // 강좌의 이름
	private String instructor; //강사
	private String lessonImg; // 이미지
	private String duration; //기간
	private int lessonFee; // 수강료
	private int capacity; //LessonGisu 의 capacity
	private int currentStudentCount; // 현재 수강중인 인원
}
