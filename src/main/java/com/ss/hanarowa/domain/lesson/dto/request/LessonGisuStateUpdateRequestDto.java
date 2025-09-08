package com.ss.hanarowa.domain.lesson.dto.request;

import com.ss.hanarowa.domain.lesson.entity.LessonState;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonGisuStateUpdateRequestDto {
	@NotBlank(message = "수업 기수 상태는 필수 입력 값입니다.")
	private LessonState lessonState;
}