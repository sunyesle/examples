package com.sunyesle.spring_boot_bulk_insert.dto;

import java.util.List;

public record TeamRequest(String name, List<MemberRequest> members) {
}
