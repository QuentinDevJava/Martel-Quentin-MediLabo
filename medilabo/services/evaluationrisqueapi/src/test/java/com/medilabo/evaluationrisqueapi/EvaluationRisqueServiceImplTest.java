package com.medilabo.evaluationrisqueapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medilabo.evaluationrisqueapi.dto.PatientDto;
import com.medilabo.evaluationrisqueapi.enums.DangerLevel;
import com.medilabo.evaluationrisqueapi.service.EvaluationRisqueServiceImpl;
import com.medilabo.evaluationrisqueapi.service.NoteApi;
import com.medilabo.evaluationrisqueapi.service.PatientApi;

@ExtendWith(MockitoExtension.class)
class EvaluationRisqueServiceImplTest {

	@Mock
	private NoteApi noteApi;

	@Mock
	private PatientApi patientApi;

	@InjectMocks
	private EvaluationRisqueServiceImpl evaluationService;

	private PatientDto createPatient(String genre, int age) {
		return new PatientDto("Test", "User", LocalDate.now().minusYears(age), genre);
	}

	@Test
	void generateDiabetesReport_AgeOver30_ZeroTriggersTest() {
		PatientDto patient = createPatient("M", 45);
		when(patientApi.getPatientDtoByPatientId(1)).thenReturn(patient);
		when(noteApi.getTriggerTermsByPatientId(1)).thenReturn(0);

		DangerLevel result = evaluationService.generateDiabetesReport(1);

		assertThat(result).isEqualTo(DangerLevel.NONE);
	}

	@Test
	void generateDiabetesReport_AgeOver30_ThreeTriggersTest() {
		PatientDto patient = createPatient("M", 50);

		when(patientApi.getPatientDtoByPatientId(1)).thenReturn(patient);
		when(noteApi.getTriggerTermsByPatientId(1)).thenReturn(3);

		DangerLevel result = evaluationService.generateDiabetesReport(1);

		assertThat(result).isEqualTo(DangerLevel.BORDERLINE);
	}

	@Test
	void generateDiabetesReport_AgeOver30_SixTriggersTest() {
		PatientDto patient = createPatient("F", 60);
		when(patientApi.getPatientDtoByPatientId(1)).thenReturn(patient);
		when(noteApi.getTriggerTermsByPatientId(1)).thenReturn(6);

		DangerLevel result = evaluationService.generateDiabetesReport(1);

		assertThat(result).isEqualTo(DangerLevel.IN_DANGER);
	}

	@Test
	void generateDiabetesReport_AgeOver30_EightTriggersTest() {
		PatientDto patient = createPatient("F", 62);
		when(patientApi.getPatientDtoByPatientId(1)).thenReturn(patient);
		when(noteApi.getTriggerTermsByPatientId(1)).thenReturn(8);

		DangerLevel result = evaluationService.generateDiabetesReport(1);

		assertThat(result).isEqualTo(DangerLevel.EARLY_ONSET);
	}

	@Test
	void generateDiabetesReport_MaleAgeUnder30_FourTriggersTest() {
		PatientDto patient = createPatient("M", 28);
		when(patientApi.getPatientDtoByPatientId(1)).thenReturn(patient);
		when(noteApi.getTriggerTermsByPatientId(1)).thenReturn(4);

		DangerLevel result = evaluationService.generateDiabetesReport(1);

		assertThat(result).isEqualTo(DangerLevel.IN_DANGER);
	}

	@Test
	void generateDiabetesReport_MaleAgeUnder30_SixTriggersTest() {
		PatientDto patient = createPatient("M", 25);
		when(patientApi.getPatientDtoByPatientId(1)).thenReturn(patient);
		when(noteApi.getTriggerTermsByPatientId(1)).thenReturn(6);

		DangerLevel result = evaluationService.generateDiabetesReport(1);

		assertThat(result).isEqualTo(DangerLevel.EARLY_ONSET);
	}

	@Test
	void generateDiabetesReport_FemaleAgeUnder30_FiveTriggersTest() {
		PatientDto patient = createPatient("F", 27);
		when(patientApi.getPatientDtoByPatientId(1)).thenReturn(patient);
		when(noteApi.getTriggerTermsByPatientId(1)).thenReturn(5);

		DangerLevel result = evaluationService.generateDiabetesReport(1);

		assertThat(result).isEqualTo(DangerLevel.IN_DANGER);
	}

	@Test
	void generateDiabetesReport_FemaleAgeUnder30_NineTriggersTest() {
		PatientDto patient = createPatient("F", 22);

		when(patientApi.getPatientDtoByPatientId(1)).thenReturn(patient);
		when(noteApi.getTriggerTermsByPatientId(1)).thenReturn(9);

		DangerLevel result = evaluationService.generateDiabetesReport(1);

		assertThat(result).isEqualTo(DangerLevel.EARLY_ONSET);
	}

	@Test
	void generateDiabetesReport_MaleAgeUnder30_TwoTriggersTest() {
		PatientDto patient = createPatient("M", 25);

		when(patientApi.getPatientDtoByPatientId(1)).thenReturn(patient);
		when(noteApi.getTriggerTermsByPatientId(1)).thenReturn(2);

		DangerLevel result = evaluationService.generateDiabetesReport(1);

		assertThat(result).isEqualTo(DangerLevel.BORDERLINE);
	}

	@Test
	void generateDiabetesReport_FemaleAgeUnder30_TwoTriggersTest() {
		PatientDto patient = createPatient("F", 30);
		when(patientApi.getPatientDtoByPatientId(1)).thenReturn(patient);
		when(noteApi.getTriggerTermsByPatientId(1)).thenReturn(2);

		DangerLevel result = evaluationService.generateDiabetesReport(1);

		assertThat(result).isEqualTo(DangerLevel.BORDERLINE);
	}
}
