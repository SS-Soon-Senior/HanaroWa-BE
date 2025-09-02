package com.ss.hanarowa.domain.lesson.dto.response;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferedLessonListResponseDTO {
	private List<MyOpenLessonListResponseDTO> offeredLessonList;
}
