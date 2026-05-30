package org.informatics.data;

import java.util.ArrayList;
import java.util.List;

public class Olympics {

    private String name;
    private List<Competition> competitions;

    public Olympics(String name) {
        this.name = name;
        this.competitions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Competition> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(List<Competition> competitions) {
        this.competitions = competitions;
    }

    public void addCompetition(Competition competition) {
        competitions.add(competition);
    }

    @Override
    public String toString() {
        return "Olympics{" +
                "name='" + name + '\'' +
                ", competitions=" + competitions +
                '}';
    }
}
