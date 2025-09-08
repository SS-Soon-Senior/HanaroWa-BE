package com.ss.hanarowa.domain.member.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.dto.response.MemberAuthResponseDTO;
import com.ss.hanarowa.domain.member.repository.MemberRepository;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(username).orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

		if (member == null)
			throw new UsernameNotFoundException(username + " is Not Found!!");

		return new MemberAuthResponseDTO(member.getEmail(),member.getPassword(), member.getRole());
	}
}