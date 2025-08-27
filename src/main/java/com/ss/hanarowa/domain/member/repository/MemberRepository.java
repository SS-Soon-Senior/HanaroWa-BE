package com.ss.hanarowa.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.hanarowa.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);

	Member getMemberByEmail(String email);
	Optional<Member> findByProviderAndProviderId(String provider, String providerId);
}