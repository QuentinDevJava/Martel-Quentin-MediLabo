package com.medilabo.patientapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.medilabo.patientapi.dto.NoteDto;
import com.medilabo.patientapi.dto.PatientDto;
import com.medilabo.patientapi.entities.Patient;
import com.medilabo.patientapi.mapper.PatientMapper;
import com.medilabo.patientapi.repository.PatientRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper = new PatientMapper();
    private final NoteApi noteApi;

    @Override
    public PatientDto getPatientById(int id) {
	return getPatientDtoWithPatientById(id);
    }

    @Override
    public List<PatientDto> getAllPatient() {
	return patientRepository.findAll().stream().map(patientMapper::toDto).toList();
    }

    @Override
    public PatientDto addPatient(PatientDto patientDto) {
	Patient patient = patientRepository.save(patientMapper.toEntity(patientDto));
	return patientMapper.toDto(patient);
    }

    @Override
    public PatientDto updatePatient(int id, PatientDto updatePatientDto) {

	PatientDto patientDto = getPatientDtoWithPatientById(id);

	if (patientDto == null) {
	    throw new IllegalArgumentException("The patient is not found");
	} else {
	    patientDto.setId(updatePatientDto.getId());
	    patientDto.setNom(updatePatientDto.getNom());
	    patientDto.setPrenom(updatePatientDto.getPrenom());
	    patientDto.setDateAnniversaire(updatePatientDto.getDateAnniversaire());
	    patientDto.setGenre(updatePatientDto.getGenre());
	    patientDto.setAdresse(updatePatientDto.getAdresse());
	    patientDto.setTelephone(updatePatientDto.getTelephone());

	    return patientMapper.toDto(patientRepository.save(patientMapper.toEntity(patientDto)));
	}
    }

    @Override
    public PatientDto getPatientWithNotesById(int id) {
	Patient patient = patientRepository.findById(id)
		.orElseThrow(() -> new IllegalArgumentException("The patient is not found"));
	List<NoteDto> noteDtos = noteApi.getNoteDtos(patient.getId());
	return patientMapper.toDtoWithNotes(patient, noteDtos);
    }

    @Override
    public List<PatientDto> getAllPatientsWithNotes() {
	return patientRepository.findAll().stream()
		.map(patient -> patientMapper.toDtoWithNotes(patient, noteApi.getNoteDtos(patient.getId()))).toList();

    }

    private PatientDto getPatientDtoWithPatientById(int patientId) {
	Optional<Patient> patient = patientRepository.findById(patientId);
	if (patient.isPresent()) {
	    return patientMapper.toDto(patient.get());
	} else {
	    return null;
	}

    }

}
