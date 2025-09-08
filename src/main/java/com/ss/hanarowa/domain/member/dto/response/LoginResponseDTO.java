package com.ss.hanarowa.domain.member.dto.response;

import java.time.LocalDate;

import com.ss.hanarowa.domain.branch.dto.response.BranchResponseDTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDTO {
	private String url;
	private TokenResponseDTO tokens;
	private BranchResponseDTO branch;
	private String name;
	private LocalDate birth;
	private String phoneNumber;
}
