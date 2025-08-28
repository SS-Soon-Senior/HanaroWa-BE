package com.ss.hanarowa.domain.lesson.dto.response;

import java.util.List;

import com.ss.hanarowa.domain.facility.entity.FacilityImage;
import com.ss.hanarowa.domain.lesson.entity.Lesson;

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
public class LessonListByBranchId {
	private long branchId;
	private String locationName;
	private String branchName;
	private List<Lesson> Lessons;
}
