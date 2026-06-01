package org.informatics.service.impl;

import org.informatics.data.*;
import org.informatics.exceptions.InvalidBiatlonException;
import org.informatics.service.BiatlonService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BiatlonServiceImplTest {

    @Test
    void whenParticipantHasMissedShots_thenPenaltyIsCalculatedCorrectly() {
        // Given
        Participant participant = new Participant(1, "Anton", "Bulgaria", Gender.Male, LocalDate.of(2000, 1, 1));

        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        Biatlon biatlon = new Biatlon(
                "Men Biatlon",
                Gender.Male,
                18,
                participants,
                4,
                2,
                new BigDecimal("60.000")
        );

        BiatlonResultOfParticipant result = new BiatlonResultOfParticipant(participant, new BigDecimal("1500.000"), 4, 2);
        BiatlonService biatlonService = new BiatlonServiceImpl();

        // When
        BigDecimal actual = biatlonService.calculatePenalty(biatlon, result);

        // Then
        BigDecimal expected = new BigDecimal("120.000");
        assertEquals(expected, actual);
    }

    @Test
    void whenParticipantFinishedRace_thenTotalTimeIsCalculatedCorrectly() {
        // Given
        Participant participant = new Participant(1, "Anton", "Bulgaria", Gender.Male, LocalDate.of(2000, 1, 1));

        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        Biatlon biatlon = new Biatlon(
                "Men Biatlon",
                Gender.Male,
                18,
                participants,
                4,
                2,
                new BigDecimal("60.000")
        );

        BiatlonResultOfParticipant result = new BiatlonResultOfParticipant(participant, new BigDecimal("1500.000"), 4, 2);
        BiatlonService biatlonService = new BiatlonServiceImpl();

        // When
        BigDecimal actual = biatlonService.calculateTotalTime(biatlon, result);

        // Then
        BigDecimal expected = new BigDecimal("1620.000");
        assertEquals(expected, actual);
    }

    @Test
    void whenParticipantHasNotCompletedAllLaps_thenParticipantIsNotInFinalRanking() {
        // Given
        Participant participant1 = new Participant(1, "Anton", "Bulgaria", Gender.Male, LocalDate.of(2000, 1, 1));
        Participant participant2 = new Participant(2, "Ivan", "Germany", Gender.Male, LocalDate.of(2001, 1, 1));

        List<Participant> participants = new ArrayList<>();
        participants.add(participant1);
        participants.add(participant2);

        Biatlon biatlon = new Biatlon(
                "Men Biatlon",
                Gender.Male,
                18,
                participants,
                4,
                2,
                new BigDecimal("60.000")
        );

        BiatlonResultOfParticipant result1 = new BiatlonResultOfParticipant(participant1, new BigDecimal("1500.000"), 4, 1);
        BiatlonResultOfParticipant result2 = new BiatlonResultOfParticipant(participant2, new BigDecimal("1400.000"), 3, 0);

        biatlon.addResult(result1);
        biatlon.addResult(result2);

        BiatlonService biatlonService = new BiatlonServiceImpl();

        // When
        List<BiatlonResultOfParticipant> actual = biatlonService.getFinalRanking(biatlon);

        // Then
        assertEquals(1, actual.size());
        assertEquals(participant1, actual.get(0).getParticipant());
    }

    @Test
    void whenParticipantsHaveDifferentTotalTimes_thenFinalRankingIsSortedByTotalTime() {
        // Given
        Participant participant1 = new Participant(1, "Anton", "Bulgaria", Gender.Male, LocalDate.of(2000, 1, 1));
        Participant participant2 = new Participant(2, "Ivan", "Germany", Gender.Male, LocalDate.of(2001, 1, 1));

        List<Participant> participants = new ArrayList<>();
        participants.add(participant1);
        participants.add(participant2);

        Biatlon biatlon = new Biatlon(
                "Men Biatlon",
                Gender.Male,
                18,
                participants,
                4,
                2,
                new BigDecimal("60.000")
        );

        BiatlonResultOfParticipant result1 = new BiatlonResultOfParticipant(participant1, new BigDecimal("1500.000"), 4, 2);
        // 1620

        BiatlonResultOfParticipant result2 = new BiatlonResultOfParticipant(participant2, new BigDecimal("1480.000"), 4, 1);
        // 1540

        biatlon.addResult(result1);
        biatlon.addResult(result2);

        BiatlonService biatlonService = new BiatlonServiceImpl();

        // When
        List<BiatlonResultOfParticipant> actual = biatlonService.getFinalRanking(biatlon);

        // Then
        assertEquals(participant2, actual.get(0).getParticipant());
        assertEquals(participant1, actual.get(1).getParticipant());
    }

    @Test
    void whenCompletedLapsAreMoreThanRequired_thenInvalidBiatlonExceptionIsThrown() {
        // Given
        Participant participant = new Participant(1, "Anton", "Bulgaria", Gender.Male, LocalDate.of(2000, 1, 1));

        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        Biatlon biatlon = new Biatlon(
                "Men Biatlon",
                Gender.Male,
                18,
                participants,
                4,
                2,
                new BigDecimal("60.000")
        );

        BiatlonResultOfParticipant result = new BiatlonResultOfParticipant(participant, new BigDecimal("1500.000"), 5, 1);
        BiatlonService biatlonService = new BiatlonServiceImpl();

        // When Then
        assertThrows(InvalidBiatlonException.class, () -> {biatlonService.calculateTotalTime(biatlon, result);});
    }

    @Test
    void whenBiatlonIsNull_thenInvalidBiatlonExceptionIsThrown() {
        // Given
        Biatlon biatlon = null;

        Participant participant = new Participant(
                1,
                "Anton",
                "Bulgaria",
                Gender.Male,
                LocalDate.of(2000, 1, 1)
        );

        BiatlonResultOfParticipant result =
                new BiatlonResultOfParticipant(
                        participant,
                        new BigDecimal("1500.000"),
                        4,
                        1
                );

        BiatlonService biatlonService = new BiatlonServiceImpl();

        // When Then
        assertThrows(InvalidBiatlonException.class, () -> {biatlonService.calculateTotalTime(biatlon, result);});
    }
}