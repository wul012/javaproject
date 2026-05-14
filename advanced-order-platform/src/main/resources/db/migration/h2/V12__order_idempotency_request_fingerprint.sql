alter table orders
    add column idempotency_request_fingerprint varchar(80);
