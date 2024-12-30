package com.openclassrooms.p9.microservice_patient.controller;

import com.openclassrooms.p9.microservice_patient.model.Patient;
import com.openclassrooms.p9.microservice_patient.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class PatientController {

    @Autowired
    PatientService patientService;

    @GetMapping("/Patients")
    public List<Patient> listAll()
    {
        return patientService.getAll();
    }

    @GetMapping(value = "/Patients/{id}")
    public Patient showPatient(@PathVariable int id) {
        Optional<Patient> patientFound = patientService.getPatientById(id);
        if (patientFound.isPresent()) {
             return patientFound.get();
        }
        else{
            throw new PatientNotFoundException();
        }
    }

    @PutMapping(value = "/Patients/{id}")
    public ResponseEntity<Patient> updatePatient(@Valid @RequestBody Patient patient,@PathVariable int id) {
        Optional<Patient> patientFound = patientService.getPatientById(id);
        if (patientFound.isPresent()) {
            patient.setId(id);
            Patient patientUpdated = patientService.savePatient(patient);

            if (Objects.isNull(patientUpdated)) {
                return ResponseEntity.noContent().build();
            }

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(patientUpdated.getId())
                    .toUri();
            return ResponseEntity.ok(patientUpdated);
        }
        else{
            throw new PatientNotFoundException();
        }
    }

    @PostMapping(value = "/Patients")
    public ResponseEntity<Patient> addPatient(@Valid @RequestBody Patient patient) {
        Patient patientAdded = patientService.savePatient(patient);

        if (Objects.isNull(patientAdded)) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(patientAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }


    @DeleteMapping ("/Patients/{id}")
    public void deletePatient(@PathVariable("id") Integer id) {
        Optional<Patient> patientFound = patientService.getPatientById(id);
        if (patientFound.isPresent()) {
            patientService.deletePatientById(id);
        }
        else {
            throw new PatientNotFoundException();
        }
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class PatientNotFoundException extends RuntimeException {
        public PatientNotFoundException() {
            super("Patient not found");
        }
    }
}
