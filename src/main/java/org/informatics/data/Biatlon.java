package org.informatics.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Biatlon extends Competition {

    private int lapsCount;
    private int shootingsCount;
    private BigDecimal penaltyForOneMiss;
    private List<BiatlonResultOfParticipant> results;

    // Конструктор ----------------------------------------------------------------------------------------------
    public Biatlon(String name, Gender gender, int minimumAge,
                   List<Participant> participants,
                   int lapsCount,
                   int shootingsCount,
                   BigDecimal penaltyForOneMiss) {

        super(name, gender, minimumAge, participants);
        this.lapsCount = lapsCount;
        this.shootingsCount = shootingsCount;
        this.penaltyForOneMiss = penaltyForOneMiss;
        this.results = new ArrayList<>();
    }

    // Добавяне на резултат -------------------------------------------------------------------------------------
    public void addResult(BiatlonResultOfParticipant result) {
        results.add(result);
    }

    // Гетъри и сетъри ------------------------------------------------------------------------------------------
    public int getLapsCount() {
        return lapsCount;
    }

    public void setLapsCount(int lapsCount) {
        this.lapsCount = lapsCount;
    }

    public int getShootingsCount() {
        return shootingsCount;
    }

    public void setShootingsCount(int shootingsCount) {
        this.shootingsCount = shootingsCount;
    }

    public BigDecimal getPenaltyForOneMiss() {
        return penaltyForOneMiss;
    }

    public void setPenaltyForOneMiss(BigDecimal penaltyForOneMiss) {
        this.penaltyForOneMiss = penaltyForOneMiss;
    }

    public List<BiatlonResultOfParticipant> getResults() {
        return results;
    }

    public void setResults(List<BiatlonResultOfParticipant> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Biatlon{" +
                "name='" + getName() + '\'' +
                ", gender=" + getGender() +
                ", minimumAge=" + getMinimumAge() +
                ", lapsCount=" + lapsCount +
                ", shootingsCount=" + shootingsCount +
                ", penaltyForOneMiss=" + penaltyForOneMiss +
                ", results=" + results +
                '}';
    }
}

