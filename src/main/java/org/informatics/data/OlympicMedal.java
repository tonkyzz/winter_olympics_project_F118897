package org.informatics.data;

import org.informatics.exceptions.InvalidOlympicMedalException;

public class OlympicMedal {

    private final Participant participant;
    private final String competitionName;
    private final int place;
    private final MedalType medalType;

    // Конструктор ---------------------------------------------------------------------------------------------------
    public OlympicMedal(Participant participant,
                        String competitionName,
                        int place,
                        MedalType medalType) {

        validateParticipant(participant);
        validateCompetitionName(competitionName);
        validatePlace(place);
        validateMedalType(medalType);

        this.participant = participant;
        this.competitionName = competitionName;
        this.place = place;
        this.medalType = medalType;
    }

    // Гетъри --------------------------------------------------------------------------------------------------------
    public Participant getParticipant() {
        return participant;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public int getPlace() {
        return place;
    }

    public MedalType getMedalType() {
        return medalType;
    }

    // Валидиране на вход ---------------------------------------------------------------------------------------------
    private void validateParticipant(Participant participant) {
        if (participant == null) {
            throw new InvalidOlympicMedalException("Participant cannot be null.");
        }
    }

    private void validateCompetitionName(String competitionName) {
        if (competitionName == null || competitionName.isBlank()) {
            throw new InvalidOlympicMedalException("Competition name cannot be empty.");
        }
    }

    private void validatePlace(int place) {
        if (place < 1 || place > 3) {
            throw new InvalidOlympicMedalException("Medal place must be between 1 and 3.");
        }
    }

    private void validateMedalType(MedalType medalType) {
        if (medalType == null) {
            throw new InvalidOlympicMedalException("Medal type cannot be null.");
        }
    }

    @Override
    public String toString() {
        return medalType + " medal | "
                + participant.getName()
                + " | " + participant.getCountry()
                + " | " + competitionName
                + " | place: " + place;
    }
}