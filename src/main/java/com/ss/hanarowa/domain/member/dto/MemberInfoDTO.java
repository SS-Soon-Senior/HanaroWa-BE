package com.ss.hanarowa.domain.member.dto;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfoDTO {

	@Size(min = 6, max = 6, message = "6자리로 입력해주세요")
	@Column
	private LocalDate birth;

	@Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식이 아닙니다.")
	@Column
	private String phoneNumber;
}