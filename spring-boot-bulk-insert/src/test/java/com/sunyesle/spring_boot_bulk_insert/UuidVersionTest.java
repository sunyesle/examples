package com.sunyesle.spring_boot_bulk_insert;

import com.sunyesle.spring_boot_bulk_insert.dto.TeamRequest;
import com.sunyesle.spring_boot_bulk_insert.support.DatabaseCleaner;
import com.sunyesle.spring_boot_bulk_insert.support.TestFixture;
import com.sunyesle.spring_boot_bulk_insert.util.UuidUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jdbc.test.autoconfigure.DataJdbcTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;
import java.util.UUID;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DatabaseCleaner.class)
class UuidVersionTest {

    @Autowired
    DatabaseCleaner databaseCleaner;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @AfterEach
    void afterEach() {
        databaseCleaner.truncate("team_uuid");
    }

    @Test
    void uuid_v4() {
        long startTime = System.currentTimeMillis();
        //===============================================

        for (int i = 0; i < 10; i++) {
            List<TeamRequest> teamRequests = TestFixture.createTeamRequest(10000, 0);

            SqlParameterSource[] batchArgs = teamRequests.stream()
                    .map(m -> new MapSqlParameterSource()
                            .addValue("teamId", UuidUtil.toBytes(UUID.randomUUID()))
                            .addValue("name", m.name()))
                    .toArray(SqlParameterSource[]::new);

            jdbcTemplate.batchUpdate("INSERT INTO team_uuid (team_id, name) VALUES (:teamId, :name)", batchArgs);
        }

        //===============================================
        long endTime = System.currentTimeMillis();
        System.out.printf("실행 시간: %d ms%n", (endTime - startTime));
    }

    @Test
    void uuid_v7() {
        long startTime = System.currentTimeMillis();
        //===============================================

        for (int i = 0; i < 10; i++) {
            List<TeamRequest> teamRequests = TestFixture.createTeamRequest(10000, 0);

            SqlParameterSource[] batchArgs = teamRequests.stream()
                    .map(m -> new MapSqlParameterSource()
                            .addValue("teamId", UuidUtil.toBytes(UuidUtil.generateUuid()))
                            .addValue("name", m.name()))
                    .toArray(SqlParameterSource[]::new);

            jdbcTemplate.batchUpdate("INSERT INTO team_uuid (team_id, name) VALUES (:teamId, :name)", batchArgs);
        }

        //===============================================
        long endTime = System.currentTimeMillis();
        System.out.printf("실행 시간: %d ms%n", (endTime - startTime));
    }
}
