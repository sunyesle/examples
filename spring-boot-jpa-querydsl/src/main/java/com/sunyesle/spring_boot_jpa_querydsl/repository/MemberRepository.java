package com.sunyesle.spring_boot_jpa_querydsl.repository;

import com.sunyesle.spring_boot_jpa_querydsl.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {
}
