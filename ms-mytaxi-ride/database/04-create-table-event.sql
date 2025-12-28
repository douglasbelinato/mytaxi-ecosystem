CREATE TABLE event(
    id VARCHAR(100) NOT NULL PRIMARY KEY,
    aggregate_id VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
	payload TEXT NOT NULL,
    retry_count INTEGER DEFAULT 0,
    date_insert TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_processed TIMESTAMP
);

CREATE INDEX idx_event_status ON event(status);