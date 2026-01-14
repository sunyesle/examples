package com.sunyesle.spring_boot_jpa_querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sunyesle.spring_boot_jpa_querydsl.config.DataJpaQuerydslTest;
import com.sunyesle.spring_boot_jpa_querydsl.entity.Company;
import com.sunyesle.spring_boot_jpa_querydsl.entity.Member;
import com.sunyesle.spring_boot_jpa_querydsl.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.sunyesle.spring_boot_jpa_querydsl.entity.QCompany.company;
import static com.sunyesle.spring_boot_jpa_querydsl.entity.QMember.member;
import static com.sunyesle.spring_boot_jpa_querydsl.entity.QTeam.team;

@DataJpaQuerydslTest
class QuerydslJoinAliasTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @BeforeEach
    void setup() {
        Company companyA = new Company("CompanyA");
        Company companyB = new Company("CompanyB");
        em.persist(companyA);
        em.persist(companyB);

        Team teamA = new Team("TeamA", companyA);
        Team teamB = new Team("TeamB", companyB);
        em.persist(teamA);
        em.persist(teamB);

        em.persist(new Member("member1", 10, teamA));
        em.persist(new Member("member2", 20, teamA));
        em.persist(new Member("member3", 30, teamB));
        em.persist(new Member("member4", 40, teamB));
    }

    @Test
    void bad() {
        System.out.println("=== [BAD] 묵시적 조인 발생 ===");
        // 의도하지 않은 조인이 여러번 발생하는 것을 확인할 수 있다.
        List<Tuple> result = queryFactory
                .select(member, team, company)
                .from(member)
                .join(member.team.company, company)
                .join(member.team, team)
                .where(
                        member.team.name.eq("TeamA"),
                        member.team.company.name.eq("CompanyA")
                )
                .fetch();
        System.out.println("============================");
        System.out.println(result);
    }

    @Test
    void good() {
        System.out.println("===== [GOOD] 명시적 조인 =====");
        // 조인은 부모에서 자식으로, 이미 선언된 별칭을 기준으로 이어간다.
        // 별칭을 사용하여 묵시적 조인을 방지한다.
        List<Tuple> result = queryFactory
                .select(member, team, company)
                .from(member)

                .join(member.team, team) // Member -> Team
                .join(team.company, company) // Team -> Company
                .where(
                        team.name.eq("TeamA"),
                        company.name.eq("CompanyA")
                )
                .fetch();
        System.out.println("============================");
        System.out.println(result);
    }
}
