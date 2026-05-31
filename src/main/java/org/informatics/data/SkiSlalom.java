package org.informatics.data;

import org.informatics.exceptions.InvalidSkiSlalomException;

import java.util.ArrayList;
import java.util.List;

public class SkiSlalom extends Competition {

    private int limitForSecondMansh;
    private List<SkiSlalomResultOfParticipant> results;

    //Конструктури ---------------------------------------------------------------------------------------------
    public SkiSlalom(String name,
                     Gender gender,
                     int minimumAge,
                     List<Participant> participants,
                     int limitForSecondMansh)
    {
        super(name, gender, minimumAge, participants);

        validateLimitForSecondMansh(limitForSecondMansh);

        this.limitForSecondMansh = limitForSecondMansh;
        this.results = new ArrayList<>();
    }

    // Добавяне на индивидуалните резултати ---------------------------------------------------------------------
    public void addResult(SkiSlalomResultOfParticipant result) {
        results.add(result);
    }

    // Гетъри и сетъри -----------------------------------------------------------------------------------------
    public int getLimitForSecondMansh() {
        return limitForSecondMansh;
    }

    public void setLimitForSecondMansh(int limitForSecondMansh) {
        validateLimitForSecondMansh(limitForSecondMansh);
        this.limitForSecondMansh = limitForSecondMansh;
    }

    public List<SkiSlalomResultOfParticipant> getResults() {
        return results;
    }

    public void setResults(List<SkiSlalomResultOfParticipant> results) {
        this.results = results;
    }

    //Валидиране на броя класирани, за втори манш -----------------------------------------------------------------
    private void validateLimitForSecondMansh(int limitForSecondMansh) {
        if (limitForSecondMansh <= 0) {
            throw new InvalidSkiSlalomException("Number of qualified participants must be greater than 0.");
        }

        if (getParticipants() != null && limitForSecondMansh > getParticipants().size()) {
            throw new InvalidSkiSlalomException("Number of qualified participants cannot be greater than total participants.");
        }
    }

}
