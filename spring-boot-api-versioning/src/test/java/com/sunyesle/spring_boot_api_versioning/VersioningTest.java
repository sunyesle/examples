package com.sunyesle.spring_boot_api_versioning;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("versioning")
class VersioningTest {

    @Autowired
    private MockMvc mockMvc;

    // V1 응답 검증
    private static final ResultMatcher[] V1_MATCHERS = new ResultMatcher[]{
            jsonPath("$[0].name").exists(),
            jsonPath("$[0].firstName").doesNotExist(),
            jsonPath("$[0].lastName").doesNotExist()
    };

    // V2 응답 검증
    private static final ResultMatcher[] V2_MATCHERS = new ResultMatcher[]{
            jsonPath("$[0].firstName").exists(),
            jsonPath("$[0].lastName").exists(),
            jsonPath("$[0].name").doesNotExist()
    };

    @Test
    void fallback_V1() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpectAll(V1_MATCHERS)
                .andDo(print());
    }

    @Test
    void header_V1() throws Exception {
        mockMvc.perform(get("/api/users")
                        .header("X-API-Version", "1.0"))
                .andExpect(status().isOk())
                .andExpectAll(V1_MATCHERS)
                .andDo(print());
    }

    @Test
    void header_V2() throws Exception {
        mockMvc.perform(get("/api/users")
                        .header("X-API-Version", "2.0"))
                .andExpect(status().isOk())
                .andExpectAll(V2_MATCHERS)
                .andDo(print());
    }

    @Test
    void query_parameter_V1() throws Exception {
        mockMvc.perform(get("/api/users")
                        .queryParam("version", "1.0"))
                .andExpect(status().isOk())
                .andExpectAll(V1_MATCHERS)
                .andDo(print());
    }

    @Test
    void query_parameter_V2() throws Exception {
        mockMvc.perform(get("/api/users")
                        .queryParam("version", "2.0"))
                .andExpect(status().isOk())
                .andExpectAll(V2_MATCHERS)
                .andDo(print());
    }

    @Test
    void media_type_parameter_V1() throws Exception {
        mockMvc.perform(get("/api/users")
                        .header("Accept", "application/json; version=1.0"))
                .andExpect(status().isOk())
                .andExpectAll(V1_MATCHERS)
                .andDo(print());
    }

    @Test
    void media_type_parameter_V2() throws Exception {
        mockMvc.perform(get("/api/users")
                        .header("Accept", "application/json; version=2.0"))
                .andExpect(status().isOk())
                .andExpectAll(V2_MATCHERS)
                .andDo(print());
    }
}
