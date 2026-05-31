package org.informatics.data;

import org.informatics.exceptions.InvalidSkiSlalomResultException;

import java.math.BigDecimal;

public class SkiSlalomResultOfParticipant {

    private Participant participant;
    private BigDecimal firstManshTime;
    private BigDecimal secondManshTime;

    //Конструктури -------------------------------------------------------------------------------------------------
    public SkiSlalomResultOfParticipant(Participant participant) {
        this.participant = participant;
    }//Понеже първо се добавя само първи манш, после ако се класира - втори манш

    public SkiSlalomResultOfParticipant(Participant participant,
                                        BigDecimal firstManshTime,
                                        BigDecimal secondManshTime)
    {
        this.participant = participant;
        validateManshTime(firstManshTime);
        this.firstManshTime = firstManshTime;
        validateManshTime(secondManshTime);
        this.secondManshTime = secondManshTime;
    }

    // Гетъри ------------------------------------------------------------------------------------------------
    public Participant getParticipant() {
        return participant;
    }

    public BigDecimal getFirstManshTime() {
        return firstManshTime;
    }

    public BigDecimal getSecondManshTime() {
        return secondManshTime;
    }

    // Сетъри -------------------------------------------------------------------------------------------------
    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public void setFirstManshTime(BigDecimal firstManshTime) {
        validateManshTime(firstManshTime);
        this.firstManshTime = firstManshTime;
    }

    public void setSecondManshTime(BigDecimal secondManshTime) {
        validateManshTime(secondManshTime);
        this.secondManshTime = secondManshTime;
    }

    // Валидиране на време за манш -----------------------------------------------------------------------------
    private void validateManshTime(BigDecimal manshTime) {
        if (manshTime == null) {
            throw new InvalidSkiSlalomResultException("Mansh time cannot be null.");
        }
        if(manshTime.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidSkiSlalomResultException("Mansh time cannot be negative.");
        }
    }


    // Помощни функции -------------------------------------------------------------------------------------------
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
