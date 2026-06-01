package org.informatics.service.impl;

import org.informatics.data.*;
import org.informatics.service.OlympicsService;
import org.junit.jupiter.api.Test;
import org.informatics.exceptions.InvalidOlympicsException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OlympicsServiceImplTest {

    @Test
    void whenCreateSkiSlalom_thenCompetitionIsAddedToOlympics() {
        // Given
        Olympics olympics = new Olympics("Winter Olympics");

        Participant participant = new Participant(1, "Anton", "Bulgaria", Gender.Male, LocalDate.of(2000, 1, 1));

        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        OlympicsService olympicsService = new OlympicsServiceImpl();

        // When
        SkiSlalom actual = olympicsService.createSkiSlalom(
                olympics,
                "Men Ski Slalom",
                Gender.Male,
                18,
                participants,
                1
        );

        // Then
        assertEquals(1, olympics.getCompetitions().size());
        assertEquals(actual, olympics.getCompetitions().get(0));
    }

    @Test
    void whenCreateBiatlon_thenCompetitionIsAddedToOlympics() {
        // Given
        Olympics olympics = new Olympics("Winter Olympics");

        Participant participant = new Participant(1, "Anton", "Bulgaria", Gender.Male, LocalDate.of(2000, 1, 1));

        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        OlympicsService olympicsService = new OlympicsServiceImpl();

        // When
        Biatlon actual = olympicsService.createBiatlon(
                olympics,
                "Men Biatlon",
                Gender.Male,
                18,
                participants,
                4,
                2,
                new BigDecimal("60.000")
        );

        // Then
        assertEquals(1, olympics.getCompetitions().size());
        assertEquals(actual, olympics.getCompetitions().get(0));
    }

    @Test
    void whenCompetitionHasFinalRanking_thenMedalistsAreReturned() {
        // Given
        Olympics olympics = new Olympics("Winter Olympics");

        Participant participant1 = new Participant(1, "Anton", "Bulgaria", Gender.Male, LocalDate.of(2000, 1, 1));
        Participant participant2 = new Participant(2, "Ivan", "Germany", Gender.Male, LocalDate.of(2001, 1, 1));
        Participant participant3 = new Participant(3, "Georgi", "Austria", Gender.Male, LocalDate.of(2002, 1, 1));

        List<Participant> participants = new ArrayList<>();
        participants.add(participant1);
        participants.add(participant2);
        participants.add(participant3);

        OlympicsService olympicsService = new OlympicsServiceImpl();

        SkiSlalom skiSlalom = olympicsService.createSkiSlalom(
                olympics,
                "Men Ski Slalom",
                Gender.Male,
                18,
                participants,
                3
        );

        skiSlalom.addResult(new SkiSlalomResultOfParticipant(
                participant1,
                new BigDecimal("55.000"),
                new BigDecimal("56.000")
        ));
        // 111

        skiSlalom.addResult(new SkiSlalomResultOfParticipant(
                participant2,
                new BigDecimal("54.000"),
                new BigDecimal("58.000")
        ));
        // 112

        skiSlalom.addResult(new SkiSlalomResultOfParticipant(
                participant3,
                new BigDecimal("53.000"),
                new BigDecimal("60.000")
        ));
        // 113

        // When
        List<OlympicMedal> actual = olympicsService.getAllMedalists(olympics);

        // Then
        assertEquals(3, actual.size());
        assertEquals(MedalType.GOLD, actual.get(0).getMedalType());
        assertEquals(MedalType.SILVER, actual.get(1).getMedalType());
        assertEquals(MedalType.BRONZE, actual.get(2).getMedalType());
    }

    @Test
    void whenMedalistsAreFromDifferentCountries_thenMedalsByCountryAreCalculatedCorrectly() {
        // Given
        Olympics olympics = new Olympics("Winter Olympics");

        Participant participant1 = new Participant(1, "Anton", "Bulgaria", Gender.Male, LocalDate.of(2000, 1, 1));
        Participant participant2 = new Participant(2, "Ivan", "Bulgaria", Gender.Male, LocalDate.of(2001, 1, 1));
        Participant participant3 = new Participant(3, "Georgi", "Germany", Gender.Male, LocalDate.of(2002, 1, 1));

        List<Participant> participants = new ArrayList<>();
        participants.add(participant1);
        participants.add(participant2);
        participants.add(participant3);

        OlympicsService olympicsService = new OlympicsServiceImpl();

        SkiSlalom skiSlalom = olympicsService.createSkiSlalom(
                olympics,
                "Men Ski Slalom",
                Gender.Male,
                18,
                participants,
                3
        );

        skiSlalom.addResult(new SkiSlalomResultOfParticipant(
                participant1,
                new BigDecimal("55.000"),
                new BigDecimal("56.000")
        ));

        skiSlalom.addResult(new SkiSlalomResultOfParticipant(
                participant2,
                new BigDecimal("54.000"),
                new BigDecimal("58.000")
        ));

        skiSlalom.addResult(new SkiSlalomResultOfParticipant(
                participant3,
                new BigDecimal("53.000"),
                new BigDecimal("60.000")
        ));

        // When
        Map<String, Integer> actual = olympicsService.getMedalsByCountry(olympics);

        // Then
        assertEquals(2, actual.get("Bulgaria"));
        assertEquals(1, actual.get("Germany"));
    }

    @Test
    void whenParticipantsAreAdded_thenAverageAgeIsCalculated() {
        // Given
        Olympics olympics = new Olympics("Winter Olympics");

        Participant participant1 = new Participant(1, "Anton", "Bulgaria", Gender.Male, LocalDate.of(2000, 1, 1));
        Participant participant2 = new Participant(2, "Ivan", "Germany", Gender.Male, LocalDate.of(2002, 1, 1));

        List<Participant> participants = new ArrayList<>();
        participants.add(participant1);
        participants.add(participant2);

        OlympicsService olympicsService = new OlympicsServiceImpl();

        olympicsService.createSkiSlalom(
                olympics,
                "Men Ski Slalom",
                Gender.Male,
                18,
                participants,
                2
        );

        // When
        double actual = olympicsService.getAverageAgeOfParticipants(olympics);

        // Then
        double expected = (participant1.getAge() + participant2.getAge()) / 2.0;
        assertEquals(expected, actual);
    }

    @Test
    void whenMedalistsExist_thenYoungestMedalistIsReturned() {
        // Given
        Olympics olympics = new Olympics("Winter Olympics");

        Participant olderParticipant = new Participant(1, "Anton", "Bulgaria", Gender.Male, LocalDate.of(2000, 1, 1));
        Participant youngerParticipant = new Participant(2, "Ivan", "Germany", Gender.Male, LocalDate.of(2005, 1, 1));

        List<Participant> participants = new ArrayList<>();
        participants.add(olderParticipant);
        participants.add(youngerParticipant);

        OlympicsService olympicsService = new OlympicsServiceImpl();

        SkiSlalom skiSlalom = olympicsService.createSkiSlalom(
                olympics,
                "Men Ski Slalom",
                Gender.Male,
                18,
                participants,
                2
        );

        skiSlalom.addResult(new SkiSlalomResultOfParticipant(
                olderParticipant,
                new BigDecimal("55.000"),
                new BigDecimal("56.000")
        ));

        skiSlalom.addResult(new SkiSlalomResultOfParticipant(
                youngerParticipant,
                new BigDecimal("54.000"),
                new BigDecimal("58.000")
        ));

        // When
        Participant actual = olympicsService.getYoungestMedalist(olympics);

        // Then
        assertEquals(youngerParticipant, actual);
    }

    @Test
    void whenOlympicsIsNull_thenInvalidOlympicsExceptionIsThrown() {
        // Given
        Olympics olympics = null;

        Participant participant = new Participant(
                1,
                "Anton",
                "Bulgaria",
                Gender.Male,
                LocalDate.of(2000, 1, 1)
        );

        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        OlympicsService olympicsService = new OlympicsServiceImpl();

        // When Then
        assertThrows(InvalidOlympicsException.class, () -> {
            olympicsService.createSkiSlalom(
                    olympics,
                    "Men Ski Slalom",
                    Gender.Male,
                    18,
                    participants,
                    1
            );
        });
    }
}