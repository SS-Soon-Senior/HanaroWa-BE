package com.ss.hanarowa.domain.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.entity.Role;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);
	List<Member> findAllByRoleNot(Role role);

	Member getMemberByEmail(String email);
	Optional<Member> findByProviderAndProviderId(String provider, String providerId);
}
