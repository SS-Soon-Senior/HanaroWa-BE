package com.ss.hanarowa.domain.member.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
	@Email(message = "올바른 이메일 형식이어야 합니다.")
	@NotBlank(message = "이메일은 필수입력 값입니다.")
	private String email;

	@NotBlank(message = "비밀번호는 필수입력 값입니다.")
	private String pwd;

}