package com.sunyesle.spring_boot_bulk_insert.support;

import com.sunyesle.spring_boot_bulk_insert.dto.MemberRequest;
import com.sunyesle.spring_boot_bulk_insert.dto.TeamRequest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestFixture {
    public static List<TeamRequest> createTeamRequest(int teamCount, int membersPerTeam) {
        return IntStream.range(0, teamCount)
                .mapToObj(i -> {
                    // 팀별 멤버 목록 생성
                    List<MemberRequest> members = IntStream.range(0, membersPerTeam)
                            .mapToObj(j -> {
                                int memberIndex = (i * membersPerTeam) + j;
                                return new MemberRequest("member" + memberIndex, 20);
                            })
                            .collect(Collectors.toList());

                    // 팀 생성
                    return new TeamRequest("team" + i, members);
                })
                .collect(Collectors.toList());
    }
}
