package org.informatics.data;

import java.math.BigDecimal;

public class SkiSlalomResultOfParticipant {

    private Participant participant;
    private BigDecimal firstManshTime;
    private BigDecimal secondManshTime;

    //Конструктури -------------------------------------------------------------------------------------------------
    public SkiSlalomResultOfParticipant(Participant participant) {
        this.participant = participant;
    }//Понеже първо се добавя само първи манш, после ако се класира - втори манш

    public SkiSlalomResultOfParticipant(Participant participant, BigDecimal firstManshTime, BigDecimal secondManshTime) {
        this.participant = participant;
        this.firstManshTime = firstManshTime;
        this.secondManshTime = secondManshTime;
    }

    // Гетъри и сетъри ------------------------------------------------------------------------------------------------
    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public BigDecimal getFirstManshTime() {
        return firstManshTime;
    }

    public void setFirstManshTime(BigDecimal firstManshTime) {
        this.firstManshTime = firstManshTime;
    }

    public BigDecimal getSecondManshTime() {
        return secondManshTime;
    }

    public void setSecondManshTime(BigDecimal secondManshTime) {
        this.secondManshTime = secondManshTime;
    }


    public boolean hasFinished(){
        return firstManshTime != null && secondManshTime != null;
    }

    public BigDecimal getTotal() {
        if (!hasFinished()) {
            throw new IllegalStateException("Participant has not finished both manshes.");
        }

        return firstManshTime.add(secondManshTime);
    }

    @Override
    public String toString() {
        return "SkiSlalomResultOfParticipant{" +
                "participant=" + participant +
                ", firstManshTime=" + firstManshTime +
                ", secondManshTime=" + secondManshTime +
                '}';
    }
}
