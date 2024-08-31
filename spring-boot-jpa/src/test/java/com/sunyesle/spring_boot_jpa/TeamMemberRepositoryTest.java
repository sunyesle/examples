package com.sunyesle.spring_boot_jpa;

import com.sunyesle.spring_boot_jpa.join2.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TeamMemberRepositoryTest {
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamMemberRepository teamMemberRepository;

    @Autowired
    TeamService teamService;

    @Autowired
    TeamMemberService teamMemberService;

    @BeforeEach
    void setUp() {
        teamMemberRepository.deleteAll();
        teamRepository.deleteAll();
        teamRepository.flush();

        Team team1 = new Team("team1");
        Team team2 = new Team("team2");

        TeamMember member1 = new TeamMember("team1 member1");
        TeamMember member2 = new TeamMember("team1 member2");
        TeamMember member3 = new TeamMember("team2 member3");

        team1.addMember(member1);
        team1.addMember(member2);

        team2.addMember(member3);

        teamRepository.save(team1);
        teamRepository.save(team2);
    }

    // Team과 TeamMember는 1:N 관계다

    // ===========================
    // FROM절에 1인 Team 사용
    // ===========================

    /*
    일반 Join : join 조건을 제외하고 실제 질의하는 대상 Entity에 대한 컬럼만 SELECT

    select
        t1_0.id,
        t1_0.name
    from
        team t1_0
    join
        team_member m1_0
            on t1_0.id=m1_0.team_id

    추가 쿼리 발생
    select
        m1_0.team_id,
        m1_0.id,
        m1_0.name
    from
        team_member m1_0
    where
        m1_0.team_id=3
     */
    @Test
    void TeamJoinTest() {
        teamService.findAllWithMemberUsingJoin();// N+1
    }

    /*
    FetchJoin : 실제 질의하는 대상 Entity와 Fetch join이 걸려있는 Entity를 포함한 컬럼 함께 SELECT

    select
        t1_0.id,
        m1_0.team_id,
        m1_0.id,
        m1_0.name,
        t1_0.name
    from
        team t1_0
    join
        team_member m1_0
            on t1_0.id=m1_0.team_id
     */
    @Test
    void TeamFetchJoinTest() {
        teamService.findAllWithMemberUsingFetchJoin();
    }

    // ===========================
    // FROM절에 N인 TeamMember 사용
    // ===========================

    /*
    select
        tm1_0.id,
        tm1_0.name,
        tm1_0.team_id
    from
        team_member tm1_0
    join
        team t1_0
            on t1_0.id=tm1_0.team_id

    추가 쿼리 발생
    select
        t1_0.id,
        t1_0.name
    from
        team t1_0
    where
        t1_0.id=9
     */
    @Test
    void TeamMemberJoinTest() {
        teamMemberService.findAllWithTeamUsingJoin();
    }

    /*
    select
        tm1_0.id,
        tm1_0.name,
        tm1_0.team_id,
        t1_0.id,
        t1_0.name
    from
        team_member tm1_0
    join
        team t1_0
            on t1_0.id=tm1_0.team_id
     */
    @Test
    void TeamMemberJoinSelectTest() {
        teamMemberService.findAllWithTeamUsingJoinSelect();
    }

    /*
    select
        tm1_0.id,
        tm1_0.name,
        t1_0.id,
        t1_0.name
    from
        team_member tm1_0
    join
        team t1_0
            on t1_0.id=tm1_0.team_id
     */
    @Test
    void TeamMemberFetchJoinTest() {
        teamMemberService.findAllWithTeamUsingFetchJoin();
    }
}
