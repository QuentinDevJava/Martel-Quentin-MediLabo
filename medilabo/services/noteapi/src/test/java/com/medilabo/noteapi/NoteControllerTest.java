package com.medilabo.noteapi;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.medilabo.noteapi.dto.NoteDto;
import com.medilabo.noteapi.security.client.AuthClient;
import com.medilabo.noteapi.service.NoteService;

import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NoteService noteService;

    @MockitoBean
    private AuthClient authClient;

    @Autowired
    private ObjectMapper objectMapper;

    private NoteDto noteDto;

    @BeforeEach
    void setup() {
	noteDto = new NoteDto();
	noteDto.setId("noteTest");
	noteDto.setPatientId(1);
	noteDto.setContenuNote("Patient is in good health.");
    }

    @Test
    void getNoteById() throws Exception {
	when(authClient.validateToken(anyString())).thenReturn(Mono.just(true));

	when(noteService.getNotesById("noteTest")).thenReturn(noteDto);

	mockMvc.perform(get("/api/notes/noteTest").header("Authorization", "Bearer test-token"))
		.andExpect(status().isOk()).andExpect(jsonPath("$.id").value("noteTest"))
		.andExpect(jsonPath("$.patientId").value(1))
		.andExpect(jsonPath("$.contenuNote").value("Patient is in good health."));
    }

    @Test
    void getNoteByPatientId() throws Exception {
	when(authClient.validateToken(anyString())).thenReturn(Mono.just(true));

	when(noteService.getNotesByPatientId(1)).thenReturn(List.of(noteDto));

	mockMvc.perform(get("/api/notes/patient/1").header("Authorization", "Bearer test-token"))
		.andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void createNote() throws Exception {
	when(authClient.validateToken(anyString())).thenReturn(Mono.just(true));

	when(noteService.createNote(ArgumentMatchers.any(NoteDto.class))).thenReturn(noteDto);

	mockMvc.perform(post("/api/notes/").header("Authorization", "Bearer test-token")
		.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(noteDto)))
		.andExpect(status().isCreated()).andExpect(jsonPath("$.id").value("noteTest"))
		.andExpect(jsonPath("$.contenuNote").value("Patient is in good health."));
    }

    @Test
    void updateNote() throws Exception {
	when(authClient.validateToken(anyString())).thenReturn(Mono.just(true));

	when(noteService.updateNote(Mockito.eq("noteTest"), ArgumentMatchers.any(NoteDto.class))).thenReturn(noteDto);

	mockMvc.perform(put("/api/notes/noteTest").header("Authorization", "Bearer test-token")
		.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(noteDto)))
		.andExpect(status().isOk()).andExpect(jsonPath("$.id").value("noteTest"))
		.andExpect(jsonPath("$.contenuNote").value("Patient is in good health."));
    }

    @Test
    void getTriggerTermCountForPatient() throws Exception {
	when(authClient.validateToken(anyString())).thenReturn(Mono.just(true));

	when(noteService.getNumberOfTermsByPatient(1)).thenReturn(3);

	mockMvc.perform(get("/api/notes/termesAnalyse/1").header("Authorization", "Bearer test-token"))
		.andExpect(status().isOk()).andExpect(content().string("3"));
    }
}
