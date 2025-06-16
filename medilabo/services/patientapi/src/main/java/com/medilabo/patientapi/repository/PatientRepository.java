package com.medilabo.patientapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medilabo.patientapi.entities.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

	Optional<Patient> findById(int id);

	Optional<Patient> findByNom(String nom);

	Optional<Patient> findByNomAndPrenom(String nom, String prenom);

}