package com.ss.hanarowa.domain.lesson.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ss.hanarowa.domain.branch.entity.Branch;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LessonRoom {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(nullable = false, length = 20)
	private String name;

	@OneToMany(mappedBy = "lessonRoom", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<LessonGisu> lessonGisus = new ArrayList<>();

	@OneToMany(mappedBy = "lessonRoom", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<RoomTime> roomTimes = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branchId", nullable = false,
		foreignKey = @ForeignKey(name = "fk_LessonRoom_Branch"))
	private Branch branch;
}
