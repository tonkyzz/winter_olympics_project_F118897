package org.informatics.data;

import org.informatics.exceptions.InvalidOlympicsException;

import java.util.ArrayList;
import java.util.List;

public class Olympics {

    private String name;
    private List<Competition> competitions;

    // Конструктор -------------------------------------------------------------------------------------------------
    public Olympics(String name) {
        validateName(name);

        this.name = name;
        this.competitions = new ArrayList<>();
    }

    // Гетъри ------------------------------------------------------------------------------------------------------
    public String getName() {
        return name;
    }

    public List<Competition> getCompetitions() {
        return competitions;
    }

    // Сетъри ------------------------------------------------------------------------------------------------------
    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    public void setCompetitions(List<Competition> competitions) {
        this.competitions = competitions;
    }

    // Добавяне на състезания --------------------------------------------------------------------------------------
    public void addCompetition(Competition competition) {
        competitions.add(competition);
    }

    // Валидиране на вход ------------------------------------------------------------------------------------------
    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidOlympicsException("Name cannot be empty.");
        }
    }

    @Override
    public String toString() {
        return "Olympics{" +
                "name='" + name + '\'' +
                ", competitions=" + competitions +
                '}';
    }
}
