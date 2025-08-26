package com.ss.hanarowa.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ss.hanarowa.member.entity.Member;
import com.ss.hanarowa.member.repository.MemberRepository;

class MemberRepositoryTest extends RepositoryTest{
	@Autowired
	MemberRepository memberRepository;

	@Test
	void saveTest() {
		Member m = Member.builder()
			.email("yo@naver.com")
			.name("yoyo")
			.password("dodo").build();

		Member mbr = Member.builder()
			.email("yo2@naver.com")
			.name("momo")
			.password("dododo")
			.build();

		Member savedM = memberRepository.save(m);
		Member savedMbr = memberRepository.save(mbr);


		Member foundM = memberRepository.findById(savedM.getId()).orElseThrow();
		Member foundMbr = memberRepository.findById(savedMbr.getId()).orElseThrow();

		assertEquals(savedM.getName(), foundM.getName());
		assertEquals(savedM, foundM);
		assertEquals(savedMbr, foundMbr);
		System.out.println("foundM = " + foundM);
		System.out.println("foundMbr = " + foundMbr);
	}

}