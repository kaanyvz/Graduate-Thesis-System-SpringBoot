package com.ky.dbmanagementsystem.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Data
@Table(name = "subjectTopics")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    @NotNull
    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "subjectTopics", fetch = FetchType.EAGER)
    private List<Thesis> theses;

}
