package org.informatics.service.impl;

import org.informatics.data.Biatlon;
import org.informatics.data.BiatlonResultOfParticipant;
import org.informatics.service.BiatlonService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BiatlonServiceImpl implements BiatlonService {

    @Override
    public BigDecimal calculatePenalty(Biatlon biatlon, BiatlonResultOfParticipant result) {
        return biatlon.getPenaltyForOneMiss()
                .multiply(BigDecimal.valueOf(result.getMissedShots()));
    }

    @Override
    public BigDecimal calculateTotalTime(Biatlon biatlon, BiatlonResultOfParticipant result) {
        if (!result.hasFinished(biatlon.getLapsCount())) {
            throw new IllegalStateException("Participant has not completed all laps.");
        }

        return result.getSkiTime().add(calculatePenalty(biatlon, result));
    }

    @Override
    public List<BiatlonResultOfParticipant> getFinishedParticipants(Biatlon biatlon) {
        List<BiatlonResultOfParticipant> finishedParticipants = new ArrayList<>();

        for (BiatlonResultOfParticipant result : biatlon.getResults()) {
            if (result.hasFinished(biatlon.getLapsCount())) {
                finishedParticipants.add(result);
            }
        }

        return finishedParticipants;
    }

    @Override
    public List<BiatlonResultOfParticipant> getFinalRanking(Biatlon biatlon) {
        List<BiatlonResultOfParticipant> ranking = getFinishedParticipants(biatlon);

        Collections.sort(ranking, new TotalTimeComparator(biatlon));

        return ranking;
    }

    @Override
    public void printFinalResults(Biatlon biatlon) {
        List<BiatlonResultOfParticipant> finalRanking = getFinalRanking(biatlon);

        System.out.println("Final results for " + biatlon.getName() + ":");

        for (int i = 0; i < finalRanking.size(); i++) {
            BiatlonResultOfParticipant result = finalRanking.get(i);

            System.out.println((i + 1) + ". "
                    + result.getParticipant().getName()
                    + " | Ski time: " + result.getSkiTime()
                    + " | Completed laps: " + result.getCompletedLaps()
                    + " | Missed shots: " + result.getMissedShots()
                    + " | Penalty: " + calculatePenalty(biatlon, result)
                    + " | Total: " + calculateTotalTime(biatlon, result)
                    + " sec");
        }
    }

    private class TotalTimeComparator implements Comparator<BiatlonResultOfParticipant> {

        private Biatlon biatlon;

        public TotalTimeComparator(Biatlon biatlon) {
            this.biatlon = biatlon;
        }

        @Override
        public int compare(BiatlonResultOfParticipant r1, BiatlonResultOfParticipant r2) {
            return calculateTotalTime(biatlon, r1)
                    .compareTo(calculateTotalTime(biatlon, r2));
        }
    }
}
