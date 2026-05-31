package org.informatics.data;

import java.util.ArrayList;
import java.util.List;

public class SkiSlalom extends Competition {

    private int limitForSecondMansh;
    private List<SkiSlalomResultOfParticipant> results;

    //Конструктури ---------------------------------------------------------------------------------------------
    public SkiSlalom(String name, Gender gender, int minimumAge, List<Participant> participants, int limitForSecondMansh) {
        super(name, gender, minimumAge, participants);
        this.limitForSecondMansh = limitForSecondMansh;
        this.results = new ArrayList<>();
    }

    // Гетъри и сетъри -----------------------------------------------------------------------------------------
    public int getLimitForSecondMansh() {
        return limitForSecondMansh;
    }

    public void setLimitForSecondMansh(int limitForSecondMansh) {
        this.limitForSecondMansh = limitForSecondMansh;
    }

    public List<SkiSlalomResultOfParticipant> getResults() {
        return results;
    }

    public void setResults(List<SkiSlalomResultOfParticipant> results) {
        this.results = results;
    }

    // Добавяне на индивидуалните резултати
    public void addResult(SkiSlalomResultOfParticipant result) {
        results.add(result);
    }
}
