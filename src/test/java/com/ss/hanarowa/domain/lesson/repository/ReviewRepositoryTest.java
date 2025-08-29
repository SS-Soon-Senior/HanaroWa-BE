package com.ss.hanarowa.domain.lesson.repository;

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
import com.ss.hanarowa.domain.lesson.entity.Review;
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

class ReviewRepositoryTest extends RepositoryTest {

	@Autowired
	private TestEntityManager tem;

	@Autowired
	private EntityManager em;

	private Branch branch;
	private Lesson lesson;
	private LessonRoom lessonRoom;
	private LessonGisu gisu1;
	private Member member1;
	private Member member2;
	private Review savedReview;

	@BeforeEach
	void setUp() {
		Location location = Location.builder()
			.name("대전")
			.build();
		tem.persist(location);

		branch = Branch.builder()
			.name("대전 컬처뱅크")
			.address("대전 서구 둔산동 123")
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
			.branch(branch)
			.build();
		tem.persist(lesson);

		lessonRoom = LessonRoom.builder()
			.name("101호")
			.branch(branch)
			.build();
		tem.persist(lessonRoom);

		gisu1 = LessonGisu.builder()
			.capacity(12)
			.lessonFee(10000)
			.duration("8주")
			.lessonState(LessonState.PENDING)
			.lesson(lesson)
			.lessonRoom(lessonRoom)
			.startedAt(LocalDateTime.now().minusDays(7))
			.endedAt(LocalDateTime.now().plusDays(21))
			.build();
		tem.persist(gisu1);

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

		savedReview = Review.builder()
			.rating(4)
			.reviewTxt("수업이 알찼어요.")
			.member(member1)
			.lessonGisu(gisu1)
			.build();
		tem.persist(savedReview);

		tem.flush();
		tem.clear();
	}

	@Test
	@Order(1)
	@DisplayName("CREATE: Review 신규 저장")
	void createReview() {
		Review newReview = Review.builder()
			.rating(5)
			.reviewTxt("최고의 강의!")
			.member(member2)
			.lessonGisu(gisu1)
			.build();

		Review persisted = tem.persistFlushFind(newReview);

		assertThat(persisted.getId()).isNotNull();
		assertThat(persisted.getRating()).isEqualTo(5);
		assertThat(persisted.getReviewTxt()).isEqualTo("최고의 강의!");
		assertThat(persisted.getMember().getId()).isEqualTo(member2.getId());
		assertThat(persisted.getLessonGisu().getId()).isEqualTo(gisu1.getId());
	}

	@Test
	@Order(2)
	@DisplayName("READ: Review 조회")
	void readReview() {
		Review found = tem.find(Review.class, savedReview.getId());

		assertThat(found).isNotNull();
		assertThat(found.getId()).isEqualTo(savedReview.getId());
		assertThat(found.getRating()).isEqualTo(4);
		assertThat(found.getReviewTxt()).isEqualTo("수업이 알찼어요.");
		assertThat(found.getMember().getId()).isEqualTo(member1.getId());
		assertThat(found.getLessonGisu().getId()).isEqualTo(gisu1.getId());
	}

	@Test
	@Order(3)
	@DisplayName("UPDATE: 점수/리뷰 텍스트 변경")
	void updateReview() {
		Review toUpdate = tem.find(Review.class, savedReview.getId());
		assertThat(toUpdate).isNotNull();

		toUpdate.setRating(2);
		toUpdate.setReviewTxt("아쉬운 점이 있었어요.");

		tem.flush();
		tem.clear();

		Review reloaded = tem.find(Review.class, savedReview.getId());
		assertThat(reloaded.getRating()).isEqualTo(2);
		assertThat(reloaded.getReviewTxt()).isEqualTo("아쉬운 점이 있었어요.");
	}

	@Test
	@Order(4)
	@DisplayName("DELETE: Review 삭제")
	void deleteReview() {
		Review target = tem.find(Review.class, savedReview.getId());
		assertThat(target).isNotNull();

		em.remove(target);
		tem.flush();
		tem.clear();

		Review afterDelete = tem.find(Review.class, savedReview.getId());
		assertThat(afterDelete).isNull();
	}
}
