package com.openclassrooms.p9.microservice_patient.service;

import com.openclassrooms.p9.microservice_patient.model.Patient;
import com.openclassrooms.p9.microservice_patient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Integer Id) {
        return patientRepository.findById(Id);
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public void deletePatientById(Integer id) {
        patientRepository.deleteById(id);
    }
}
