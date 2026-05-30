package org.informatics.data;

import java.util.List;

public abstract class Competition {

    private String name;
    private Gender gender;
    private int minimumAge;

    private List<Participant> participants;

    public Competition(String name, Gender gender, int minimumAge, List<Participant> participants) {
        this.name = name;
        this.gender = gender;
        this.minimumAge = minimumAge;
        this.participants = participants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(int minimumAge) {
        this.minimumAge = minimumAge;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public void addParticipant(Participant participant) {
        this.participants.add(participant);
    }


    @Override
    public String toString() {
        return "Competition{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                ", minimumAge=" + minimumAge +
                ", participants=" + participants +
                '}';
    }
}
