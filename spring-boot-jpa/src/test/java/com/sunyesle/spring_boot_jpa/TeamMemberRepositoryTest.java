package com.sunyesle.spring_boot_jpa;

import com.sunyesle.spring_boot_jpa.join2.*;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
     */
    @Test
    void TeamJoinTest() {
        List<Team> memberUsingJoin = teamService.findAllWithMemberUsingJoin();

        assertThatThrownBy(() -> System.out.println(memberUsingJoin))
                .isInstanceOf(LazyInitializationException.class);
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
        List<Team> memberUsingJoin = teamService.findAllWithMemberUsingFetchJoin();

        System.out.println(memberUsingJoin);
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
     */
    @Test
    void TeamMemberJoinTest() {
        List<TeamMember> teamMembers = teamMemberService.findAllWithTeamUsingJoin();

        assertThatThrownBy(() -> teamMembers.forEach(e -> System.out.println(e.getTeam().getName())))
                .isInstanceOf(LazyInitializationException.class);
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
        List<TeamMember> teamMembers = teamMemberService.findAllWithTeamUsingJoinSelect();

        teamMembers.forEach(e -> System.out.println(e.getTeam().getName()));
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
        List<TeamMember> teamMembers = teamMemberService.findAllWithTeamUsingFetchJoin();

        teamMembers.forEach(e -> System.out.println(e.getTeam().getName()));
    }
}