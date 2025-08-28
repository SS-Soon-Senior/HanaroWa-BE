package com.ss.hanarowa.domain.lesson.entity;

import java.util.ArrayList;
import java.util.List;

import com.ss.hanarowa.global.entity.BaseEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LessonGisu extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private int capacity;

	@Column(nullable = false)
	private int lessonFee;

	@Column(nullable = false, length = 50)
	private String duration;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'PENDING'")
	private LessonState lessonState;

	@OneToMany(mappedBy = "lessonGisu", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Curriculum> curriculums = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lessonId", nullable = false,
		foreignKey = @ForeignKey(name="fk_LessonGisu_Lesson"))
	private Lesson lesson;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lessonRoomId", nullable = false,
		foreignKey = @ForeignKey(name="fk_LessonGisu_LessonRoom"))
	private LessonRoom lessonRoom;

	public void updateState(LessonState lessonState) {
		this.lessonState = lessonState;
	}
}
