-- =============================================
-- V5: jobs
-- experience_required + skills via join table
-- =============================================
CREATE TABLE jobs (
    id                  BIGINT       NOT NULL AUTO_INCREMENT,
    company             VARCHAR(255) NOT NULL,
    job_title           VARCHAR(255) NOT NULL,
    job_type            VARCHAR(100) NOT NULL,
    experience_required VARCHAR(255),
    salary              VARCHAR(100) NOT NULL,
    location            VARCHAR(255) NOT NULL,
    applicants          BIGINT       NOT NULL DEFAULT 0,
    description         LONGTEXT     NOT NULL,
    posted_on           DATE         NOT NULL,
    responsibilities    LONGTEXT,
    user_id             BIGINT       NOT NULL,

    CONSTRAINT pk_jobs      PRIMARY KEY (id),
    CONSTRAINT fk_jobs_user FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE
);

CREATE INDEX idx_jobs_user_id ON jobs (user_id);
