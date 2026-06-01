package org.informatics.data;

import org.informatics.exceptions.InvalidBiatlonException;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public class BiatlonResultOfParticipant implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Participant participant;
    private BigDecimal skiTime;
    private int completedLaps;
    private int missedShots;

    // Конструктори ---------------------------------------------------------------------------------------------
    public BiatlonResultOfParticipant(Participant participant) {
        this.participant = participant;
    }

    public BiatlonResultOfParticipant(Participant participant,
                                      BigDecimal skiTime,
                                      int completedLaps,
                                      int missedShots) {

        validateSkiTime(skiTime);
        validateCompletedLaps(completedLaps);
        validateMissedShots(missedShots);

        this.participant = participant;
        this.skiTime = skiTime;
        this.completedLaps = completedLaps;
        this.missedShots = missedShots;
    }

    // Гетъри ------------------------------------------------------------------------------------------------------
    public Participant getParticipant() {
        return participant;
    }

    public BigDecimal getSkiTime() {
        return skiTime;
    }

    public int getCompletedLaps() {
        return completedLaps;
    }

    public int getMissedShots() {
        return missedShots;
    }

    // Сетъри ------------------------------------------------------------------------------------------------------
    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public void setSkiTime(BigDecimal skiTime) {
        validateSkiTime(skiTime);
        this.skiTime = skiTime;
    }

    public void setCompletedLaps(int completedLaps) {
        validateCompletedLaps(completedLaps);
        this.completedLaps = completedLaps;
    }

    public void setMissedShots(int missedShots) {
        validateMissedShots(missedShots);
        this.missedShots = missedShots;
    }

    // Валидиране на входа -----------------------------------------------------------------------------------------

    private void validateSkiTime(BigDecimal skiTime) {
        if (skiTime == null) {
            throw new InvalidBiatlonException("Ski time cannot be null.");
        }

        if (skiTime.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidBiatlonException("Ski time must be greater than 0.");
        }
    }

    private void validateCompletedLaps(int completedLaps) {
        if (completedLaps < 0) {
            throw new InvalidBiatlonException("Completed laps cannot be negative.");
        }
    }

    private void validateMissedShots(int missedShots) {
        if (missedShots < 0) {
            throw new InvalidBiatlonException("Missed shots cannot be negative.");
        }
    }

    // Проверка дали участникът е завършил ----------------------------------------------------------------------
    public boolean hasFinished(int requiredLaps) {
        return skiTime != null && completedLaps == requiredLaps;
    }

    @Override
    public String toString() {
        return "BiatlonResultOfParticipant{" +
                "participant=" + participant +
                ", skiTime=" + skiTime +
                ", completedLaps=" + completedLaps +
                ", missedShots=" + missedShots +
                '}';
    }
}