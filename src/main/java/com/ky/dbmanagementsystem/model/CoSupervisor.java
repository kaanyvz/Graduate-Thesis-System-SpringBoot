package com.ky.dbmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

@Entity
@Table(name = "cosupervisors")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoSupervisor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cosupervisor_id")
    private int id;


    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String lastname;


    @OneToMany(mappedBy = "coSupervisor", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Thesis> theses = new ArrayList<>();

    @Override
    public String toString() {
        return "CoSupervisor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
