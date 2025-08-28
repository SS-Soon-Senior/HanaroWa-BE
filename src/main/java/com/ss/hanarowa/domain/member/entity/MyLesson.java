package com.ss.hanarowa.domain.member.entity;

import com.ss.hanarowa.domain.lesson.entity.LessonGisu;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyLesson { //수강신청 내역(내가 수업을 듣는거)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "memberId",
		foreignKey = @ForeignKey(name = "fk_MyLesson_Member"), nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lessonGisuId",
		foreignKey = @ForeignKey(name = "fk_MyLesson_LessonGisu"), nullable = false)
	private LessonGisu lessonGisu;
}