alter table failed_event_messages
    add column replay_approval_status varchar(32) not null default 'NOT_REQUESTED';

alter table failed_event_messages
    add column replay_approval_reason varchar(500);

alter table failed_event_messages
    add column replay_approval_requested_by varchar(80);

alter table failed_event_messages
    add column replay_approval_requested_at timestamp(6) with time zone;

alter table failed_event_messages
    add column replay_approval_reviewed_by varchar(80);

alter table failed_event_messages
    add column replay_approval_reviewed_at timestamp(6) with time zone;

alter table failed_event_messages
    add column replay_approval_review_note varchar(500);

create index idx_failed_event_messages_replay_approval
    on failed_event_messages (replay_approval_status, replay_approval_requested_at);
