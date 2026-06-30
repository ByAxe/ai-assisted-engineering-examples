package com.example.auditlog.query;

import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.auditlog.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class QueryCharacterizationIT {

  @Autowired private MockMvc mockMvc;

  @Test
  void baselineNoFilterResultIsStableOnSeededRows() throws Exception {
    mockMvc
        .perform(get("/api/v1/audit-events").param("page", "0").param("size", "5"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.page").value(0))
        .andExpect(jsonPath("$.size").value(5))
        .andExpect(jsonPath("$.totalElements").value(12))
        .andExpect(
            jsonPath(
                    "$.content[*].id",
                    contains(
                        "00000000-0000-0000-0000-000000000012",
                        "00000000-0000-0000-0000-000000000011",
                        "00000000-0000-0000-0000-000000000010",
                        "00000000-0000-0000-0000-000000000009",
                        "00000000-0000-0000-0000-000000000008")));
  }

  @Test
  void baselineActorFilterIsStableOnSeededRows() throws Exception {
    mockMvc
        .perform(get("/api/v1/audit-events").param("actor", "bob@example.com"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalElements").value(2))
        .andExpect(
            jsonPath(
                "$.content[*].id",
                contains(
                    "00000000-0000-0000-0000-000000000007",
                    "00000000-0000-0000-0000-000000000002")));
  }

  @Test
  void baselineTimeWindowIsStableOnSeededRows() throws Exception {
    mockMvc
        .perform(
            get("/api/v1/audit-events")
                .param("from", "2026-04-20T09:25:00Z")
                .param("to", "2026-04-20T09:50:00Z"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalElements").value(5))
        .andExpect(
            jsonPath(
                "$.content[*].id",
                contains(
                    "00000000-0000-0000-0000-000000000010",
                    "00000000-0000-0000-0000-000000000009",
                    "00000000-0000-0000-0000-000000000008",
                    "00000000-0000-0000-0000-000000000007",
                    "00000000-0000-0000-0000-000000000006")));
  }
}
