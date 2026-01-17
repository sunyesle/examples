package com.sunyesle.spring_boot_jpa_querydsl.repository;

import com.sunyesle.spring_boot_jpa_querydsl.dto.MemberSearchCond;
import com.sunyesle.spring_boot_jpa_querydsl.entity.Member;

import java.util.List;

public interface MemberCustomRepository {
    List<Member> search(MemberSearchCond cond);
}
