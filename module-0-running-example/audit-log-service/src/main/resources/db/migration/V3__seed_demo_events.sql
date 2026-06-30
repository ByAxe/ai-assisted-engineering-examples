insert into audit_events
    (id, recorded_at, actor, action, resource_type, resource_id, outcome, reason, metadata)
values
    ('00000000-0000-0000-0000-000000000001', '2026-04-20T09:00:00Z', 'alice@example.com', 'LOGIN', 'session', 's-1001', 'ALLOWED', 'interactive login', '{"ip":"10.0.0.1"}'),
    ('00000000-0000-0000-0000-000000000002', '2026-04-20T09:05:00Z', 'bob@example.com', 'ACCESS_PII', 'customer', 'c-2001', 'DENIED', 'missing approval', '{"ticket":"INC-101"}'),
    ('00000000-0000-0000-0000-000000000003', '2026-04-20T09:10:00Z', 'carol@example.com', 'EXPORT_DATA', 'report', 'r-3001', 'ALLOWED', 'quarterly export', '{"format":"csv"}'),
    ('00000000-0000-0000-0000-000000000004', '2026-04-20T09:15:00Z', 'alice@example.com', 'ACCESS_PII', 'customer', 'c-2002', 'ALLOWED', 'support case', '{"ticket":"SUP-44"}'),
    ('00000000-0000-0000-0000-000000000005', '2026-04-20T09:20:00Z', 'dave@example.com', 'DELETE_RECORD', 'customer', 'c-2003', 'DENIED', 'destructive action blocked', '{"policy":"retention"}'),
    ('00000000-0000-0000-0000-000000000006', '2026-04-20T09:25:00Z', 'erin@example.com', 'GRANT_ROLE', 'user', 'u-5001', 'ALLOWED', 'admin grant', '{"role":"auditor"}'),
    ('00000000-0000-0000-0000-000000000007', '2026-04-20T09:30:00Z', 'bob@example.com', 'EXPORT_DATA', 'report', 'r-3002', 'DENIED', 'export limit exceeded', '{"format":"json"}'),
    ('00000000-0000-0000-0000-000000000008', '2026-04-20T09:35:00Z', 'alice@example.com', 'LOGOUT', 'session', 's-1001', 'ALLOWED', 'session closed', '{"ip":"10.0.0.1"}'),
    ('00000000-0000-0000-0000-000000000009', '2026-04-20T09:40:00Z', 'frank@example.com', 'REVOKE_ROLE', 'user', 'u-5002', 'ALLOWED', 'role cleanup', '{"role":"admin"}'),
    ('00000000-0000-0000-0000-000000000010', '2026-04-20T09:45:00Z', 'carol@example.com', 'ACCESS_PII', 'customer', 'c-2004', 'DENIED', 'break-glass expired', '{"ticket":"SEC-9"}'),
    ('00000000-0000-0000-0000-000000000011', '2026-04-20T09:50:00Z', 'gina@example.com', 'LOGIN', 'session', 's-1002', 'DENIED', 'mfa failed', '{"ip":"10.0.0.2"}'),
    ('00000000-0000-0000-0000-000000000012', '2026-04-20T09:55:00Z', 'alice@example.com', 'EXPORT_DATA', 'report', 'r-3003', 'ALLOWED', 'case export', '{"format":"csv"}');

