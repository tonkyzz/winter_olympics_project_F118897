package org.informatics.data;

import org.informatics.exceptions.InvalidCompetitionException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Competition implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private Gender gender;
    private int minimumAge;
    private List<Participant> participants;

    // Конструктор -----------------------------------------------------------------------------------------------------
    public Competition(String name, Gender gender, int minimumAge, List<Participant> participants) {

        validateName(name);
        validateGender(gender);
        validateMinimumAge(minimumAge);

        this.name = name;
        this.gender = gender;
        this.minimumAge = minimumAge;

        if (participants == null) {
            this.participants = new ArrayList<>();
        } else {
            this.participants = participants;
        }
    }

    // Getter-и -------------------------------------------------------------------------------------------------------
    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public int getMinimumAge() {
        return minimumAge;
    }

    public List<Participant> getParticipants() {
        return participants;
    }


    // Setter-и --------------------------------------------------------------------------------------------------------
    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    public void setGender(Gender gender) {
        validateGender(gender);
        this.gender = gender;
    }

    public void setMinimumAge(int minimumAge) {
        validateMinimumAge(minimumAge);
        this.minimumAge = minimumAge;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }


    // Добавяне на участник -------------------------------------------------------------------------------------------

    public void addParticipant(Participant participant) {
        this.participants.add(participant);
    }

    // Валидиране на вход ---------------------------------------------------------------------------------------------

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new InvalidCompetitionException("Competition name is empty.");
        }
    }

    private void validateGender(Gender gender) {
        if (gender == null) {
            throw new InvalidCompetitionException("No gender provided.");
        }
    }

    private void validateMinimumAge(int minimumAge) {
        if (minimumAge < 0) {
            throw new InvalidCompetitionException("Minimum age is negative.");
        }
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
