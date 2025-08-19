package com.ss.hanarowa.facility.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.ss.hanarowa.BaseEntity;
import com.ss.hanarowa.member.entity.Member;

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilityTime extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "facility",
		foreignKey = @ForeignKey(name = "fk_FacilityTime_Facility"))
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Facility facility;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member",
		foreignKey = @ForeignKey(name = "fk_FacilityTime_Member"))
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Member member;
}
