package com.sunyesle.spring_boot_jpa;

import com.sunyesle.spring_boot_jpa.member.Member;
import com.sunyesle.spring_boot_jpa.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
class MemberRepositoryTest {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void setUp() {

        // Example data
        List<Member> members = Arrays.asList(
                new Member(null, "Alice"  , 28, true , LocalDateTime.parse("2024-08-08 12:00:00", formatter)),
                new Member(null, "Bob"    , 34, false, LocalDateTime.parse("2024-07-29 15:30:00", formatter)),
                new Member(null, "Charlie", 22, true , LocalDateTime.parse("2024-08-13 09:45:00", formatter)),
                new Member(null, "Diana"  , 30, true , LocalDateTime.parse("2024-08-03 18:20:00", formatter)),
                new Member(null, "Edward" , 30, false, LocalDateTime.parse("2024-07-23 22:10:00", formatter)),
                new Member(null, "Fiona"  , 31, true , LocalDateTime.parse("2024-08-16 11:05:00", formatter)),
                new Member(null, "George" , 29, true , LocalDateTime.parse("2024-08-10 14:55:00", formatter)),
                new Member(null, "Hannah" , 25, false, LocalDateTime.parse("2024-07-30 20:25:00", formatter)),
                new Member(null, "Ian"    , 33, true , LocalDateTime.parse("2024-08-06 07:15:00", formatter)),
                new Member(null, "Issac", null, true , LocalDateTime.parse("2024-08-12 16:40:00", formatter))
        );

        memberRepository.saveAll(members);
    }

    @Test
    void equals(){
        List<Member> members = memberRepository.findByName("Bob");
        System.out.println(members);
    }

    @Test
    void notEquals(){
        List<Member> members = memberRepository.findByNameNot("Bob");
        System.out.println(members);
    }

    @Test
    void between(){
        LocalDateTime startDate =  LocalDateTime.parse("2024-08-01 00:00:00", formatter);
        LocalDateTime endDate =  LocalDateTime.parse("2024-08-10 00:00:00", formatter);
        List<Member> members = memberRepository.findByCreatedAtBetween(startDate, endDate);
        System.out.println(members);
    }

    @Test
    void lessThan(){
        List<Member> members = memberRepository.findByAgeLessThan(30);
        System.out.println(members);
    }

    @Test
    void lessThanEqual(){
        List<Member> members = memberRepository.findByAgeLessThanEqual(30);
        System.out.println(members);
    }

    @Test
    void greaterThan(){
        List<Member> members = memberRepository.findByAgeGreaterThan(30);
        System.out.println(members);
    }

    @Test
    void greaterThanEqual(){
        List<Member> members = memberRepository.findByAgeGreaterThanEqual(30);
        System.out.println(members);
    }

    @Test
    void after(){
        LocalDateTime date =  LocalDateTime.parse("2024-08-01 00:00:00", formatter);
        List<Member> members = memberRepository.findByCreatedAtAfter(date);
        System.out.println(members);
    }

    @Test
    void before(){
        LocalDateTime date =  LocalDateTime.parse("2024-08-01 00:00:00", formatter);
        List<Member> members = memberRepository.findByCreatedAtBefore(date);
        System.out.println(members);
    }

    @Test
    void isNull(){
        List<Member> members = memberRepository.findByAgeNull();
        System.out.println(members);
    }

    @Test
    void isNotNull(){
        List<Member> members = memberRepository.findByAgeNotNull();
        System.out.println(members);
    }

    @Test
    void like(){
        List<Member> members = memberRepository.findByNameLike("%an%");
        System.out.println(members);
    }

    @Test
    void notLike(){
        List<Member> members = memberRepository.findByNameNotLike("%an%");
        System.out.println(members);
    }

    @Test
    void startingWith(){
        List<Member> members = memberRepository.findByNameStartingWith("I");
        System.out.println(members);
    }

    @Test
    void endingWith(){
        List<Member> members = memberRepository.findByNameEndingWith("a");
        System.out.println(members);
    }

    @Test
    void containing(){
        List<Member> members = memberRepository.findByNameContaining("o");
        System.out.println(members);
    }

    @Test
    void orderBy(){
        List<Member> members = memberRepository.findByAgeOrderByNameDesc(30);
        System.out.println(members);
    }

    @Test
    void in(){
        List<Integer> ages = Arrays.asList(22, 25, 28, 33);
        List<Member> members = memberRepository.findByAgeIn(ages);
        System.out.println(members);
    }

    @Test
    void notIn(){
        List<Integer> ages = Arrays.asList(22, 25, 28, 33);
        List<Member> members = memberRepository.findByAgeNotIn(ages);
        System.out.println(members);
    }

    @Test
    void true_(){
        List<Member> members = memberRepository.findByActiveTrue();
        System.out.println(members);
    }

    @Test
    void false_(){
        List<Member> members = memberRepository.findByActiveFalse();
        System.out.println(members);
    }

    @Test
    void ignoreCase(){
        List<Member> members = memberRepository.findByNameIgnoreCase("A");
        System.out.println(members);
    }
}
