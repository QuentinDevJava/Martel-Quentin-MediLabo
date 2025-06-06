package com.medilabo.patientapi.service;

import java.util.List;

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
	private final PatientMapper patientMapper;
	private final NoteApi noteApi;

	@Override
	public PatientDto getPatientById(int id) {
		Patient patient = patientRepository.findById(id);
		return patientMapper.toDto(patient);
	}

	@Override
	public PatientDto getPatientByName(String name) {
		Patient patient = patientRepository.findByNom(name);
		return patientMapper.toDto(patient);
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
	public PatientDto updatePatient(int id, PatientDto patientDto) {
		Patient patient = patientRepository.findById(id);

		patient.setNom(patientDto.getNom());
		patient.setPrenom(patientDto.getPrenom());
		patient.setDateAnniversaire(patientDto.getDateAnniversaire());
		patient.setGenre(patientDto.getGenre());
		patient.setAdresse(patientDto.getAdresse());
		patient.setTelephone(patientDto.getTelephone());

		return patientMapper.toDto(patientRepository.save(patient));
	}

	@Override
	public PatientDto getPatientWithNotesById(int id) {
		Patient patient = patientRepository.findById(id);
		List<NoteDto> noteDtos = noteApi.getNoteDtos(patient.getNom());
		return patientMapper.toDtoWithNotes(patient, noteDtos);
	}

	@Override
	public PatientDto getPatientWithNotesByName(String name) {
		Patient patient = patientRepository.findByNom(name);
		List<NoteDto> noteDtos = noteApi.getNoteDtos(name);
		return patientMapper.toDtoWithNotes(patient, noteDtos);
	}

}
