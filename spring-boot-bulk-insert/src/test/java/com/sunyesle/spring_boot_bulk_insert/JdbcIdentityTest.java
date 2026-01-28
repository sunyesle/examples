package com.sunyesle.spring_boot_bulk_insert;

import com.sunyesle.spring_boot_bulk_insert.dto.MemberRequest;
import com.sunyesle.spring_boot_bulk_insert.dto.TeamRequest;
import com.sunyesle.spring_boot_bulk_insert.support.DatabaseCleaner;
import com.sunyesle.spring_boot_bulk_insert.support.TestFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DatabaseCleaner.class)
class JdbcIdentityTest {

    @Autowired
    DatabaseCleaner databaseCleaner;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @AfterEach
    void afterEach() {
        databaseCleaner.truncate("team_identity", "member_identity");
    }

    @Test
    void single() {
        List<TeamRequest> teamRequests = TestFixture.createTeamRequest(100, 0);

        SqlParameterSource[] batchArgs = teamRequests.stream()
                .map(m -> new MapSqlParameterSource()
                        .addValue("name", m.name()))
                .toArray(SqlParameterSource[]::new);

        jdbcTemplate.batchUpdate("INSERT INTO team_identity (name) VALUES (:name)", batchArgs);
    }

    @Test
    void relation() {
        List<TeamRequest> teamRequests = TestFixture.createTeamRequest(100, 2);

        // Team 데이터 세팅
        SqlParameterSource[] teamBatchArgs = teamRequests.stream()
                .map(m -> new MapSqlParameterSource()
                        .addValue("name", m.name()))
                .toArray(SqlParameterSource[]::new);

        // AUTO_INCREMENT로 생성된 PK 값을 담기 위한 KeyHolder 준비
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // Team 데이터 저장
        jdbcTemplate.batchUpdate("INSERT INTO team_identity (name) VALUES (:name)", teamBatchArgs, keyHolder);

        // 생성된 PK 값을 가져옴
        List<Map<String, Object>> keyList = keyHolder.getKeyList();

        // Member 데이터 세팅
        List<MapSqlParameterSource> memberParamList = new ArrayList<>();
        for (int i = 0; i < teamRequests.size(); i++) {
            Number key = (Number) keyList.get(i).values().iterator().next();
            long teamId = key.longValue();

            TeamRequest team = teamRequests.get(i);
            for (MemberRequest member : team.members()) {
                memberParamList.add(new MapSqlParameterSource()
                        .addValue("name", member.name())
                        .addValue("age", member.age())
                        .addValue("teamId", teamId));
            }
        }

        // Member 데이터 저장
        jdbcTemplate.batchUpdate("INSERT INTO member_identity (name, age, team_id) VALUES (:name, :age, :teamId)",
                memberParamList.toArray(SqlParameterSource[]::new)
        );
    }
}
