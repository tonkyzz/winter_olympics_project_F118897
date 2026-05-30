package org.informatics.service;

import org.informatics.data.SkiSlalom;
import org.informatics.data.SkiSlalomResultOfParticipant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SkiSlalomServiceImpl implements SkiSlalomService {

    @Override
    public List<SkiSlalomResultOfParticipant> getFirstManshRanking(SkiSlalom skiSlalom) {
        List<SkiSlalomResultOfParticipant> ranking = new ArrayList<>();

        for (SkiSlalomResultOfParticipant result : skiSlalom.getResults()) {
            if (result.getFirstManshTime() != null) {
                ranking.add(result);
            }
        }

        Collections.sort(ranking, new FirstManshComparator());

        return ranking;
    }

    @Override
    public List<SkiSlalomResultOfParticipant> getQualifiedForSecondMansh(SkiSlalom skiSlalom) {
        List<SkiSlalomResultOfParticipant> firstManshRanking = getFirstManshRanking(skiSlalom);
        List<SkiSlalomResultOfParticipant> qualified = new ArrayList<>();

        int count = Math.min(
                skiSlalom.getLimitForSecondMansh(),
                firstManshRanking.size()
        );

        for (int i = 0; i < count; i++) {
            qualified.add(firstManshRanking.get(i));
        }

        return qualified;
    }

    @Override
    public List<SkiSlalomResultOfParticipant> getSecondManshRanking(SkiSlalom skiSlalom) {
        List<SkiSlalomResultOfParticipant> qualified = getQualifiedForSecondMansh(skiSlalom);
        List<SkiSlalomResultOfParticipant> ranking = new ArrayList<>();

        for (SkiSlalomResultOfParticipant result : qualified) {
            if (result.getSecondManshTime() != null) {
                ranking.add(result);
            }
        }

        Collections.sort(ranking, new SecondManshComparator());

        return ranking;
    }

    @Override
    public List<SkiSlalomResultOfParticipant> getFinalRanking(SkiSlalom skiSlalom) {
        List<SkiSlalomResultOfParticipant> qualified = getQualifiedForSecondMansh(skiSlalom);
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
    public void printFinalResults(SkiSlalom skiSlalom) {
        List<SkiSlalomResultOfParticipant> finalRanking = getFinalRanking(skiSlalom);

        System.out.println("Final results for " + skiSlalom.getName() + ":");

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

    public void printFirstManshRanking(SkiSlalom skiSlalom) {
        List<SkiSlalomResultOfParticipant> ranking = getFirstManshRanking(skiSlalom);

        System.out.println("First mansh ranking for " + skiSlalom.getName() + ":");

        for (int i = 0; i < ranking.size(); i++) {
            SkiSlalomResultOfParticipant result = ranking.get(i);

            System.out.println((i + 1) + ". "
                    + result.getParticipant().getName()
                    + " - "
                    + result.getFirstManshTime()
                    + " sec");
        }
    }

    public void printQualifiedForSecondMansh(SkiSlalom skiSlalom) {
        List<SkiSlalomResultOfParticipant> qualified = getQualifiedForSecondMansh(skiSlalom);

        System.out.println("Qualified for second mansh for " + skiSlalom.getName() + ":");

        for (int i = 0; i < qualified.size(); i++) {
            SkiSlalomResultOfParticipant result = qualified.get(i);

            System.out.println((i + 1) + ". "
                    + result.getParticipant().getName()
                    + " - "
                    + result.getFirstManshTime()
                    + " sec");
        }
    }

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
}