package com.sunyesle.spring_boot_jpa.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByName(String name);
    List<Member> findByNameNot(String name);
    List<Member> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Member> findByAgeLessThan(Integer age);
    List<Member> findByAgeLessThanEqual(Integer age);
    List<Member> findByAgeGreaterThan(Integer age);
    List<Member> findByAgeGreaterThanEqual(Integer age);
    List<Member> findByCreatedAtAfter(LocalDateTime date);
    List<Member> findByCreatedAtBefore(LocalDateTime date);
    List<Member> findByAgeNull();
    List<Member> findByAgeNotNull();
    List<Member> findByNameLike(String name);
    List<Member> findByNameNotLike(String name);
    List<Member> findByNameStartingWith(String str);
    List<Member> findByNameEndingWith(String str);
    List<Member> findByNameContaining(String str);
    List<Member> findByAgeOrderByNameDesc(Integer age);
    List<Member> findByAgeIn(Collection<Integer> ages);
    List<Member> findByAgeNotIn(Collection<Integer> ages);
    List<Member> findByActiveTrue();
    List<Member> findByActiveFalse();
    List<Member> findByNameIgnoreCase(String name);
}
