package org.informatics.data;

import org.informatics.exceptions.InvalidParticipantException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ParticipantTest {

    @Test
    void whenParticipantNameIsEmpty_thenInvalidParticipantExceptionIsThrown() {
        // Given
        int id = 1;
        String name = "";
        String country = "Bulgaria";
        Gender gender = Gender.Male;
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);

        // When Then
        assertThrows(InvalidParticipantException.class, () -> {new Participant(id, name, country, gender, dateOfBirth);});
    }

    @Test
    void whenParticipantDateOfBirthIsInFuture_thenInvalidParticipantExceptionIsThrown() {
        // Given
        int id = 1;
        String name = "Anton";
        String country = "Bulgaria";
        Gender gender = Gender.Male;
        LocalDate dateOfBirth = LocalDate.now().plusYears(1);

        // When Then
        assertThrows(InvalidParticipantException.class, () -> {new Participant(id, name, country, gender, dateOfBirth);});
    }

    @Test
    void whenParticipantIdIsNegative_thenInvalidParticipantExceptionIsThrown() {
        // Given
        int id = -1;
        String name = "Anton";
        String country = "Bulgaria";
        Gender gender = Gender.Male;
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);

        // When Then
        assertThrows(InvalidParticipantException.class, () -> {new Participant(id, name, country, gender, dateOfBirth);});
    }
}