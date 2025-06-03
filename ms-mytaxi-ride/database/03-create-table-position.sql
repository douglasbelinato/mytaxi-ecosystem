CREATE TABLE position(
    id VARCHAR(100) NOT NULL PRIMARY KEY,
    ride_id VARCHAR(100) NOT NULL,
	latitude double precision,
	longitude double precision,
    date_insert TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ride_id) REFERENCES ride(id)
);