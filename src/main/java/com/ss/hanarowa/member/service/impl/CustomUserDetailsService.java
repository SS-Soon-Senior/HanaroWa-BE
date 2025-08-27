package com.ss.hanarowa.member.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ss.hanarowa.member.dto.MemberAuthDTO;
import com.ss.hanarowa.member.entity.Member;
import com.ss.hanarowa.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.getMemberByEmail(username);

		if (member == null)
			throw new UsernameNotFoundException(username + " is Not Found!!");

		return new MemberAuthDTO(member.getEmail(),member.getPassword(), member.getRole());
	}
}