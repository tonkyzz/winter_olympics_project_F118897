package org.informatics.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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


    // Сравняване --------------------------------------------------------------------------------
    private class FirstManshComparator implements Comparator<SkiSlalomResultOfParticipant> {

        @Override
        public int compare(SkiSlalomResultOfParticipant r1, SkiSlalomResultOfParticipant r2) {
            return r1.getFirstManshTime().compareTo(r2.getFirstManshTime());
        }
    }

    private class SecondManshComparator implements Comparator<SkiSlalomResultOfParticipant> {

        @Override
        public int compare(SkiSlalomResultOfParticipant r1, SkiSlalomResultOfParticipant r2) {
            return r1.getSecondManshTime().compareTo(r2.getSecondManshTime());
        }
    }

    private class TotalTimeComparator implements Comparator<SkiSlalomResultOfParticipant> {

        @Override
        public int compare(SkiSlalomResultOfParticipant r1, SkiSlalomResultOfParticipant r2) {
            return r1.getTotal().compareTo(r2.getTotal());
        }
    }

    // Добавяне на индивидуалните резултати
    public void addResult(SkiSlalomResultOfParticipant result) {
        results.add(result);
    }

    // Класиране за втори манш и списък с класираните
    public List<SkiSlalomResultOfParticipant> getQualifiedForSecondMansh() {
        List<SkiSlalomResultOfParticipant> firstManshRanking = getFirstManshRanking();
        List<SkiSlalomResultOfParticipant> qualified = new ArrayList<>();

        int count = Math.min(limitForSecondMansh, firstManshRanking.size());

        for (int i = 0; i < count; i++) {
            qualified.add(firstManshRanking.get(i));
        }

        return qualified;
    }

    // Класиране на резултатите
    public List<SkiSlalomResultOfParticipant> getFirstManshRanking() {
        List<SkiSlalomResultOfParticipant> ranking = new ArrayList<>();

        for (SkiSlalomResultOfParticipant result : results) {
            if (result.getFirstManshTime() != null) {
                ranking.add(result);
            }
        }

        Collections.sort(ranking, new FirstManshComparator());

        return ranking;
    }

    public List<SkiSlalomResultOfParticipant> getSecondManshRanking() {
        List<SkiSlalomResultOfParticipant> qualified = getQualifiedForSecondMansh();
        List<SkiSlalomResultOfParticipant> ranking = new ArrayList<>();

        for (SkiSlalomResultOfParticipant result : qualified) {
            if (result.getSecondManshTime() != null) {
                ranking.add(result);
            }
        }

        Collections.sort(ranking, new SecondManshComparator());

        return ranking;
    }

    public List<SkiSlalomResultOfParticipant> getFinalRanking() {
        List<SkiSlalomResultOfParticipant> qualified = getQualifiedForSecondMansh();
        List<SkiSlalomResultOfParticipant> ranking = new ArrayList<>();

        for (SkiSlalomResultOfParticipant result : qualified) {
            if (result.hasFinished()) {
                ranking.add(result);
            }
        }

        Collections.sort(ranking, new TotalTimeComparator());

        return ranking;
    }


    @Override
    public void finalResults() {
        List<SkiSlalomResultOfParticipant> finalRanking = getFinalRanking();

        System.out.println("Final results for " + getName() + ":");

        for (int i = 0; i < finalRanking.size(); i++) {
            SkiSlalomResultOfParticipant result = finalRanking.get(i);

            System.out.println((i + 1) + ". "
                    + result.getParticipant().getName()
                    + " | First mansh: " + result.getFirstManshTime()
                    + " | Second mansh: " + result.getSecondManshTime()
                    + " | Total: " + result.getTotal()
                    + " sec");
        }
    }
}
