package com.sunyesle.spring_boot_bulk_insert;

import com.sunyesle.spring_boot_bulk_insert.dto.TeamRequest;
import com.sunyesle.spring_boot_bulk_insert.entity.MemberIdentity;
import com.sunyesle.spring_boot_bulk_insert.entity.TeamIdentity;
import com.sunyesle.spring_boot_bulk_insert.repository.TeamIdentityRepository;
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

// ID 생성 전략이 IDENTITY인 경우, JPA로 배치처리가 불가능하다.
@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DatabaseCleaner.class)
class JpaIdentityTest {

    @Autowired
    DatabaseCleaner databaseCleaner;

    @Autowired
    TeamIdentityRepository teamIdentityRepository;

    @AfterEach
    void afterEach() {
        databaseCleaner.truncate("team_identity", "member_identity");
    }

    @Test
    void single() {
        List<TeamRequest> teamRequests = TestFixture.createTeamRequest(100, 0);

        List<TeamIdentity> teams = teamRequests.stream()
                .map(t -> new TeamIdentity(t.name()))
                .collect(Collectors.toList());

        teamIdentityRepository.saveAll(teams);
        teamIdentityRepository.flush();
    }


    @Test
    void relation() {
        List<TeamRequest> teamRequests = TestFixture.createTeamRequest(100, 2);

        List<TeamIdentity> teams = teamRequests.stream()
                .map(request -> {
                    // Team 생성
                    TeamIdentity team = new TeamIdentity(request.name());

                    // Member 생성 및 연관 관계 설정
                    request.members().forEach(m -> {
                        MemberIdentity member = new MemberIdentity(m.name(), m.age());
                        team.addMember(member); // Team 내부 리스트에 추가
                    });

                    return team;
                })
                .collect(Collectors.toList());

        teamIdentityRepository.saveAll(teams);
        teamIdentityRepository.flush();
    }
}
