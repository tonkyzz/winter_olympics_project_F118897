package org.informatics.data;

import org.informatics.exceptions.InvalidBiatlonException;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Biatlon extends Competition implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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

        validateLapsCount(lapsCount);
        validateShootingCount(shootingsCount);
        validatePenaltyForOneMiss(penaltyForOneMiss);

        this.lapsCount = lapsCount;
        this.shootingsCount = shootingsCount;
        this.penaltyForOneMiss = penaltyForOneMiss;
        this.results = new ArrayList<>();
    }

    // Добавяне на резултат -------------------------------------------------------------------------------------
    public void addResult(BiatlonResultOfParticipant result) {
        results.add(result);
    }

    // Гетъри ---------------------------------------------------------------------------------------------------
    public int getLapsCount() {
        return lapsCount;
    }

    public int getShootingsCount() {
        return shootingsCount;
    }

    public BigDecimal getPenaltyForOneMiss() {
        return penaltyForOneMiss;
    }

    public List<BiatlonResultOfParticipant> getResults() {
        return results;
    }

    // Сетъри -----------------------------------------------------------------------------------------------------
    public void setLapsCount(int lapsCount) {
        validateLapsCount(lapsCount);
        this.lapsCount = lapsCount;
    }

    public void setShootingsCount(int shootingsCount) {
        validateShootingCount(shootingsCount);
        this.shootingsCount = shootingsCount;
    }

    public void setPenaltyForOneMiss(BigDecimal penaltyForOneMiss) {
        validatePenaltyForOneMiss(penaltyForOneMiss);
        this.penaltyForOneMiss = penaltyForOneMiss;
    }

    public void setResults(List<BiatlonResultOfParticipant> results) {
        this.results = results;
    }

    //Валидиране на входа ---------------------------------------------------------------------------------------
    private void validateLapsCount(int lapsCount) {
        if (lapsCount == 0) {
            throw new InvalidBiatlonException("Laps count cannot be 0");
        }
        if(lapsCount < 0) {
            throw new InvalidBiatlonException("Laps count cannot be negative");
        }
    }

    private void validateShootingCount(int shootingsCount) {
        if (shootingsCount == 0) {
            throw new InvalidBiatlonException("Shootings count cannot be 0");
        }
        if(shootingsCount < 0) {
            throw new InvalidBiatlonException("Shootings count cannot be negative");
        }
    }

    private void validatePenaltyForOneMiss(BigDecimal penaltyForOneMiss) {
        if (penaltyForOneMiss == null) {
            throw new InvalidBiatlonException("Penalty cannot be 0 minutes");
        }
        if(penaltyForOneMiss.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidBiatlonException("Penalty cannot be negative minutes");
        }
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

