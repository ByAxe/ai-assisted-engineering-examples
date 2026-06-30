package com.example.auditlog.api;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.auditlog.TestcontainersConfiguration;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class QueryAuditEventFilterIT {

  @Autowired private MockMvc mockMvc;

  @Test
  void outcomeFilterReturnsOnlyMatchingRows() throws Exception {
    mockMvc
        .perform(get("/api/v1/audit-events").param("outcome", "DENIED"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalElements").value(5))
        .andExpect(jsonPath("$.content[*].outcome", everyItem(is("DENIED"))))
        .andExpect(
            jsonPath(
                "$.content[*].id",
                contains(
                    "00000000-0000-0000-0000-000000000011",
                    "00000000-0000-0000-0000-000000000010",
                    "00000000-0000-0000-0000-000000000007",
                    "00000000-0000-0000-0000-000000000005",
                    "00000000-0000-0000-0000-000000000002")));
  }

  @Test
  void actionFilterReturnsOnlyMatchingRows() throws Exception {
    mockMvc
        .perform(get("/api/v1/audit-events").param("action", "EXPORT_DATA"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalElements").value(3))
        .andExpect(jsonPath("$.content[*].action", everyItem(is("EXPORT_DATA"))))
        .andExpect(
            jsonPath(
                "$.content[*].id",
                contains(
                    "00000000-0000-0000-0000-000000000012",
                    "00000000-0000-0000-0000-000000000007",
                    "00000000-0000-0000-0000-000000000003")));
  }

  @Test
  void outcomeAndActionFiltersAreCombinedWithAnd() throws Exception {
    mockMvc
        .perform(
            get("/api/v1/audit-events")
                .param("outcome", "DENIED")
                .param("action", "EXPORT_DATA"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalElements").value(1))
        .andExpect(
            jsonPath("$.content[*].id", contains("00000000-0000-0000-0000-000000000007")));
  }

  @Test
  void invalidOutcomeReturnsBadRequestWithClearMessage() throws Exception {
    mockMvc
        .perform(get("/api/v1/audit-events").param("outcome", "MAYBE"))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath(
                "$.message",
                stringContainsInOrder(List.of("Invalid value 'MAYBE'", "parameter 'outcome'"))));
  }

  @Test
  void invalidActionReturnsBadRequestWithClearMessage() throws Exception {
    mockMvc
        .perform(get("/api/v1/audit-events").param("action", "DOWNLOAD"))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath(
                "$.message",
                stringContainsInOrder(List.of("Invalid value 'DOWNLOAD'", "parameter 'action'"))));
  }
}

