package com.sunyesle.spring_boot_bulk_insert;

import com.sunyesle.spring_boot_bulk_insert.dto.TeamRequest;
import com.sunyesle.spring_boot_bulk_insert.entity.MemberUuid;
import com.sunyesle.spring_boot_bulk_insert.entity.TeamUuid;
import com.sunyesle.spring_boot_bulk_insert.repository.TeamUuidRepository;
import com.sunyesle.spring_boot_bulk_insert.support.DatabaseCleaner;
import com.sunyesle.spring_boot_bulk_insert.support.TestFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.stream.Collectors;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DatabaseCleaner.class)
class JpaUuidTest {

    @Autowired
    DatabaseCleaner databaseCleaner;

    @Autowired
    TeamUuidRepository teamUuidRepository;

    @AfterEach
    void afterEach() {
        databaseCleaner.truncate("team_uuid", "member_uuid");
    }

    @Test
    void single() {
        List<TeamRequest> teamRequests = TestFixture.createTeamRequest(100, 0);

        List<TeamUuid> teams = teamRequests.stream()
                .map(t -> new TeamUuid(t.name()))
                .collect(Collectors.toList());

        teamUuidRepository.saveAll(teams);
        teamUuidRepository.flush();
    }

    @Test
    void relation() {
        List<TeamRequest> teamRequests = TestFixture.createTeamRequest(100, 2);

        List<TeamUuid> teams = teamRequests.stream()
                .map(request -> {
                    // Team 생성
                    TeamUuid team = new TeamUuid(request.name());

                    // Member 생성 및 연관 관계 설정
                    request.members().forEach(m -> {
                        MemberUuid member = new MemberUuid(m.name(), m.age());
                        team.addMember(member); // Team 내부 리스트에 추가
                    });

                    return team;
                })
                .collect(Collectors.toList());

        teamUuidRepository.saveAll(teams);
        teamUuidRepository.flush();
    }
}
