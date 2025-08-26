package com.ss.hanarowa.member.dto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.ss.hanarowa.member.entity.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO extends User{
	private Long id;

	private String email;

	private String password;

	private Role role;


	public MemberDTO(String email, String password, Role role) {
		super(email, password, Collections.singletonList(new SimpleGrantedAuthority(role.name())));
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public Map<String, Object> getClaims() {
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		map.put("pwd", password);
		map.put("role", role);

		return map;
	}
}
