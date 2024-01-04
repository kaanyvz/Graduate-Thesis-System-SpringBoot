package com.ky.dbmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "supervisors")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supervisor  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String lastName;
//
//    @NotNull
//    @NotBlank
//    @Email
//    private String mail;

    @ManyToMany(mappedBy = "supervisors", fetch = FetchType.EAGER)
    private List<Thesis> theses = new ArrayList<>();

}
