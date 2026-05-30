package org.informatics.data;

import java.math.BigDecimal;

public class BiatlonResultOfParticipant {

    private Participant participant;
    private BigDecimal skiTime;
    private int completedLaps;
    private int missedShots;

    // Конструктори ---------------------------------------------------------------------------------------------
    public BiatlonResultOfParticipant(Participant participant) {
        this.participant = participant;
    }

    public BiatlonResultOfParticipant(Participant participant, BigDecimal skiTime,
                                      int completedLaps, int missedShots) {
        this.participant = participant;
        this.skiTime = skiTime;
        this.completedLaps = completedLaps;
        this.missedShots = missedShots;
    }

    // Гетъри и сетъри ------------------------------------------------------------------------------------------
    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public BigDecimal getSkiTime() {
        return skiTime;
    }

    public void setSkiTime(BigDecimal skiTime) {
        this.skiTime = skiTime;
    }

    public int getCompletedLaps() {
        return completedLaps;
    }

    public void setCompletedLaps(int completedLaps) {
        this.completedLaps = completedLaps;
    }

    public int getMissedShots() {
        return missedShots;
    }

    public void setMissedShots(int missedShots) {
        this.missedShots = missedShots;
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
