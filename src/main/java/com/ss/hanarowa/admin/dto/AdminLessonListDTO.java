package com.ss.hanarowa.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminLessonListDTO {
	String lessonName;
	String instructor;
	String instruction;
	String lessonImg;
}
