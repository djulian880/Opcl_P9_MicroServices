package com.openclassrooms.p9.microservice_patient.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;


@Data
//@Getter
//@Setter
@DynamicUpdate
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Column
    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @Column
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @Column
    @NotBlank(message = "La date de naissance est obligatoire")
    private String dateDeNaissance;

    @Column
    @NotBlank(message = "Le genre est obligatoire")
    private String genre;

    @Column
    private String adressePostale;

    @Column
    private String NumeroDeTelephone;


}
