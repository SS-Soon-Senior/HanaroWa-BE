package com.ss.hanarowa.domain.lesson.dto.response;

import java.time.LocalDateTime;

import com.ss.hanarowa.domain.lesson.entity.LessonState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonListResponseDTO {
	private Long lessonId;
	private Long lessonGisuId;
	private LessonState lessonState;
	private String startedAt;
	private String lessonName;
	private String instructorName;
	private String duration;
	private String lessonRoomName;
	private String reservedAt;
	private boolean isReviewed;
	private boolean isInProgress;
}
