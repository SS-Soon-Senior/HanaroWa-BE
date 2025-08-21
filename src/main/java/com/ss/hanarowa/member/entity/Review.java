package com.ss.hanarowa.member.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.ss.hanarowa.lesson.entity.Lesson;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private int rating;

	private String reviewTxt;

	@ManyToOne
	@JoinColumn(name = "memberId",
		foreignKey = @ForeignKey(name = "fk_Review_Member"))
	private Member member;

	@ManyToOne
	@JoinColumn(name = "lessonId",
		foreignKey = @ForeignKey(name = "fk_Review_Lesson"))
	private Lesson lesson;
}
