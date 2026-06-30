package com.example.auditlog.api;

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
class QueryAuditEventIT {

  @Autowired private MockMvc mockMvc;

  @Test
  void noFilterReturnsSeededRowsRecordedAtDescendingAndPaginated() throws Exception {
    mockMvc
        .perform(get("/api/v1/audit-events").param("page", "0").param("size", "3"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.page").value(0))
        .andExpect(jsonPath("$.size").value(3))
        .andExpect(jsonPath("$.totalElements").value(12))
        .andExpect(jsonPath("$.content[0].id").value("00000000-0000-0000-0000-000000000012"))
        .andExpect(jsonPath("$.content[0].recordedAt").value("2026-04-20T09:55:00Z"))
        .andExpect(jsonPath("$.content[1].id").value("00000000-0000-0000-0000-000000000011"))
        .andExpect(jsonPath("$.content[2].id").value("00000000-0000-0000-0000-000000000010"));
  }

  @Test
  void actorFilterReturnsExactActorMatches() throws Exception {
    mockMvc
        .perform(get("/api/v1/audit-events").param("actor", "alice@example.com"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalElements").value(4))
        .andExpect(jsonPath("$.content[0].id").value("00000000-0000-0000-0000-000000000012"))
        .andExpect(jsonPath("$.content[1].id").value("00000000-0000-0000-0000-000000000008"))
        .andExpect(jsonPath("$.content[2].id").value("00000000-0000-0000-0000-000000000004"))
        .andExpect(jsonPath("$.content[3].id").value("00000000-0000-0000-0000-000000000001"));
  }

  @Test
  void fromAndToFilterRecordedAtWindow() throws Exception {
    mockMvc
        .perform(
            get("/api/v1/audit-events")
                .param("from", "2026-04-20T09:20:00Z")
                .param("to", "2026-04-20T09:40:00Z"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalElements").value(4))
        .andExpect(jsonPath("$.content[0].id").value("00000000-0000-0000-0000-000000000008"))
        .andExpect(jsonPath("$.content[1].id").value("00000000-0000-0000-0000-000000000007"))
        .andExpect(jsonPath("$.content[2].id").value("00000000-0000-0000-0000-000000000006"))
        .andExpect(jsonPath("$.content[3].id").value("00000000-0000-0000-0000-000000000005"));
  }
}

