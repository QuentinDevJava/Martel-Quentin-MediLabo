package com.medilabo.patientapi.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.patientapi.dto.PatientDto;
import com.medilabo.patientapi.service.PatientService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Contrôleur REST pour la gestion des patients. Fournit des endpoints pour
 * créer, mettre à jour, récupérer des patients, avec ou sans leurs notes
 * médicales.
 */
@Slf4j
@RestController
@RequestMapping("/api/patients/")
@AllArgsConstructor
public class PatientController {

    private PatientService patientService;

    /**
     * Crée un nouveau patient.
     *
     * @param patientDto les données du patient à créer
     * @return le patient créé avec le statut HTTP 201
     */
    @PostMapping
    public ResponseEntity<PatientDto> addPatient(@Valid @RequestBody PatientDto patientDto) {
	PatientDto savedPatient = patientService.addPatient(patientDto);
	log.info("Receive POST /api/patients: PatientDto " + patientDto
		+ " - PatientApi use RestController to create Patient");
	return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    /**
     * Met à jour les informations d’un patient existant.
     *
     * @param id         l'identifiant du patient à mettre à jour
     * @param patientDto les nouvelles données du patient
     * @return le patient mis à jour avec le statut HTTP 200
     */
    @PutMapping("{id}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable int id, @Valid @RequestBody PatientDto patientDto) {
	PatientDto updatedPatient = patientService.updatePatient(id, patientDto);
	log.info("Receive PUT /api/patients/" + id + ": PatientDto " + patientDto
		+ " - PatientApi use RestController to update Patient");
	return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }

    /**
     * Récupère un patient par son identifiant.
     *
     * @param id l'identifiant du patient
     * @return les données du patient avec le statut HTTP 200
     */
    @GetMapping("{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable int id) {
	log.info("Receive GET /api/patients/" + id + ": PatientApi use RestController to send PatientDto by ID");
	try {
	    return new ResponseEntity<>(patientService.getPatientById(id), HttpStatus.OK);
	} catch (EntityNotFoundException e) {
	    log.info("Patient not found ", e);
	    return ResponseEntity.notFound().build();
	} catch (Exception e) {
	    log.error("Error while getting patient by id", e);
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
    }

    /**
     * Récupère la liste de tous les patients.
     *
     * @return la liste des patients avec le statut HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients() {
	log.info("Receive GET /api/patients: PatientApi use RestController to send list of PatientDto");
	return new ResponseEntity<>(patientService.getAllPatient(), HttpStatus.OK);
    }

    /**
     * Récupère un patient et ses notes médicales par son identifiant.
     *
     * @param id l'identifiant du patient
     * @return les données du patient avec ses notes avec le statut HTTP 200
     */
    @GetMapping("notes/{id}")
    public ResponseEntity<PatientDto> getPatientWithNotesById(@PathVariable int id) {
	log.info("Receive GET /api/patients/" + id + ": PatientApi use RestController to send PatientDto by ID");
	try {
	    return new ResponseEntity<>(patientService.getPatientWithNotesById(id), HttpStatus.OK);
	} catch (EntityNotFoundException e) {
	    log.info("Patient not found ", e);
	    return ResponseEntity.notFound().build();
	} catch (Exception e) {
	    log.error("Error ", e);
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
    }

    /**
     * Récupère tous les patients avec leurs notes médicales.
     *
     * @return la liste des patients avec leurs notes avec le statut HTTP 200
     */
    @GetMapping("notes")
    public ResponseEntity<List<PatientDto>> getAllPatientWithNotesById() {
	log.info("Receive GET /api/patients/notes - PatientApi use RestController to send all Patients with notes");
	return new ResponseEntity<>(patientService.getAllPatientsWithNotes(), HttpStatus.OK);
    }

}
