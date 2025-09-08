package com.ss.hanarowa.domain.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ss.hanarowa.domain.branch.entity.Branch;
import com.ss.hanarowa.domain.branch.entity.Location;
import com.ss.hanarowa.domain.lesson.entity.Category;
import com.ss.hanarowa.domain.lesson.entity.Lesson;
import com.ss.hanarowa.domain.lesson.entity.LessonGisu;
import com.ss.hanarowa.domain.lesson.entity.LessonRoom;
import com.ss.hanarowa.domain.lesson.entity.LessonState;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.entity.MyLesson;
import com.ss.hanarowa.domain.member.entity.Role;
import com.ss.hanarowa.repository.RepositoryTest;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

class MyLessonRepositoryTest extends RepositoryTest {

	@Autowired
	private TestEntityManager tem;

	@Autowired
	private EntityManager em;

	private Branch branch;
	private Lesson lesson;
	private LessonRoom lessonRoom;
	private LessonGisu gisu1;
	private LessonGisu gisu2;
	private Member member1;
	private Member member2;
	private MyLesson savedMyLesson;

	@BeforeEach
	void setUp() {
		Location location = Location.builder()
			.name("대전")
			.build();
		tem.persist(location);

		branch = Branch.builder()
			.name("대전 컬처뱅크")
			.address("대전광역시 서구 둔산동 123")
			.telNumber("042-123-4567")
			.location(location)
			.build();
		tem.persist(branch);

		lesson = Lesson.builder()
			.lessonName("요가 교실")
			.instructor("홍길동")
			.instruction("매주 월/수/금 10:00")
			.description("기초 요가 수업입니다.")
			.category(Category.HEALTH)
			.lessonImg(null)
			.branch(branch)
			.member(null)
			.build();
		tem.persist(lesson);

		lessonRoom = LessonRoom.builder()
			.name("101호")
			.branch(branch)
			.build();
		tem.persist(lessonRoom);

		gisu1 = LessonGisu.builder()
			.capacity(10)
			.lessonFee(10000)
			.duration("8주")
			.lessonState(LessonState.PENDING)
			.lesson(lesson)
			.lessonRoom(lessonRoom)
			.startedAt(LocalDateTime.now().minusDays(30))
			.endedAt(LocalDateTime.now().plusDays(30))
			.build();
		tem.persist(gisu1);

		gisu2 = LessonGisu.builder()
			.capacity(15)
			.lessonFee(15000)
			.duration("6주")
			.lessonState(LessonState.PENDING)
			.lesson(lesson)
			.lessonRoom(lessonRoom)
			.startedAt(LocalDateTime.now().minusDays(10))
			.endedAt(LocalDateTime.now().plusDays(50))
			.build();
		tem.persist(gisu2);

		member1 = Member.builder()
			.name("김시영")
			.email("siyoung@hana.com")
			.birth(LocalDate.of(1998, 1, 1))
			.role(Role.USERS)
			.phoneNumber("010-1111-1111")
			.branch(branch)
			.build();
		tem.persist(member1);

		member2 = Member.builder()
			.name("정소은")
			.email("soeun@hana.com")
			.birth(LocalDate.of(1999, 2, 2))
			.role(Role.USERS)
			.phoneNumber("010-2222-2222")
			.branch(branch)
			.build();
		tem.persist(member2);

		savedMyLesson = MyLesson.builder()
			.member(member1)
			.lessonGisu(gisu1)
			.build();
		tem.persist(savedMyLesson);

		tem.flush();
		tem.clear();
	}

	@Test
	@Order(1)
	@DisplayName("CREATE: MyLesson 신규 저장")
	void createMyLesson() {
		MyLesson newMyLesson = MyLesson.builder()
			.member(member2)
			.lessonGisu(gisu2)
			.build();

		MyLesson persisted = tem.persistFlushFind(newMyLesson);

		assertThat(persisted.getId()).isNotNull();
		assertThat(persisted.getMember().getId()).isEqualTo(member2.getId());
		assertThat(persisted.getLessonGisu().getId()).isEqualTo(gisu2.getId());
	}

	@Test
	@Order(2)
	@DisplayName("READ: MyLesson 조회)")
	void readMyLesson() {
		MyLesson found = tem.find(MyLesson.class, savedMyLesson.getId());

		assertThat(found).isNotNull();
		assertThat(found.getId()).isEqualTo(savedMyLesson.getId());
		assertThat(found.getMember().getId()).isEqualTo(member1.getId());
		assertThat(found.getLessonGisu().getId()).isEqualTo(gisu1.getId());
	}

	@Test
	@Order(3)
	@DisplayName("UPDATE: MyLesson의 수강 기수 변경")
	void updateMyLesson_changeGisu() {
		MyLesson toUpdate = tem.find(MyLesson.class, savedMyLesson.getId());
		assertThat(toUpdate).isNotNull();

		toUpdate.setLessonGisu(gisu2);

		tem.flush();
		tem.clear();

		MyLesson found = tem.find(MyLesson.class, savedMyLesson.getId());
		assertThat(found.getLessonGisu().getId()).isEqualTo(gisu2.getId());
	}

	@Test
	@Order(4)
	@DisplayName("DELETE: MyLesson 삭제")
	void deleteMyLesson() {
		MyLesson target = tem.find(MyLesson.class, savedMyLesson.getId());
		assertThat(target).isNotNull();

		em.remove(target);
		tem.flush();
		tem.clear();

		MyLesson afterDelete = tem.find(MyLesson.class, savedMyLesson.getId());
		assertThat(afterDelete).isNull();
	}
}
