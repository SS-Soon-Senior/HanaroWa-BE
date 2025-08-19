package com.ss.hanarowa.lesson.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.ss.hanarowa.branch.entity.Branch;

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

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 30)
	private String lessonName;

	@Column(nullable = false, length = 15)
	private String instructor;

	@Column(nullable = false)
	private String instruction;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false, length = 15)
	private String lessonRoom;

	@Column(nullable = false)
	private int capacity;

	@Column(nullable = false)
	private int lessonPrice;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Category category;

	private String lessonImg;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch",
		foreignKey = @ForeignKey(name = "fk_Lesson_Branch"))
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Branch branch;

	@OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Curriculum> curriculums = new ArrayList<>();
}
