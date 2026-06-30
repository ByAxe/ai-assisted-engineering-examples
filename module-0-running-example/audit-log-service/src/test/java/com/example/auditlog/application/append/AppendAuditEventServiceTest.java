package com.example.auditlog.application.append;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.auditlog.domain.Action;
import com.example.auditlog.domain.AuditEvent;
import com.example.auditlog.domain.AuditEventAppendRepository;
import com.example.auditlog.domain.Outcome;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Map;
import org.junit.jupiter.api.Test;

class AppendAuditEventServiceTest {

  private final RecordingAppendRepository repository = new RecordingAppendRepository();
  private final AppendAuditEventService service =
      new AppendAuditEventService(
          repository, Clock.fixed(Instant.parse("2026-04-20T12:00:00Z"), ZoneOffset.UTC));

  @Test
  void appendsEventWithServerGeneratedIdAndTimestamp() {
    var event =
        service.append(
            new AppendAuditEventCommand(
                "alice@example.com",
                Action.LOGIN,
                "session",
                "s-1",
                Outcome.ALLOWED,
                "interactive login",
                Map.of("ip", "10.0.0.1")));

    assertThat(event.id()).isNotNull();
    assertThat(event.recordedAt()).isEqualTo(Instant.parse("2026-04-20T12:00:00Z"));
    assertThat(event.actor()).isEqualTo("alice@example.com");
    assertThat(event.outcome()).isEqualTo(Outcome.ALLOWED);
  }

  @Test
  void rejectsMissingActor() {
    assertThatThrownBy(
            () ->
                service.append(
                    new AppendAuditEventCommand(
                        "", Action.LOGIN, null, null, Outcome.ALLOWED, null, Map.of())))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("actor must not be blank");
  }

  @Test
  void rejectsMissingOutcome() {
    assertThatThrownBy(
            () ->
                service.append(
                    new AppendAuditEventCommand(
                        "alice@example.com", Action.LOGIN, null, null, null, null, Map.of())))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("outcome must not be null");
  }

  private static class RecordingAppendRepository implements AuditEventAppendRepository {
    @Override
    public AuditEvent append(AuditEvent auditEvent) {
      return auditEvent;
    }
  }
}

