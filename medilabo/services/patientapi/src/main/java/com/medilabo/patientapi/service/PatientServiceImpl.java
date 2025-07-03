package com.medilabo.patientapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medilabo.patientapi.dto.NoteDto;
import com.medilabo.patientapi.dto.PatientDto;
import com.medilabo.patientapi.entities.Patient;
import com.medilabo.patientapi.mapper.PatientMapper;
import com.medilabo.patientapi.repository.PatientRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service de gestion des patients. Fournit les opérations CRUD sur les
 * patients, avec ou sans récupération des notes associées.
 */
@Slf4j
@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper = new PatientMapper();
    private final NoteApi noteApi;

    /**
     * Récupère un patient par son identifiant.
     *
     * @param id identifiant du patient
     * @return PatientDto correspondant
     */
    @Override
    public PatientDto getPatientById(int id) {
	return getPatientDtoWithPatientById(id);
    }

    /**
     * Récupère tous les patients.
     *
     * @return liste des PatientDto
     */
    @Override
    public List<PatientDto> getAllPatient() {
	return patientRepository.findAll().stream().map(patientMapper::toDto).toList();
    }

    /**
     * Ajoute un nouveau patient.
     *
     * @param patientDto données du patient à ajouter
     * @return PatientDto créé
     */
    @Override
    public PatientDto addPatient(PatientDto patientDto) {
	log.info("addPatient receive : {}", patientDto.getDateAnniversaire());

	Patient patient = patientRepository.save(patientMapper.toEntity(patientDto));

	return patientMapper.toDto(patient);
    }

    /**
     * Met à jour un patient existant.
     *
     * @param id               identifiant du patient à mettre à jour
     * @param updatePatientDto nouvelles données du patient
     * @return PatientDto mis à jour
     * @throws EntityNotFoundException si le patient n'existe pas
     */
    @Override
    public PatientDto updatePatient(int id, PatientDto updatePatientDto) {
	PatientDto patientDto = getPatientDtoWithPatientById(id);

	if (patientDto == null) {
	    throw new EntityNotFoundException("The patient is not found");
	}

	patientDto.setId(updatePatientDto.getId());
	patientDto.setNom(updatePatientDto.getNom());
	patientDto.setPrenom(updatePatientDto.getPrenom());
	patientDto.setDateAnniversaire(updatePatientDto.getDateAnniversaire());
	patientDto.setGenre(updatePatientDto.getGenre());
	patientDto.setAdresse(updatePatientDto.getAdresse());
	patientDto.setTelephone(updatePatientDto.getTelephone());

	return patientMapper.toDto(patientRepository.save(patientMapper.toEntity(patientDto)));
    }

    /**
     * Récupère un patient avec ses notes médicales par son identifiant.
     *
     * @param id identifiant du patient
     * @return PatientDto avec les notes
     * @throws EntityNotFoundException si le patient n'existe pas
     */
    @Override
    public PatientDto getPatientWithNotesById(int id) {
	Patient patient = patientRepository.findById(id)
		.orElseThrow(() -> new EntityNotFoundException("The patient is not found"));

	List<NoteDto> noteDtos = noteApi.getNoteDtos(patient.getId());

	return patientMapper.toDtoWithNotes(patient, noteDtos);
    }

    /**
     * Récupère tous les patients avec leurs notes médicales.
     *
     * @return liste des PatientDto avec les notes
     */
    @Override
    public List<PatientDto> getAllPatientsWithNotes() {
	return patientRepository.findAll().stream()
		.map(patient -> patientMapper.toDtoWithNotes(patient, noteApi.getNoteDtos(patient.getId()))).toList();
    }

    private PatientDto getPatientDtoWithPatientById(int patientId) {
	return patientRepository.findById(patientId).map(patientMapper::toDto)
		.orElseThrow(() -> new EntityNotFoundException("No patient found for id " + patientId));
    }
}