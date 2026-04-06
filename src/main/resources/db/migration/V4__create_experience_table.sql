-- =============================================
-- V4: experience
-- =============================================
CREATE TABLE experience (
    id          BIGINT NOT NULL AUTO_INCREMENT,
    title       VARCHAR(255) NOT NULL,
    company     VARCHAR(255) NOT NULL,
    location    VARCHAR(255),
    start_date  DATE         NOT NULL,
    end_date    DATE,
    description TEXT,
    profile_id  BIGINT       NOT NULL,

    CONSTRAINT pk_experience         PRIMARY KEY (id),
    CONSTRAINT fk_experience_profile FOREIGN KEY (profile_id)
        REFERENCES profiles (id)
        ON DELETE CASCADE
);

CREATE INDEX idx_experience_profile_id ON experience (profile_id);
