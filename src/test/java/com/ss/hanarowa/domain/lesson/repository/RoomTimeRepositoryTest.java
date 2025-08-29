package com.ss.hanarowa.domain.lesson.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import com.ss.hanarowa.domain.branch.entity.Branch;
import com.ss.hanarowa.domain.branch.entity.Location;
import com.ss.hanarowa.domain.lesson.entity.LessonRoom;
import com.ss.hanarowa.domain.lesson.entity.RoomTime;
import com.ss.hanarowa.repository.RepositoryTest;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static java.time.temporal.ChronoUnit.SECONDS;

class RoomTimeRepositoryTest extends RepositoryTest {

	@Autowired
	private TestEntityManager tem;

	@Autowired
	private EntityManager em;

	private Branch branch;
	private LessonRoom room101;
	private LessonRoom room102;
	private RoomTime savedRoomTime;

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

		room101 = LessonRoom.builder()
			.name("101호")
			.branch(branch)
			.build();
		tem.persist(room101);

		room102 = LessonRoom.builder()
			.name("102호")
			.branch(branch)
			.build();
		tem.persist(room102);

		savedRoomTime = RoomTime.builder()
			.lessonRoom(room101)
			.startedAt(LocalDateTime.now().minusDays(7))
			.endedAt(LocalDateTime.now().plusDays(7))
			.build();
		tem.persist(savedRoomTime);

		tem.flush();
		tem.clear();
	}

	@Test
	@Order(1)
	@DisplayName("CREATE: RoomTime 신규 저장")
	void createRoomTime() {
		RoomTime newRoomTime = RoomTime.builder()
			.lessonRoom(room102)
			.startedAt(LocalDateTime.now().minusDays(1))
			.endedAt(LocalDateTime.now().plusDays(1))
			.build();

		RoomTime persisted = tem.persistFlushFind(newRoomTime);

		assertThat(persisted.getId()).isNotNull();
		assertThat(persisted.getLessonRoom().getId()).isEqualTo(room102.getId());
		assertThat(persisted.getStartedAt()).isNotNull();
		assertThat(persisted.getEndedAt()).isNotNull();
	}

	@Test
	@Order(2)
	@DisplayName("READ: RoomTime 조회")
	void readRoomTime() {
		RoomTime found = tem.find(RoomTime.class, savedRoomTime.getId());

		assertThat(found).isNotNull();
		assertThat(found.getId()).isEqualTo(savedRoomTime.getId());
		assertThat(found.getLessonRoom().getId()).isEqualTo(room101.getId());
		assertThat(found.getStartedAt()).isBefore(found.getEndedAt());
	}

	@Test
	@Order(3)
	@DisplayName("UPDATE: lessonRoom 및 기간(startedAt/endedAt) 변경")
	void updateRoomTime() {
		RoomTime toUpdate = tem.find(RoomTime.class, savedRoomTime.getId());
		assertThat(toUpdate).isNotNull();

		LocalDateTime newStart = LocalDateTime.now().minusDays(3);
		LocalDateTime newEnd   = LocalDateTime.now().plusDays(14);

		toUpdate.setLessonRoom(room102);
		toUpdate.setStartedAt(newStart);
		toUpdate.setEndedAt(newEnd);

		tem.flush();
		tem.clear();

		RoomTime reloaded = tem.find(RoomTime.class, savedRoomTime.getId());
		assertThat(reloaded.getLessonRoom().getId()).isEqualTo(room102.getId());


		assertThat(reloaded.getStartedAt().truncatedTo(SECONDS))
			.isEqualTo(newStart.truncatedTo(SECONDS));

		assertThat(reloaded.getEndedAt().truncatedTo(SECONDS))
			.isEqualTo(newEnd.truncatedTo(SECONDS));
	}

	@Test
	@Order(4)
	@DisplayName("DELETE: RoomTime 삭제")
	void deleteRoomTime() {
		RoomTime target = tem.find(RoomTime.class, savedRoomTime.getId());
		assertThat(target).isNotNull();

		em.remove(target);
		tem.flush();
		tem.clear();

		RoomTime afterDelete = tem.find(RoomTime.class, savedRoomTime.getId());
		assertThat(afterDelete).isNull();
	}
}
