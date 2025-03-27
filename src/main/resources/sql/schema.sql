CREATE TABLE IF NOT EXISTS file_history
(
    id                SERIAL PRIMARY KEY,
    file_name         VARCHAR(255) NOT NULL,
    file_path         TEXT         NOT NULL,
    file_type         VARCHAR(50)  NOT NULL,
    file_size         BIGINT       NOT NULL,
    created_date_time TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_date_time TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS file_info
(
    id                 SERIAL PRIMARY KEY,
    file_original_name VARCHAR(255) NOT NULL,
    file_hash_name     VARCHAR(255) NOT NULL,
    file_full_path     VARCHAR(255) NOT NULL,
    file_size          BIGINT       NOT NULL,
    file_type          VARCHAR(50)  NOT NULL,
    created_date_time  TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_date_time  TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP NOT NULL
);