package com.ss.hanarowa.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminLessonListResponseDTO {
	String lessonName;
	String instructor;
	String instruction;
	String lessonImg;
}
