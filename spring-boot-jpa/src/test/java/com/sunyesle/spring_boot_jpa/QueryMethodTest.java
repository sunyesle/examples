package com.sunyesle.spring_boot_jpa;

import com.sunyesle.spring_boot_jpa.entity.Address;
import com.sunyesle.spring_boot_jpa.entity.Member;
import com.sunyesle.spring_boot_jpa.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class QueryMethodTest {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        Address address = new Address("서울", "테헤란로", "12345");

        List<Member> members = Arrays.asList(
                new Member("Alice"  , 28  , true , LocalDateTime.parse("2024-08-01 00:00:00", formatter), address),
                new Member("Bob"    , 34  , false, LocalDateTime.parse("2024-07-29 15:30:00", formatter), address),
                new Member("Charlie", 22  , true , LocalDateTime.parse("2024-08-13 09:45:00", formatter), address),
                new Member("Diana"  , 30  , true , LocalDateTime.parse("2024-08-03 18:20:00", formatter), address),
                new Member("Edward" , 30  , false, LocalDateTime.parse("2024-07-23 22:10:00", formatter), address),
                new Member("Fiona"  , 31  , true , LocalDateTime.parse("2024-08-16 11:05:00", formatter), address),
                new Member("George" , 29  , true , LocalDateTime.parse("2024-08-10 00:00:00", formatter), address),
                new Member("Hannah" , 25  , false, LocalDateTime.parse("2024-07-30 20:25:00", formatter), address),
                new Member("Ian"    , 33  , true , LocalDateTime.parse("2024-08-06 07:15:00", formatter), address),
                new Member("Issac"  , null, true , LocalDateTime.parse("2024-08-12 16:40:00", formatter), address)
        );

        memberRepository.saveAll(members);
    }

    @Test
    @DisplayName("Equals: 이름이 Bob인 회원 조회")
    void equals() {
        List<Member> members = memberRepository.findByName("Bob");

        assertThat(members)
                .hasSize(1)
                .allSatisfy(m -> assertThat(m.getName()).isEqualTo("Bob"));
    }

    @Test
    @DisplayName("Not: 이름이 Bob이 아닌 회원 조회")
    void notEquals() {
        List<Member> members = memberRepository.findByNameNot("Bob");

        assertThat(members)
                .hasSize(9)
                .allSatisfy(m -> assertThat(m.getName()).isNotEqualTo("Bob"));
    }

    @Test
    @DisplayName("LessThan: 30세 미만 회원 조회")
    void lessThan() {
        List<Member> members = memberRepository.findByAgeLessThan(30);

        assertThat(members)
                .hasSize(4)
                .allSatisfy(m -> assertThat(m.getAge()).isLessThan(30));
    }

    @Test
    @DisplayName("LessThanEqual: 30세 이하 회원 조회")
    void lessThanEqual() {
        List<Member> members = memberRepository.findByAgeLessThanEqual(30);

        assertThat(members)
                .hasSize(6)
                .allSatisfy(m -> assertThat(m.getAge()).isLessThanOrEqualTo(30));
    }

    @Test
    @DisplayName("GreaterThan: 30세 초과 회원 조회")
    void greaterThan() {
        List<Member> members = memberRepository.findByAgeGreaterThan(30);

        assertThat(members)
                .hasSize(3)
                .allSatisfy(m -> assertThat(m.getAge()).isGreaterThan(30));
    }

    @Test
    @DisplayName("GreaterThanEqual: 30세 이상 회원 조회")
    void greaterThanEqual() {
        List<Member> members = memberRepository.findByAgeGreaterThanEqual(30);

        assertThat(members)
                .hasSize(5)
                .allSatisfy(m -> assertThat(m.getAge()).isGreaterThanOrEqualTo(30));
    }

    @Test
    @DisplayName("Between: 8월 1일부터 8월 10일 사이 가입자 조회 (이상, 이하)")
    void between() {
        LocalDateTime startDate = LocalDateTime.parse("2024-08-01 00:00:00", formatter);
        LocalDateTime endDate = LocalDateTime.parse("2024-08-10 00:00:00", formatter);
        List<Member> members = memberRepository.findByCreatedAtBetween(startDate, endDate);

        assertThat(members)
                .hasSize(4)
                .allSatisfy(m -> {
                    assertThat(m.getCreatedAt()).isAfterOrEqualTo(startDate);
                    assertThat(m.getCreatedAt()).isBeforeOrEqualTo(endDate);
                });
    }

    @Test
    @DisplayName("After: 8월 1일 이후 가입자 조회 (초과)")
    void after() {
        LocalDateTime date = LocalDateTime.parse("2024-08-01 00:00:00", formatter);
        List<Member> members = memberRepository.findByCreatedAtAfter(date);

        assertThat(members)
                .hasSize(6)
                .allSatisfy(m -> assertThat(m.getCreatedAt()).isAfter(date));
    }

    @Test
    @DisplayName("Before: 8월 1일 이전 가입자 조회 (미만)")
    void before() {
        LocalDateTime date = LocalDateTime.parse("2024-08-01 00:00:00", formatter);
        List<Member> members = memberRepository.findByCreatedAtBefore(date);

        assertThat(members)
                .hasSize(3)
                .allSatisfy(m -> assertThat(m.getCreatedAt()).isBefore(date));
    }

    @Test
    @DisplayName("Null: 나이가 null 값인 회원 조회")
    void isNull() {
        List<Member> members = memberRepository.findByAgeNull();

        assertThat(members)
                .hasSize(1)
                .allSatisfy(m -> assertThat(m.getAge()).isNull());
    }

    @Test
    @DisplayName("NotNull: 나이가 null 값이 아닌 회원 조회")
    void isNotNull() {
        List<Member> members = memberRepository.findByAgeNotNull();

        assertThat(members)
                .hasSize(9)
                .allSatisfy(m -> assertThat(m.getAge()).isNotNull());
    }

    @Test
    @DisplayName("Like: 이름에 an이 포함된 회원 조회")
    void like() {
        List<Member> members = memberRepository.findByNameLike("%an%");

        assertThat(members)
                .hasSize(3)
                .allSatisfy(m -> assertThat(m.getName()).contains("an"));
    }

    @Test
    @DisplayName("NotLike: 이름에 an이 포함되지 않은 회원 조회")
    void notLike() {
        List<Member> members = memberRepository.findByNameNotLike("%an%");

        assertThat(members)
                .hasSize(7)
                .allSatisfy(m -> assertThat(m.getName()).doesNotContain("an"));
    }

    @Test
    @DisplayName("Containing: 이름에 o가 포함된 회원 조회")
    void containing() {
        // like과 다르게 자동으로 %을 붙여서 쿼리를 생성한다.
        List<Member> members = memberRepository.findByNameContaining("o");

        assertThat(members)
                .hasSize(3)
                .allSatisfy(m -> assertThat(m.getName()).contains("o"));
    }

    @Test
    @DisplayName("StartingWith: 이름이 I로 시작되는 회원 조회")
    void startingWith() {
        List<Member> members = memberRepository.findByNameStartingWith("I");

        assertThat(members)
                .hasSize(2)
                .allSatisfy(m -> assertThat(m.getName()).startsWith("I"));
    }

    @Test
    @DisplayName("EndingWith: 이름이 a로 끝나는 회원 조회")
    void endingWith() {
        List<Member> members = memberRepository.findByNameEndingWith("a");

        assertThat(members)
                .hasSize(2)
                .allSatisfy(m -> assertThat(m.getName()).endsWith("a"));
    }

    @Test
    @DisplayName("OrderBy: 나이가 30인 회원을 이름 내림차순으로 조회")
    void orderBy() {
        List<Member> members = memberRepository.findByAgeOrderByNameDesc(30);

        assertThat(members)
                .hasSize(2)
                .allSatisfy(m -> assertThat(m.getAge()).isEqualTo(30))
                .extracting(Member::getName)
                .containsExactly("Edward", "Diana"); // 순서까지 검증
    }

    @Test
    @DisplayName("In: 특정 나이에 포함된 회원 조회")
    void in() {
        List<Integer> ages = Arrays.asList(22, 25, 28);
        List<Member> members = memberRepository.findByAgeIn(ages);

        assertThat(members)
                .hasSize(3)
                .allSatisfy(m -> {
                    assertThat(m.getAge()).isIn(ages);
                });
    }

    @Test
    @DisplayName("NotIn: 특정 나이에 포함되지 않은 회원 조회")
    void notIn() {
        List<Integer> ages = Arrays.asList(22, 25, 28);
        List<Member> members = memberRepository.findByAgeNotIn(ages);

        assertThat(members)
                .hasSize(6)
                .allSatisfy(m -> {
                    assertThat(m.getAge()).isNotIn(ages);
                    assertThat(m.getAge()).isNotNull(); // NOT IN은 null을 결과에서 제외한다.
                });
    }

    @Test
    @DisplayName("True: 활성화(active=true) 상태인 회원 조회")
    void true_() {
        List<Member> members = memberRepository.findByActiveTrue();

        assertThat(members)
                .hasSize(7)
                .allSatisfy(member -> assertThat(member.isActive()).isTrue());
    }

    @Test
    @DisplayName("False: 비활성화(active=false) 상태인 회원 조회")
    void false_() {
        List<Member> members = memberRepository.findByActiveFalse();

        assertThat(members)
                .hasSize(3)
                .allSatisfy(member -> assertThat(member.isActive()).isFalse());
    }

    @Test
    @DisplayName("IgnoreCase: 대소문자 구분 없이 이름 조회")
    void ignoreCase() {
        List<Member> members = memberRepository.findByNameIgnoreCase("ALICE");

        assertThat(members)
                .hasSize(1)
                .allSatisfy(m -> assertThat(m.getName()).isEqualTo("Alice"));
    }
}
