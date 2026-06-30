create table audit_events (
    id uuid primary key,
    recorded_at timestamptz not null,
    actor text not null,
    action text not null,
    resource_type text,
    resource_id text,
    outcome text not null,
    reason text,
    metadata jsonb
);

create index idx_audit_events_recorded_at on audit_events (recorded_at desc);
create index idx_audit_events_actor on audit_events (actor);
create index idx_audit_events_action on audit_events (action);
create index idx_audit_events_outcome on audit_events (outcome);

