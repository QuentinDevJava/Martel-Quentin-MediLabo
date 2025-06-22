package com.medilabo.patientapi;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.patientapi.dto.NoteDto;
import com.medilabo.patientapi.dto.PatientDto;
import com.medilabo.patientapi.service.PatientService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

    private PatientDto patientDto;

    @BeforeEach
    void setup() {
	patientDto = new PatientDto();
	patientDto.setId(1);
	patientDto.setNom("Test");
	patientDto.setPrenom("Jean");
	patientDto.setDateAnniversaire(LocalDate.of(1990, 1, 1));
	patientDto.setGenre("M");
	patientDto.setAdresse("123 rue Test");
	patientDto.setTelephone("0000000000");
    }

    @Test
    void getPatientById() throws Exception {
	when(patientService.getPatientById(1)).thenReturn(patientDto);

	mockMvc.perform(get("/api/patients/1")).andExpect(status().isOk()).andExpect(jsonPath("$.nom").value("Test"))
		.andExpect(jsonPath("$.prenom").value("Jean"));
    }

    @Test
    void getAllPatients() throws Exception {
	when(patientService.getAllPatient()).thenReturn(List.of(patientDto));

	mockMvc.perform(get("/api/patients/")).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void addPatient() throws Exception {
	when(patientService.addPatient(ArgumentMatchers.any(PatientDto.class))).thenReturn(patientDto);

	mockMvc.perform(post("/api/patients/").contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(patientDto))).andExpect(status().isCreated())
		.andExpect(jsonPath("$.nom").value("Test"));
    }

    @Test
    void updatePatient() throws Exception {
	when(patientService.updatePatient(Mockito.eq(1), ArgumentMatchers.any(PatientDto.class)))
		.thenReturn(patientDto);

	mockMvc.perform(put("/api/patients/1").contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(patientDto))).andExpect(status().isOk())
		.andExpect(jsonPath("$.nom").value("Test"));
    }

    @Test
    void getPatientWithNotesById() throws Exception {
	patientDto.setNotes(List.of(new NoteDto("note-1", 1, "Jean", "test note")));

	when(patientService.getPatientWithNotesById(1)).thenReturn(patientDto);

	mockMvc.perform(get("/api/patients/notes/1")).andExpect(status().isOk())
		.andExpect(jsonPath("$.nom").value("Test")).andExpect(jsonPath("$.notes.length()").value(1));
    }

    @Test
    void getAllPatientWithNotesById() throws Exception {
	patientDto.setNotes(List.of(new NoteDto("note-1", 1, "Jean", "test note")));

	when(patientService.getAllPatientsWithNotes()).thenReturn(List.of(patientDto));

	mockMvc.perform(get("/api/patients/notes")).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(1))
		.andExpect(jsonPath("$[0].notes.length()").value(1));
    }
}