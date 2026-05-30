package org.informatics.data;

public class OlympicMedal {

    private Participant participant;
    private String competitionName;
    private int place;
    private String medalType;

    public OlympicMedal(Participant participant, String competitionName, int place, String medalType) {
        this.participant = participant;
        this.competitionName = competitionName;
        this.place = place;
        this.medalType = medalType;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public String getMedalType() {
        return medalType;
    }

    public void setMedalType(String medalType) {
        this.medalType = medalType;
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