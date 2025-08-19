package com.ss.hanarowa.member.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.ss.hanarowa.branch.entity.Branch;
import com.ss.hanarowa.facility.entity.FacilityTime;

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
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private LocalDate birth;

	private String password;

	private String account;

	@Column(nullable = false)
	private String phoneNumber;

	private LocalDate deletedAt;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch",
		foreignKey = @ForeignKey(name = "fk_Member_Branch"))
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Branch branch;

	@OneToMany(mappedBy = "facilityTime", cascade = CascadeType.ALL)
	@Builder.Default
	private List<FacilityTime> facilityTimes = new ArrayList<>();

	@OneToMany(mappedBy = "myLesson", cascade = CascadeType.ALL)
	@Builder.Default
	private List<MyLesson> myLessons = new ArrayList<>();
}
