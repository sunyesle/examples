package com.sunyesle.spring_boot_jpa.join2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    public List<Team> findAllWithMemberUsingJoin() {
        return teamRepository.findAllWithMemberUsingJoin();
    }

    public List<Team> findAllWithMemberUsingFetchJoin() {
        return teamRepository.findAllWithMemberUsingFetchJoin();
    }
}
