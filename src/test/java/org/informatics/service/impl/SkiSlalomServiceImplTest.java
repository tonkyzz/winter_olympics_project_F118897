package org.informatics.service.impl;

import org.informatics.data.*;
import org.informatics.exceptions.InvalidSkiSlalomException;
import org.informatics.service.SkiSlalomService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SkiSlalomServiceImplTest {

    @Test
    void whenFirstManshTimesAreDifferent_thenRankingIsSortedByFirstManshTime() {
        // Given
        Participant participant1 = new Participant(1, "Anton", "Bulgaria", Gender.Male, LocalDate.of(2000, 1, 1));
        Participant participant2 = new Participant(2, "Ivan", "Germany", Gender.Male, LocalDate.of(2001, 1, 1));
        Participant participant3 = new Participant(3, "Georgi", "Austria", Gender.Male, LocalDate.of(2002, 1, 1));

        List<Participant> participants = new ArrayList<>();
        participants.add(participant1);
        participants.add(participant2);
        participants.add(participant3);

        SkiSlalom skiSlalom = new SkiSlalom("Men Ski Slalom", Gender.Male, 18, participants, 2);

        SkiSlalomResultOfParticipant result1 = new SkiSlalomResultOfParticipant(participant1);
        result1.setFirstManshTime(new BigDecimal("55.000"));

        SkiSlalomResultOfParticipant result2 = new SkiSlalomResultOfParticipant(participant2);
        result2.setFirstManshTime(new BigDecimal("54.000"));

        SkiSlalomResultOfParticipant result3 = new SkiSlalomResultOfParticipant(participant3);
        result3.setFirstManshTime(new BigDecimal("60.000"));

        skiSlalom.addResult(result1);
        skiSlalom.addResult(result2);
        skiSlalom.addResult(result3);

        SkiSlalomService skiSlalomService = new SkiSlalomServiceImpl();

        // When
        List<SkiSlalomResultOfParticipant> actual = skiSlalomService.getFirstManshRanking(skiSlalom);

        // Then
        assertEquals(participant2, actual.get(0).getParticipant());
        assertEquals(participant1, actual.get(1).getParticipant());
        assertEquals(participant3, actual.get(2).getParticipant());
    }

    @Test
    void whenLimitForSecondManshIsTwo_thenOnlyBestTwoParticipantsQualify() {
        // Given
        Participant participant1 = new Participant(1, "Anton", "Bulgaria", Gender.Male, LocalDate.of(2000, 1, 1));
        Participant participant2 = new Participant(2, "Ivan", "Germany", Gender.Male, LocalDate.of(2001, 1, 1));
        Participant participant3 = new Participant(3, "Georgi", "Austria", Gender.Male, LocalDate.of(2002, 1, 1));

        List<Participant> participants = new ArrayList<>();
        participants.add(participant1);
        participants.add(participant2);
        participants.add(participant3);

        SkiSlalom skiSlalom = new SkiSlalom("Men Ski Slalom", Gender.Male, 18, participants, 2);

        SkiSlalomResultOfParticipant result1 = new SkiSlalomResultOfParticipant(participant1);
        result1.setFirstManshTime(new BigDecimal("55.000"));

        SkiSlalomResultOfParticipant result2 = new SkiSlalomResultOfParticipant(participant2);
        result2.setFirstManshTime(new BigDecimal("54.000"));

        SkiSlalomResultOfParticipant result3 = new SkiSlalomResultOfParticipant(participant3);
        result3.setFirstManshTime(new BigDecimal("60.000"));

        skiSlalom.addResult(result1);
        skiSlalom.addResult(result2);
        skiSlalom.addResult(result3);

        SkiSlalomService skiSlalomService = new SkiSlalomServiceImpl();

        // When
        List<SkiSlalomResultOfParticipant> actual = skiSlalomService.getQualifiedForSecondMansh(skiSlalom);

        // Then
        assertEquals(2, actual.size());
        assertEquals(participant2, actual.get(0).getParticipant());
        assertEquals(participant1, actual.get(1).getParticipant());
    }

    @Test
    void whenParticipantIsNotQualified_thenParticipantIsNotInFinalRanking() {
        // Given
        Participant participant1 = new Participant(1, "Anton", "Bulgaria", Gender.Male, LocalDate.of(2000, 1, 1));
        Participant participant2 = new Participant(2, "Ivan", "Germany", Gender.Male, LocalDate.of(2001, 1, 1));
        Participant participant3 = new Participant(3, "Georgi", "Austria", Gender.Male, LocalDate.of(2002, 1, 1));

        List<Participant> participants = new ArrayList<>();
        participants.add(participant1);
        participants.add(participant2);
        participants.add(participant3);

        SkiSlalom skiSlalom = new SkiSlalom("Men Ski Slalom", Gender.Male, 18, participants, 2);

        SkiSlalomResultOfParticipant result1 = new SkiSlalomResultOfParticipant(participant1, new BigDecimal("55.000"), new BigDecimal("56.000"));
        SkiSlalomResultOfParticipant result2 = new SkiSlalomResultOfParticipant(participant2, new BigDecimal("54.000"), new BigDecimal("58.000"));
        SkiSlalomResultOfParticipant result3 = new SkiSlalomResultOfParticipant(participant3, new BigDecimal("60.000"), new BigDecimal("1.000"));

        skiSlalom.addResult(result1);
        skiSlalom.addResult(result2);
        skiSlalom.addResult(result3);

        SkiSlalomService skiSlalomService = new SkiSlalomServiceImpl();

        // When
        List<SkiSlalomResultOfParticipant> actual = skiSlalomService.getFinalRanking(skiSlalom);

        // Then
        assertEquals(2, actual.size());
        assertEquals(participant1, actual.get(0).getParticipant());
        assertEquals(participant2, actual.get(1).getParticipant());
    }

    @Test
    void whenSkiSlalomIsNull_thenInvalidSkiSlalomExceptionIsThrown() {
        // Given
        SkiSlalom skiSlalom = null;
        SkiSlalomService skiSlalomService = new SkiSlalomServiceImpl();

        // When Then
        assertThrows(InvalidSkiSlalomException.class, () -> {skiSlalomService.getFirstManshRanking(skiSlalom);});
    }

    @Test
    void whenParticipantHasOnlyFirstMansh_thenParticipantIsNotInFinalRanking() {
        // Given
        Participant participant1 = new Participant(1, "Anton", "Bulgaria", Gender.Male, LocalDate.of(2000, 1, 1));
        Participant participant2 = new Participant(2, "Ivan", "Germany", Gender.Male, LocalDate.of(2001, 1, 1));

        List<Participant> participants = new ArrayList<>();
        participants.add(participant1);
        participants.add(participant2);

        SkiSlalom skiSlalom = new SkiSlalom(
                "Men Ski Slalom",
                Gender.Male,
                18,
                participants,
                2
        );

        SkiSlalomResultOfParticipant result1 =
                new SkiSlalomResultOfParticipant(
                        participant1,
                        new BigDecimal("55.000"),
                        new BigDecimal("56.000")
                );

        SkiSlalomResultOfParticipant result2 = new SkiSlalomResultOfParticipant(participant2);

        result2.setFirstManshTime(new BigDecimal("54.000"));
        // Няма втори манш

        skiSlalom.addResult(result1);
        skiSlalom.addResult(result2);

        SkiSlalomService skiSlalomService = new SkiSlalomServiceImpl();

        // When
        List<SkiSlalomResultOfParticipant> actual = skiSlalomService.getFinalRanking(skiSlalom);

        // Then
        assertEquals(1, actual.size());
        assertEquals(participant1, actual.get(0).getParticipant());
    }
}