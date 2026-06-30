package com.sam.audit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuditEventEndpointIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private JdbcTemplate jdbcTemplate;

  @Test
  void createsAuditEventAndReturnsLocation() throws Exception {
    mockMvc
        .perform(
            post("/audit-events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
					{
					  "actor": "trainer@session-1",
					  "action": "USER_LOGIN",
					  "targetType": "user-account",
					  "targetId": "user-42",
					  "occurredAt": "2026-04-20T12:00:00Z"
					}
					"""))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", matchesPattern(".*/audit-events/[0-9a-f\\-]+$")));

    assertThat(jdbcTemplate.queryForObject("select count(*) from audit_events", Integer.class))
        .isEqualTo(1);
  }

  @Test
  void rejectsInvalidPayload() throws Exception {
    mockMvc
        .perform(
            post("/audit-events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
					{
					  "actor": "",
					  "action": "USER_LOGIN",
					  "targetType": "user-account",
					  "targetId": "user-42"
					}
					"""))
        .andExpect(status().isBadRequest());

    assertThat(jdbcTemplate.queryForObject("select count(*) from audit_events", Integer.class))
        .isZero();
  }
}
