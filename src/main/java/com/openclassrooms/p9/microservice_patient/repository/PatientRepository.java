package com.openclassrooms.p9.microservice_patient.repository;

import com.openclassrooms.p9.microservice_patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
