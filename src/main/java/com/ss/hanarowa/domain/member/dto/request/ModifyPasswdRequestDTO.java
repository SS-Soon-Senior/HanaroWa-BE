package com.ss.hanarowa.domain.member.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyPasswdRequestDTO {
	private String currentPassword; //현재 비밀번호

	@Pattern(
		regexp = "^(?=.*[가-힣A-Za-z])(?=.*\\d)[가-힣A-Za-z\\d]{6,20}$",
		message = "비밀번호는 6~20자 사이이며, 문자, 숫자를 각각 최소 1개 포함해야 합니다."
	)
	private String newPassword; //새 비밀번호

}
