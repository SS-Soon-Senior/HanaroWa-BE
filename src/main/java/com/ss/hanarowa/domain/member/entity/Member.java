package com.ss.hanarowa.domain.member.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ss.hanarowa.domain.branch.entity.Branch;
import com.ss.hanarowa.domain.facility.entity.FacilityTime;
import com.ss.hanarowa.domain.lesson.entity.Lesson;
import com.ss.hanarowa.domain.lesson.entity.Review;

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
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String email;

	@Column
	private LocalDate birth;

	private String password;

	// 소셜 로그인
	@Column(length = 20)
	private String provider;

	private String providerId;

	@Column
	private String phoneNumber;

	@Column(columnDefinition = "DATETIME(0)")
	private LocalDateTime deletedAt;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	private String refreshToken;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branchId",
		foreignKey = @ForeignKey(name = "fk_Member_Branch"))
	private Branch branch;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<FacilityTime> facilityTimes = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<MyLesson> myLessons = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<Review> reviews = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<Advice> advices = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<Lesson> lessons = new ArrayList<>();
	
	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public void clearRefreshToken() {
		this.refreshToken = null;
	}
}
