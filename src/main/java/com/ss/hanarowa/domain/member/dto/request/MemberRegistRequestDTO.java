package com.ss.hanarowa.domain.member.dto.request;

import com.ss.hanarowa.domain.member.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MemberRegistRequestDTO {
	private String name;

	@Email(message = "올바른 이메일 형식이어야 합니다.")
	@NotBlank(message = "이메일은 필수입력 값입니다.")
	private String email;

	@NotBlank(message = "비밀번호는 필수입력 값입니다.")
	@Pattern(
		regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$",
		message = "비밀번호는 6~20자 사이이며 영문, 숫자를 각각 최소 1개 포함해야 합니다."
	)
	private String password;

	@Builder.Default
	private Role role = Role.USERS;

}
