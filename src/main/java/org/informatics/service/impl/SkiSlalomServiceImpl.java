package org.informatics.service.impl;

import org.informatics.data.Participant;
import org.informatics.data.SkiSlalom;
import org.informatics.data.SkiSlalomResultOfParticipant;
import org.informatics.service.SkiSlalomService;

import org.informatics.exceptions.InvalidSkiSlalomException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SkiSlalomServiceImpl implements SkiSlalomService {

    // Сортиране на класирането от първи манш -------------------------------------------------------------------
    @Override
    public List<SkiSlalomResultOfParticipant> getFirstManshRanking(SkiSlalom skiSlalom) {

        validateSkiSlalom(skiSlalom);
        validateAllResults(skiSlalom);

        List<SkiSlalomResultOfParticipant> ranking = new ArrayList<>();

        for (SkiSlalomResultOfParticipant result : skiSlalom.getResults()) {
            if (result.getFirstManshTime() != null) {
                ranking.add(result);
            }
        }

        ranking.sort(new FirstManshComparator());

        return ranking;
    }

    // Създаване на списък с класираните ----------------------------------------------------------------------
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

    // Сортиране на финалното класиране -------------------------------------------------------------------------
    @Override
    public List<SkiSlalomResultOfParticipant> getFinalRanking(SkiSlalom skiSlalom) {
        List<SkiSlalomResultOfParticipant> qualified = getQualifiedForSecondMansh(skiSlalom);
        List<SkiSlalomResultOfParticipant> ranking = new ArrayList<>();

        for (SkiSlalomResultOfParticipant result : qualified) {
            if (result.hasFinished()) {
                ranking.add(result);
            }
        }

        ranking.sort(new TotalTimeComparator());

        return ranking;
    }


    // Принтиране на финалните резултати ---------------------------------------------------------------------
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


    // Сравняване на резултатите -------------------------------------------------------------------------------
    private class FirstManshComparator implements Comparator<SkiSlalomResultOfParticipant> {

        @Override
        public int compare(SkiSlalomResultOfParticipant r1, SkiSlalomResultOfParticipant r2) {
            return r1.getFirstManshTime().compareTo(r2.getFirstManshTime());
        }
    }

    private class TotalTimeComparator implements Comparator<SkiSlalomResultOfParticipant> {

        @Override
        public int compare(SkiSlalomResultOfParticipant r1, SkiSlalomResultOfParticipant r2) {
            return r1.getTotal().compareTo(r2.getTotal());
        }
    }

    // Валидиране на входа ----------------------------------------------------------------------------------------
    private void validateSkiSlalom(SkiSlalom skiSlalom) {
        if (skiSlalom == null) {
            throw new InvalidSkiSlalomException("Ski slalom cannot be null.");
        }

        if (skiSlalom.getParticipants() == null || skiSlalom.getParticipants().isEmpty()) {
            throw new InvalidSkiSlalomException("Ski slalom must have participants.");
        }

        if (skiSlalom.getResults() == null) {
            throw new InvalidSkiSlalomException("Ski slalom results cannot be null.");
        }

        if (skiSlalom.getLimitForSecondMansh() <= 0) {
            throw new InvalidSkiSlalomException("Limit for second mansh must be greater than 0.");
        }

        if (skiSlalom.getLimitForSecondMansh() > skiSlalom.getParticipants().size()) {
            throw new InvalidSkiSlalomException(
                    "Limit for second mansh cannot be greater than participants count."
            );
        }
    }

    private void validateAllResults(SkiSlalom skiSlalom) {
        for (SkiSlalomResultOfParticipant result : skiSlalom.getResults()) {
            validateResult(skiSlalom, result);
        }
    }

    private void validateResult(SkiSlalom skiSlalom, SkiSlalomResultOfParticipant result) {
        if (result == null) {
            throw new InvalidSkiSlalomException("Ski slalom result cannot be null.");
        }

        if (result.getParticipant() == null) {
            throw new InvalidSkiSlalomException("Result participant cannot be null.");
        }

        if (!containsParticipant(skiSlalom, result.getParticipant())) {
            throw new InvalidSkiSlalomException("Participant is not registered in this ski slalom.");
        }

        if (result.getParticipant().getGender() != skiSlalom.getGender()) {
            throw new InvalidSkiSlalomException("Participant gender does not match ski slalom gender.");
        }

        if (result.getParticipant().getAge() < skiSlalom.getMinimumAge()) {
            throw new InvalidSkiSlalomException("Participant is too young for this ski slalom.");
        }
    }

    private boolean containsParticipant(SkiSlalom skiSlalom, Participant participant) {
        for (Participant current : skiSlalom.getParticipants()) {
            if (current.getId() == participant.getId()) {
                return true;
            }
        }

        return false;
    }
}