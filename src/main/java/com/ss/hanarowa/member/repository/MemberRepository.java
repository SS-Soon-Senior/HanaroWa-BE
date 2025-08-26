package com.ss.hanarowa.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.hanarowa.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByProviderAndProviderId(String provider, String providerId);
	Optional<Member> findByEmail(String email);
}
