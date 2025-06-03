CREATE TABLE ride(
    id VARCHAR(100) NOT NULL PRIMARY KEY,
    passenger_id VARCHAR(100) NOT NULL,
    driver_id VARCHAR(100),
    status TEXT NOT NULL,
    fare NUMERIC(10, 6),
    distance double precision,
	latitude_from double precision,
	longitude_from double precision,
	latitude_to double precision,
	longitude_to double precision,
    date_insert TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);