package com.jobintech.jobboardapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @Column(name = "job_title")
    private String jobTitle;


    @Size(max = 255)
    @Column(name = "location")
    private String location;

    @Lob
    @Column(name = "about")
    private String about;

    @ElementCollection
    @CollectionTable(
            name = "profile_skills",
            joinColumns = @JoinColumn(name = "profile_id")
    )
    @Column(name = "skill")
    private List<String> skills = new ArrayList<>();


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // Bidirectionnel — Profile owner side
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("startDate DESC")
    private List<Experience> experiences = new ArrayList<>();
}
