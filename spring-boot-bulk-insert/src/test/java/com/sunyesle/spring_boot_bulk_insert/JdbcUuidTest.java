package com.sunyesle.spring_boot_bulk_insert;

import com.sunyesle.spring_boot_bulk_insert.dto.MemberRequest;
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

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DatabaseCleaner.class)
class JdbcUuidTest {

    @Autowired
    DatabaseCleaner databaseCleaner;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @AfterEach
    void afterEach() {
        databaseCleaner.truncate("team_uuid", "member_uuid");
    }

    @Test
    void single() {
        List<TeamRequest> teamRequests = TestFixture.createTeamRequest(100, 0);

        SqlParameterSource[] batchArgs = teamRequests.stream()
                .map(m -> new MapSqlParameterSource()
                        .addValue("teamId", UuidUtil.toBytes(UUID.randomUUID()))
                        .addValue("name", m.name()))
                .toArray(SqlParameterSource[]::new);

        jdbcTemplate.batchUpdate("INSERT INTO team_uuid (team_id, name) VALUES (:teamId, :name)", batchArgs);
    }

    @Test
    void relation() {
        List<TeamRequest> teamRequests = TestFixture.createTeamRequest(100, 2);

        List<MapSqlParameterSource> teamParamList = new ArrayList<>();
        List<MapSqlParameterSource> memberParamList = new ArrayList<>();

        for (TeamRequest teamDto : teamRequests) {
            byte[] teamId = UuidUtil.toBytes(UUID.randomUUID());

            teamParamList.add(new MapSqlParameterSource()
                    .addValue("teamId", teamId)
                    .addValue("name", teamDto.name())
            );

            for (MemberRequest memberDto : teamDto.members()) {
                memberParamList.add(new MapSqlParameterSource()
                        .addValue("memberId", UuidUtil.toBytes(UUID.randomUUID()), Types.BINARY)
                        .addValue("age", memberDto.age())
                        .addValue("name", memberDto.name())
                        .addValue("teamId", teamId)
                );
            }
        }

        jdbcTemplate.batchUpdate("INSERT INTO team_uuid (team_id, name) VALUES (:teamId, :name)",
                teamParamList.toArray(SqlParameterSource[]::new)
        );

        jdbcTemplate.batchUpdate("INSERT INTO member_uuid (member_id, name, age, team_id) VALUES (:memberId, :name, :age, :teamId)",
                memberParamList.toArray(SqlParameterSource[]::new)
        );
    }
}
