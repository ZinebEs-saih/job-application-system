-- =============================================
-- V2: profiles
-- =============================================
CREATE TABLE profiles (
    id        BIGINT NOT NULL AUTO_INCREMENT,
    job_title VARCHAR(255),
    location  VARCHAR(255),
    about     TEXT,
    user_id   BIGINT NOT NULL,

    CONSTRAINT pk_profiles      PRIMARY KEY (id),
    CONSTRAINT uq_profiles_user UNIQUE (user_id),
    CONSTRAINT fk_profiles_user FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE
);
