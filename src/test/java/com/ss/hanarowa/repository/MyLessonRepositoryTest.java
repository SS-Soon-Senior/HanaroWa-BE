package com.ss.hanarowa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
import com.ss.hanarowa.domain.member.repository.MyLessonRepository;

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
	private MyLessonRepository myLessonRepository;

	private Lesson lesson;
	private LessonRoom lessonRoom;
	private LessonGisu gisu1;
	private LessonGisu gisu2;
	private Member member1;
	private Member member2;

	// 테스트 전 세팅
	@BeforeEach
	void setUp() {
		Location location = Location.builder()
			.name("대전")
			.build();
		tem.persist(location);

		Branch branch = Branch.builder()
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
			.build();
		gisu1.setStartedAt(LocalDateTime.now().minusDays(30));
		gisu1.setEndedAt(LocalDateTime.now().plusDays(30));
		tem.persist(gisu1);

		gisu2 = LessonGisu.builder()
			.capacity(15)
			.lessonFee(15000)
			.duration("6주")
			.lessonState(LessonState.PENDING)
			.lesson(lesson)
			.lessonRoom(lessonRoom)
			.build();
		gisu2.setStartedAt(LocalDateTime.now().minusDays(10));
		gisu2.setEndedAt(LocalDateTime.now().plusDays(50));
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

		tem.persist(MyLesson.builder().member(member1).lessonGisu(gisu1).build());
		tem.persist(MyLesson.builder().member(member1).lessonGisu(gisu1).build());
		tem.persist(MyLesson.builder().member(member2).lessonGisu(gisu1).build());
		tem.persist(MyLesson.builder().member(member2).lessonGisu(gisu2).build());

		tem.flush();
		tem.clear();
	}

	@Test
	@Order(1)
	@DisplayName("countByLessonGisu: 특정 기수 수강신청 수")
	void countByLessonGisu() {
		int countGisu1 = myLessonRepository.countByLessonGisu(gisu1);
		int countGisu2 = myLessonRepository.countByLessonGisu(gisu2);

		assertThat(countGisu1).isEqualTo(3);
		assertThat(countGisu2).isEqualTo(1);
	}

	@Test
	@Order(2)
	@DisplayName("countByLessonGisuId: 특정 기수 ID 수강신청 수")
	void countByLessonGisuId() {
		int countGisu1 = myLessonRepository.countByLessonGisuId(gisu1.getId());
		int countGisu2 = myLessonRepository.countByLessonGisuId(gisu2.getId());

		assertThat(countGisu1).isEqualTo(3);
		assertThat(countGisu2).isEqualTo(1);
	}

	@Test
	@Order(3)
	@DisplayName("findAllByLessonGisuId: 특정 기수 ID로 조회")
	void findAllByLessonGisuId() {
		List<MyLesson> list = myLessonRepository.findAllByLessonGisuId(gisu1.getId());

		assertThat(list).hasSize(3);
		assertThat(list).allSatisfy(ml -> {
			assertThat(ml.getLessonGisu().getId()).isEqualTo(gisu1.getId());
			assertThat(ml.getMember()).isNotNull();
		});
	}

	@Test
	@Order(4)
	@DisplayName("findAllByMemberId: 특정 회원 ID로 조회")
	void findAllByMemberId() {
		List<MyLesson> list1 = myLessonRepository.findAllByMemberId(member1.getId());
		List<MyLesson> list2 = myLessonRepository.findAllByMemberId(member2.getId());

		// member1: gisu1에서 2건
		assertThat(list1).hasSize(2);
		assertThat(list1).allSatisfy(ml -> assertThat(ml.getMember().getId()).isEqualTo(member1.getId()));

		// member2: gisu1 1건 + gisu2 1건 = 총 2건
		assertThat(list2).hasSize(2);
		assertThat(list2).allSatisfy(ml -> assertThat(ml.getMember().getId()).isEqualTo(member2.getId()));
	}
}
