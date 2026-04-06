-- =============================================
-- V3: profile_skills
-- @ElementCollection de Profile.skills
-- =============================================
CREATE TABLE profile_skills (
    profile_id BIGINT       NOT NULL,
    skill      VARCHAR(255) NOT NULL,

    CONSTRAINT fk_profile_skills_profile FOREIGN KEY (profile_id)
        REFERENCES profiles (id)
        ON DELETE CASCADE
);

CREATE INDEX idx_profile_skills_profile_id ON profile_skills (profile_id);
