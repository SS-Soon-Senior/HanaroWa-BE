package com.ss.hanarowa.domain.lesson.repository;
import static org.assertj.core.api.Assertions.assertThat;

import com.ss.hanarowa.domain.branch.entity.Branch;
import com.ss.hanarowa.domain.branch.entity.Location;
import com.ss.hanarowa.domain.lesson.entity.Category;
import com.ss.hanarowa.domain.lesson.entity.Lesson;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.entity.Role;
import com.ss.hanarowa.repository.RepositoryTest;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

class LessonRepositoryTest extends RepositoryTest {

	@Autowired
	private TestEntityManager tem;

	@Autowired
	private EntityManager em;

	private Branch branch1;
	private Branch branch2;
	private Member teacher;
	private Lesson savedLesson;

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

		teacher = Member.builder()
			.name("홍길동")
			.email("teacher@hana.com")
			.birth(LocalDate.of(1988, 5, 5))
			.role(Role.USERS)
			.branch(branch1)
			.build();
		tem.persist(teacher);

		savedLesson = Lesson.builder()
			.lessonName("요가 교실")
			.instructor("홍길동")
			.instruction("매주 월/수/금 10:00")
			.description("기초 요가 수업입니다.")
			.category(Category.HEALTH)
			.lessonImg(null)
			.branch(branch1)
			.member(teacher)
			.build();
		tem.persist(savedLesson);

		tem.flush();
		tem.clear();
	}

	@Test
	@Order(1)
	@DisplayName("CREATE: Lesson 신규 저장")
	void createLesson() {
		Lesson newLesson = Lesson.builder()
			.lessonName("필라테스 입문")
			.instructor("김교강")
			.instruction("매주 화/목 14:00")
			.description("근력과 유연성 향상")
			.category(Category.HEALTH)
			.branch(branch1)
			.member(null)
			.build();

		Lesson persisted = tem.persistFlushFind(newLesson);

		assertThat(persisted.getId()).isNotNull();
		assertThat(persisted.getLessonName()).isEqualTo("필라테스 입문");
		assertThat(persisted.getCategory()).isEqualTo(Category.HEALTH);
		assertThat(persisted.getBranch().getId()).isEqualTo(branch1.getId());
		assertThat(persisted.getMember()).isNull();
	}

	@Test
	@Order(2)
	@DisplayName("READ: Lesson 조회")
	void readLesson() {
		Lesson found = tem.find(Lesson.class, savedLesson.getId());

		assertThat(found).isNotNull();
		assertThat(found.getId()).isEqualTo(savedLesson.getId());
		assertThat(found.getLessonName()).isEqualTo("요가 교실");
		assertThat(found.getInstruction()).isEqualTo("매주 월/수/금 10:00");
		assertThat(found.getDescription()).isEqualTo("기초 요가 수업입니다.");
		assertThat(found.getCategory()).isEqualTo(Category.HEALTH);
		assertThat(found.getBranch().getId()).isEqualTo(branch1.getId());
		assertThat(found.getMember().getId()).isEqualTo(teacher.getId());
	}

	@Test
	@Order(3)
	@DisplayName("UPDATE: 이름/카테고리/강사 변경")
	void updateLesson() {
		Lesson toUpdate = tem.find(Lesson.class, savedLesson.getId());
		assertThat(toUpdate).isNotNull();

		toUpdate.setLessonName("요가 심화");
		toUpdate.setCategory(Category.CULTURE);
		toUpdate.setMember(null);

		tem.flush();
		tem.clear();

		Lesson reloaded = tem.find(Lesson.class, savedLesson.getId());
		assertThat(reloaded.getLessonName()).isEqualTo("요가 심화");
		assertThat(reloaded.getCategory()).isEqualTo(Category.CULTURE);
		assertThat(reloaded.getMember()).isNull();
	}

	@Test
	@Order(4)
	@DisplayName("DELETE: Lesson 삭제")
	void deleteLesson() {
		Lesson target = tem.find(Lesson.class, savedLesson.getId());
		assertThat(target).isNotNull();

		em.remove(target);
		tem.flush();
		tem.clear();

		Lesson afterDelete = tem.find(Lesson.class, savedLesson.getId());
		assertThat(afterDelete).isNull();
	}
}
