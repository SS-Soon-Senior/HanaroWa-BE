package com.ss.hanarowa.branch.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.ss.hanarowa.facility.entity.Facility;
import com.ss.hanarowa.lesson.entity.Lesson;
import com.ss.hanarowa.member.entity.Member;

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
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, length = 25)
	private String name;

	@Column(nullable = false, length = 50)
	private String address;

	@Column(nullable = false, length = 15, unique = true)
	private String telNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location",
		foreignKey = @ForeignKey(name="fk_Branch_Location"))
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Location location;

	@OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Facility> facilities = new ArrayList<>();

	@OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
	private List<Member> members = new ArrayList<>();


	@OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
	private List<Lesson> lessons = new ArrayList<>();
}
