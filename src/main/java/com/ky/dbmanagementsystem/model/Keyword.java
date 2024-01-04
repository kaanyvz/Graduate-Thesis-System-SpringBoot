package com.ky.dbmanagementsystem.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "keywords")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    private String name;

    @ManyToMany(mappedBy = "keywords", fetch = FetchType.EAGER)
    private List<Thesis> theses;

    public Keyword(String name) {
        this.name = name;
    }

}
