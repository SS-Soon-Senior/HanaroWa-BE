package com.ss.hanarowa.domain.member.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberListResponseDTO {
	private String name;
	private String branch;
	private String phone;
	private String email;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
	LocalDate birth;
}
