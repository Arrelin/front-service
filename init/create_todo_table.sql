CREATE TABLE IF NOT EXISTS todo (
                                    id SERIAL PRIMARY KEY,
                                    task VARCHAR(255) NOT NULL,
    completed BOOLEAN NOT NULL DEFAULT FALSE
    );