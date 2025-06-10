package com.medilabo.noteapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.medilabo.noteapi.entities.Note;
import com.medilabo.noteapi.repository.NoteRepository;

@SpringBootApplication
public class NoteApiApplication implements CommandLineRunner {

	private final NoteRepository noteRepository;

	public NoteApiApplication(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(NoteApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (noteRepository.findAll().isEmpty()) {
			noteRepository.save(Note.builder()

					.patientId(1)

					.patientNom("TestNone")

					.contenuNote(
							"Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé")

					.build());

			noteRepository.save(Note.builder()

					.patientId(2)

					.patientNom("TestBorderline")

					.contenuNote(
							"Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement")

					.build());

			noteRepository.save(Note.builder()

					.patientId(2)

					.patientNom("TestBorderline")

					.contenuNote(
							"Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale")

					.build());

			noteRepository.save(Note.builder()

					.patientId(3)

					.patientNom("TestInDanger")

					.contenuNote("Le patient déclare qu'il fume depuis peu")

					.build());

			noteRepository.save(Note.builder()

					.patientId(3)

					.patientNom("TestInDanger")

					.contenuNote(
							"Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé")

					.build());

			noteRepository.save(Note.builder().patientId(4)

					.patientNom("TestEarlyOnset")

					.contenuNote(
							"Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments")

					.build());

			noteRepository.save(Note.builder()

					.patientId(4)

					.patientNom("TestEarlyOnset")

					.contenuNote("Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps")

					.build());

			noteRepository.save(Note.builder()

					.patientId(4)

					.patientNom("TestEarlyOnset")

					.contenuNote(
							"Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé")

					.build());

			noteRepository.save(Note.builder()

					.patientId(4)

					.patientNom("TestEarlyOnset")

					.contenuNote("Taille, Poids, Cholestérol, Vertige et Réaction")

					.build());
		}
	}
}
