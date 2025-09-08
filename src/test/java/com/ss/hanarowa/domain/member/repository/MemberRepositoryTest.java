package com.ss.hanarowa.domain.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import com.ss.hanarowa.domain.branch.entity.Branch;
import com.ss.hanarowa.domain.branch.entity.Location;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.entity.Role;
import com.ss.hanarowa.repository.RepositoryTest;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

class MemberRepositoryTest extends RepositoryTest {

	@Autowired
	private TestEntityManager tem;

	@Autowired
	private EntityManager em;

	private Branch branch1;
	private Branch branch2;
	private Member savedMember;

	@BeforeEach
	void setUp() {
		Location locDaejeon = Location.builder().name("대전").build();
		tem.persist(locDaejeon);

		Location locChuncheon = Location.builder().name("춘천").build();
		tem.persist(locChuncheon);

		branch1 = Branch.builder()
			.name("대전 컬처뱅크")
			.address("대전 서구 둔산동 123")
			.telNumber("042-123-4567")
			.location(locDaejeon)
			.build();
		tem.persist(branch1);

		branch2 = Branch.builder()
			.name("춘천 컬처뱅크")
			.address("강원 춘천시 중앙로 77")
			.telNumber("033-987-6543")
			.location(locChuncheon)
			.build();
		tem.persist(branch2);

		savedMember = Member.builder()
			.name("김시영")
			.email("siyoung@hana.com")
			.birth(LocalDate.of(1998, 1, 1))
			.password("$2a$10$dummy")
			.phoneNumber("010-1111-1111")
			.role(Role.USERS)
			.branch(branch1)
			.build();
		tem.persist(savedMember);

		tem.flush();
		tem.clear();
	}

	@Test
	@Order(1)
	@DisplayName("CREATE: Member 신규 저장")
	void createMember() {
		Member newMember = Member.builder()
			.name("정소은")
			.email("soeun@hana.com")
			.birth(LocalDate.of(1999, 2, 2))
			.password("$2a$10$dummy")
			.phoneNumber("010-2222-2222")
			.role(Role.ADMIN)
			.branch(branch2)
			.build();

		Member persisted = tem.persistFlushFind(newMember);

		assertThat(persisted.getId()).isNotNull();
		assertThat(persisted.getName()).isEqualTo("정소은");
		assertThat(persisted.getEmail()).isEqualTo("soeun@hana.com");
		assertThat(persisted.getRole()).isEqualTo(Role.ADMIN);
		assertThat(persisted.getBranch().getId()).isEqualTo(branch2.getId());
	}

	@Test
	@Order(2)
	@DisplayName("READ: Member 조회")
	void readMember() {
		Member found = tem.find(Member.class, savedMember.getId());

		assertThat(found).isNotNull();
		assertThat(found.getId()).isEqualTo(savedMember.getId());
		assertThat(found.getName()).isEqualTo("김시영");
		assertThat(found.getEmail()).isEqualTo("siyoung@hana.com");
		assertThat(found.getRole()).isEqualTo(Role.USERS);
		assertThat(found.getBranch().getId()).isEqualTo(branch1.getId());
	}

	@Test
	@Order(3)
	@DisplayName("UPDATE: 전화번호/지점 변경")
	void updateMember() {
		Member toUpdate = tem.find(Member.class, savedMember.getId());
		assertThat(toUpdate).isNotNull();

		toUpdate.setPhoneNumber("010-9999-0000");
		toUpdate.setBranch(branch2);

		tem.flush();
		tem.clear();

		Member reloaded = tem.find(Member.class, savedMember.getId());
		assertThat(reloaded.getPhoneNumber()).isEqualTo("010-9999-0000");
		assertThat(reloaded.getBranch().getId()).isEqualTo(branch2.getId());
	}

	@Test
	@Order(4)
	@DisplayName("DELETE: Member 삭제")
	void deleteMember() {
		Member target = tem.find(Member.class, savedMember.getId());
		assertThat(target).isNotNull();

		em.remove(target);
		tem.flush();
		tem.clear();

		Member afterDelete = tem.find(Member.class, savedMember.getId());
		assertThat(afterDelete).isNull();
	}
}
