package com.ss.hanarowa.domain.lesson.repository;

import com.ss.hanarowa.repository.RepositoryTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.ss.hanarowa.domain.branch.entity.Branch;
import com.ss.hanarowa.domain.branch.entity.Location;
import com.ss.hanarowa.domain.lesson.entity.LessonRoom;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

class LessonRoomRepositoryTest extends RepositoryTest {
	@Autowired
	private TestEntityManager tem;

	@Autowired
	private EntityManager em;

	private Branch branch1;
	private Branch branch2;
	private LessonRoom savedRoom;

	@BeforeEach
	void setUp() {
		Location locDaejeon = Location.builder()
			.name("대전")
			.build();
		tem.persist(locDaejeon);

		Location locChuncheon = Location.builder()
			.name("춘천")
			.build();
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

		savedRoom = LessonRoom.builder()
			.name("101호")
			.branch(branch1)
			.build();
		tem.persist(savedRoom);

		tem.flush();
		tem.clear();
	}

	@Test
	@Order(1)
	@DisplayName("CREATE: LessonRoom 신규 저장")
	void createLessonRoom() {
		LessonRoom newRoom = LessonRoom.builder()
			.name("102호")
			.branch(branch1)
			.build();

		LessonRoom persisted = tem.persistFlushFind(newRoom);

		assertThat(persisted.getId()).isNotNull();
		assertThat(persisted.getName()).isEqualTo("102호");
		assertThat(persisted.getBranch().getId()).isEqualTo(branch1.getId());
	}

	@Test
	@Order(2)
	@DisplayName("READ: LessonRoom 조회")
	void readLessonRoom() {
		LessonRoom found = tem.find(LessonRoom.class, savedRoom.getId());

		assertThat(found).isNotNull();
		assertThat(found.getId()).isEqualTo(savedRoom.getId());
		assertThat(found.getName()).isEqualTo("101호");
		assertThat(found.getBranch().getId()).isEqualTo(branch1.getId());
	}

	@Test
	@Order(3)
	@DisplayName("UPDATE: LessonRoom 이름 변경")
	void updateLessonRoom() {
		LessonRoom toUpdate = tem.find(LessonRoom.class, savedRoom.getId());
		assertThat(toUpdate).isNotNull();

		toUpdate.setName("리뉴얼 101호");

		tem.flush();
		tem.clear();

		LessonRoom reloaded = tem.find(LessonRoom.class, savedRoom.getId());
		assertThat(reloaded.getName()).isEqualTo("리뉴얼 101호");
	}

	@Test
	@Order(4)
	@DisplayName("DELETE: LessonRoom 삭제")
	void deleteLessonRoom() {
		LessonRoom target = tem.find(LessonRoom.class, savedRoom.getId());
		assertThat(target).isNotNull();

		em.remove(target);
		tem.flush();
		tem.clear();

		LessonRoom afterDelete = tem.find(LessonRoom.class, savedRoom.getId());
		assertThat(afterDelete).isNull();
	}
}
