package com.example.auditlog.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.auditlog.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AppendAuditEventIT {

  @Autowired private MockMvc mockMvc;

  @Autowired private JdbcTemplate jdbcTemplate;

  @Test
  void postCreatesEventWithServerFields() throws Exception {
    var result =
        mockMvc
            .perform(
                post("/api/v1/audit-events")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                        {
                          "actor": "admin@example.com",
                          "action": "LOGIN",
                          "resourceType": "session",
                          "resourceId": "s-demo",
                          "outcome": "ALLOWED",
                          "reason": "demo login",
                          "metadata": {"ip": "10.1.1.5"}
                        }
                        """))
            .andExpect(status().isCreated())
            .andExpect(
                header().string("Location", matchesPattern(".*/api/v1/audit-events/[0-9a-f\\-]+$")))
            .andExpect(jsonPath("$.id").isNotEmpty())
            .andExpect(jsonPath("$.recordedAt").isNotEmpty())
            .andExpect(jsonPath("$.actor").value("admin@example.com"))
            .andExpect(jsonPath("$.action").value("LOGIN"))
            .andExpect(jsonPath("$.outcome").value("ALLOWED"))
            .andReturn();

    var id = JsonTestSupport.readString(result, "$.id");
    assertThat(
            jdbcTemplate.queryForObject(
                "select count(*) from audit_events where id = ?::uuid", Integer.class, id))
        .isOne();
  }

  @Test
  void missingActorReturnsBadRequest() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/audit-events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "actor": "",
                      "action": "LOGIN",
                      "outcome": "ALLOWED"
                    }
                    """))
        .andExpect(status().isBadRequest());
  }

  @Test
  void missingOutcomeReturnsBadRequest() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/audit-events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "actor": "admin@example.com",
                      "action": "LOGIN"
                    }
                    """))
        .andExpect(status().isBadRequest());
  }

  @Test
  void databaseRejectsUpdateAndDelete() {
    var id = "00000000-0000-0000-0000-000000000001";

    assertThatThrownBy(
            () ->
                jdbcTemplate.update(
                    "update audit_events set actor = ? where id = ?::uuid",
                    "mutated@example.com",
                    id))
        .isInstanceOf(DataAccessException.class);

    assertThatThrownBy(
            () -> jdbcTemplate.update("delete from audit_events where id = ?::uuid", id))
        .isInstanceOf(DataAccessException.class);
  }
}

