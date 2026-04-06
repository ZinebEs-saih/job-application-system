-- =============================================
-- V7: applicants
-- lié à users ET jobs
-- un user ne peut postuler qu'une fois par job
-- =============================================
CREATE TABLE applicants (
                            id           BIGINT       NOT NULL AUTO_INCREMENT,
                            user_id      BIGINT       NOT NULL,          -- QUI a postulé
                            job_id       BIGINT       NOT NULL,          -- À QUEL job
                            resume       VARCHAR(500) NOT NULL,
                            portfolio    VARCHAR(500) NOT NULL,
                            linked_in    VARCHAR(500),
                            cover_letter LONGTEXT,
                            status       VARCHAR(50)  NOT NULL DEFAULT 'PENDING',
                            applied_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,

                            CONSTRAINT pk_applicants          PRIMARY KEY (id),

    -- impossible de postuler 2 fois au même job
                            CONSTRAINT uq_applicant_user_job  UNIQUE (user_id, job_id),

                            CONSTRAINT fk_applicants_user     FOREIGN KEY (user_id)
                                REFERENCES users (id)
                                ON DELETE CASCADE,

                            CONSTRAINT fk_applicants_job      FOREIGN KEY (job_id)
                                REFERENCES jobs (id)
                                ON DELETE CASCADE
);

CREATE INDEX idx_applicants_user_id ON applicants (user_id);
CREATE INDEX idx_applicants_job_id  ON applicants (job_id);
CREATE INDEX idx_applicants_status  ON applicants (status);