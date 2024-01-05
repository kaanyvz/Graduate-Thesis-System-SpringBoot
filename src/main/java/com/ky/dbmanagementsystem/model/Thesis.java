package com.ky.dbmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "theses")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Thesis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thesis_id")
    private int id;

    @NotNull
    private Integer thesisNo;

    @NotNull
    private String thesis_title;

    @NotNull
    @Lob
    @Column(length = 5000)
    private String thesis_abstract;

    @NotNull
    private LocalDateTime thesis_year;

    @NotNull
    private String thesis_type;

    @NotNull
    private Integer numberOfPages;

    @NotNull
    private LocalDateTime submissionDate;

    @ManyToOne()
    @JoinColumn(name = "cosupervisor_id")
    @NotNull
    private CoSupervisor coSupervisor;

    @ManyToOne()
    @JoinColumn(name = "language_id")
    @NotNull
    private Language language;

    @ManyToOne()
    @JoinColumn(name = "university_id")
    @NotNull
    private  University university;

    @ManyToOne()
    @JoinColumn(name = "institute_id")
    @NotNull
    private Institute institute;

    @ManyToOne()
    @JoinColumn(name = "author_id")
    @NotNull
    private Author author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "thesis_keywords",
            joinColumns = @JoinColumn(name = "thesis_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id"))
    @NotNull
    private List<Keyword> keywords;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "thesis_subject_topics",
            joinColumns = @JoinColumn(name = "thesis_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_topic_id"))
    @NotNull
    private List<SubjectTopic> subjectTopics;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "thesis_supervisors",
            joinColumns = @JoinColumn(name = "thesis_id"),
            inverseJoinColumns = @JoinColumn(name = "supervisor_id"))
    @NotNull
    private List<Supervisor> supervisors;

    @Override
    public String toString() {
        return "Thesis{" +
                "id='" + id + '\'' +
                ", thesisNo=" + thesisNo +
                ", thesis_title='" + thesis_title + '\'' +
                ", thesis_abstract='" + thesis_abstract + '\'' +
                ", thesis_year=" + thesis_year +
                ", thesis_type='" + thesis_type + '\'' +
                ", numberOfPages=" + numberOfPages +
                ", submissionDate=" + submissionDate +
                '}';
    }

}