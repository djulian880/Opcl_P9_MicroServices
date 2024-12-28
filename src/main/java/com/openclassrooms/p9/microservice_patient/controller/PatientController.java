package com.openclassrooms.p9.microservice_patient.controller;

import com.openclassrooms.p9.microservice_patient.model.Patient;
import com.openclassrooms.p9.microservice_patient.service.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @RequestMapping("/Patients/list")
    public List<Patient> listAll()
    {
        List<Patient> p=patientService.getAll();
        System.out.println(p.get(2).getPrenom());
        return patientService.getAll();
    }

    @GetMapping(value = "/Patients/{id}")
    public Optional<Patient> showPatient(@PathVariable int id) {
        return patientService.getPatientById(id);
    }


    @PostMapping(value = "/Patients")
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        Patient patientAdded = patientService.savePatient(patient);
        //System.out.println(patient.getId());
        if (Objects.isNull(patientAdded)) {
            return ResponseEntity.noContent().build();
        }
        //.buildAndExpand(patientAdded.getId())
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(2)
                .toUri();
        return ResponseEntity.created(location).build();
    }


    @GetMapping("/Patients/delete/{id}")
    public String deletePatient(@PathVariable("id") Integer id) {
        patientService.deletePatientById(id);
        return "redirect:/Patients/list";
    }
}
