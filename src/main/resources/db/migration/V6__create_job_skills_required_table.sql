-- =============================================
-- V6: job_skills_required
-- @ElementCollection de Job.skillsRequired
-- =============================================
CREATE TABLE job_skills_required (
    job_id BIGINT       NOT NULL,
    skill  VARCHAR(255) NOT NULL,

    CONSTRAINT fk_job_skills_job FOREIGN KEY (job_id)
        REFERENCES jobs (id)
        ON DELETE CASCADE
);

CREATE INDEX idx_job_skills_job_id ON job_skills_required (job_id);
