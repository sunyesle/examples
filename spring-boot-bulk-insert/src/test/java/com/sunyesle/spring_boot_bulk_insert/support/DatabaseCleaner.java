package com.sunyesle.spring_boot_bulk_insert.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.jdbc.core.JdbcTemplate;

@TestComponent
public class DatabaseCleaner {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public void truncate(String... tables) {
        for (String table : tables) {
            jdbcTemplate.execute("TRUNCATE TABLE " + table);
        }
    }
}
