package com.jobintech.jobboardapi.entities;

import com.jobintech.jobboardapi.enums.ApplicationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "applicants",
        // Un user ne peut postuler qu'une seule fois par job
        uniqueConstraints = @UniqueConstraint(
                name = "uq_applicant_user_job",
                columnNames = {"user_id", "job_id"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Lié à User — on sait QUI a postulé
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Lié à Job — on sait À QUEL JOB
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    // Infos spécifiques à la candidature
    @NotBlank(message = "Resume cannot be empty")
    @Size(max = 500)
    @Column(nullable = false, length = 500)
    private String resume;           // lien CV

    @NotBlank(message = "Portfolio cannot be empty")
    @Size(max = 500)
    @Column(nullable = false, length = 500)
    private String portfolio;

    @Size(max = 500)
    @Column(name = "linked_in", length = 500)
    private String linkedIn;

    @Lob
    @Column(name = "cover_letter")
    private String coverLetter;

    //Statut de la candidature
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    // Date de candidature automatique
    @Column(name = "applied_at", updatable = false)
    private LocalDateTime appliedAt;

    @PrePersist
    protected void onCreate() {
        this.appliedAt = LocalDateTime.now();
    }
}