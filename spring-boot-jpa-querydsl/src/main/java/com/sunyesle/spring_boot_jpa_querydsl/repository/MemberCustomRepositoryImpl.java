package com.sunyesle.spring_boot_jpa_querydsl.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sunyesle.spring_boot_jpa_querydsl.dto.MemberSearchCond;
import com.sunyesle.spring_boot_jpa_querydsl.entity.Member;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sunyesle.spring_boot_jpa_querydsl.entity.QMember.member;
import static com.sunyesle.spring_boot_jpa_querydsl.entity.QTeam.team;

@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Member> search(MemberSearchCond cond) {
        return queryFactory
                .select(member)
                .from(member)
                .leftJoin(member.team, team)
                .where(
                        memberNameEq(cond.memberName()),
                        teamNameEq(cond.teamName()),
                        ageGoe(cond.ageGoe()),
                        ageLoe(cond.ageLoe())
                )
                .where()
                .fetch();
    }

    private BooleanExpression memberNameEq(String memberName) {
        return memberName != null ? member.name.eq(memberName) : null;
    }

    private BooleanExpression teamNameEq(String teamName) {
        return teamName != null ? team.name.eq(teamName) : null;
    }

    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe != null ? member.age.goe(ageGoe) : null;
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe != null ? member.age.loe(ageLoe) : null;
    }
}
