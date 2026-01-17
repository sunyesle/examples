package com.sunyesle.spring_boot_jpa_querydsl.dto;

public record MemberSearchCond(
        String memberName,
        String teamName,
        Integer ageGoe,
        Integer ageLoe
) {
}
