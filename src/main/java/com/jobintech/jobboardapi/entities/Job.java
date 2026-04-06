package com.jobintech.jobboardapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String company;


    @Column(name = "job_title")
    private String jobTitle;


    @Column(name = "job_type")
    private String jobType;

    @Column(name = "experience_required")
    private String experienceRequired;

    @ElementCollection
    @CollectionTable(
        name = "job_skills_required",
        joinColumns = @JoinColumn(name = "job_id")
    )
    @Column(name = "skill")
    private Set<String> skillsRequired = new LinkedHashSet<>();

    @Column(nullable = false, length = 100)
    private String salary;


    @Column(nullable = false)
    private String location;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Long applicants = 0L;

    @Lob
    @Column(nullable = false)
    private String description;

    @Column(name = "posted_on", nullable = false)
    private LocalDate postedOn;

    @Lob
    @Column(name = "responsibilities")
    private String responsibilities;

    //LAZY + cascade suppression gérée par DB
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User postedBy;
}
