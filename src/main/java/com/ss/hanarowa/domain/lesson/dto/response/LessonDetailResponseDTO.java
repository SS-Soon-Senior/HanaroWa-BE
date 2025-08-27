package com.ss.hanarowa.domain.lesson.dto.response;

import com.ss.hanarowa.domain.branch.entity.Branch;
import com.ss.hanarowa.domain.lesson.entity.Category;
import com.ss.hanarowa.domain.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonDetailResponseDTO {
	private String lessonName;
	private String instructor;
	private String instruction;
	private String description;
	private Category category;
	private String lessonImg;
	private Branch branch;
	private Member member;
}
