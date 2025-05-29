package com.medilabo.noteapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.medilabo.noteapi.entities.Note;

@EnableMongoRepositories(basePackages = "com.mediLabo.noteApi.repository")
@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

	Optional<Note> findById(String id);

	List<Note> findAllByFkPatientNom(String Patientnom);

}
