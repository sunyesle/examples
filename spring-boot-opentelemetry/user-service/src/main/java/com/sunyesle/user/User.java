package com.sunyesle.user;

import jakarta.annotation.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("USERS")
public record User(@Nullable @Id Long id, String name) {
}
