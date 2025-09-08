package com.ss.hanarowa.domain.lesson.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LessonListByBranchIdResponseDTO {
	private long branchId;
	private String locationName;
	private String branchName;
	private List<LessonInfoResponseDTO> Lessons;
}
