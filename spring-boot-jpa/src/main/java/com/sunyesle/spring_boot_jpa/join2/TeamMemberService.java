package com.sunyesle.spring_boot_jpa.join2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamMemberService {
    private final TeamMemberRepository teamMemberRepository;

    public List<TeamMember> findAllWithTeamUsingJoin() {
        List<TeamMember> members = teamMemberRepository.findAllWithTeamUsingJoin();
        members.forEach(e -> System.out.println(e.getTeam().getName()));
        return members;
    }

    public List<TeamMember> findAllWithTeamUsingJoinSelect() {
        List<TeamMember> members = teamMemberRepository.findAllWithTeamUsingJoinSelect();
        members.forEach(e -> System.out.println(e.getTeam().getName()));
        return members;
    }

    public List<TeamMember> findAllWithTeamUsingFetchJoin() {
        List<TeamMember> members = teamMemberRepository.findAllWithTeamUsingFetchJoin();
        members.forEach(e -> System.out.println(e.getTeam().getName()));
        return members;
    }
}
