package org.informatics.service.impl;

import org.informatics.data.Biatlon;
import org.informatics.data.BiatlonResultOfParticipant;
import org.informatics.data.Participant;
import org.informatics.exceptions.InvalidBiatlonException;
import org.informatics.service.BiatlonService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BiatlonServiceImpl implements BiatlonService {

    @Override
    public BigDecimal calculatePenalty(Biatlon biatlon, BiatlonResultOfParticipant result) {
        validateBiatlon(biatlon);
        validateResultForBiatlon(biatlon, result);

        return biatlon.getPenaltyForOneMiss()
                .multiply(BigDecimal.valueOf(result.getMissedShots()));
    }

    @Override
    public BigDecimal calculateTotalTime(Biatlon biatlon, BiatlonResultOfParticipant result) {
        validateBiatlon(biatlon);
        validateResultForBiatlon(biatlon, result);

        BigDecimal skiTime = result.getSkiTime();

        if (skiTime == null) {
            throw new InvalidBiatlonException("Ski time cannot be null when calculating total time.");
        }

        if (!result.hasFinished(biatlon.getLapsCount())) {
            throw new InvalidBiatlonException("Participant has not completed all laps.");
        }

        return skiTime.add(calculatePenalty(biatlon, result));
    }

    @Override
    public List<BiatlonResultOfParticipant> getFinishedParticipants(Biatlon biatlon) {
        validateBiatlon(biatlon);

        List<BiatlonResultOfParticipant> finishedParticipants = new ArrayList<>();

        for (BiatlonResultOfParticipant result : biatlon.getResults()) {
            validateResultForBiatlon(biatlon, result);

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

        if (finalRanking.isEmpty()) {
            System.out.println("No finished participants.");
            return;
        }

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

    private void validateBiatlon(Biatlon biatlon) {
        if (biatlon == null) {
            throw new InvalidBiatlonException("Biatlon cannot be null.");
        }

        if (biatlon.getParticipants() == null || biatlon.getParticipants().isEmpty()) {
            throw new InvalidBiatlonException("Biatlon must have participants.");
        }

        if (biatlon.getResults() == null) {
            throw new InvalidBiatlonException("Biatlon results cannot be null.");
        }

        if (biatlon.getLapsCount() <= 0) {
            throw new InvalidBiatlonException("Laps count must be greater than 0.");
        }

        if (biatlon.getShootingsCount() <= 0) {
            throw new InvalidBiatlonException("Shootings count must be greater than 0.");
        }

        if (biatlon.getPenaltyForOneMiss() == null) {
            throw new InvalidBiatlonException("Penalty for one miss cannot be null.");
        }

        if (biatlon.getPenaltyForOneMiss().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidBiatlonException("Penalty for one miss must be greater than 0.");
        }
    }

    private void validateResultForBiatlon(Biatlon biatlon, BiatlonResultOfParticipant result) {
        if (result == null) {
            throw new InvalidBiatlonException("Biatlon result cannot be null.");
        }

        if (result.getParticipant() == null) {
            throw new InvalidBiatlonException("Result participant cannot be null.");
        }

        if (!containsParticipant(biatlon, result.getParticipant())) {
            throw new InvalidBiatlonException("Participant is not registered in this biatlon.");
        }

        if (result.getParticipant().getGender() != biatlon.getGender()) {
            throw new InvalidBiatlonException("Participant gender does not match biatlon gender.");
        }

        if (result.getParticipant().getAge() < biatlon.getMinimumAge()) {
            throw new InvalidBiatlonException("Participant is too young for this biatlon.");
        }

        if (result.getCompletedLaps() < 0) {
            throw new InvalidBiatlonException("Completed laps cannot be negative.");
        }

        if (result.getCompletedLaps() > biatlon.getLapsCount()) {
            throw new InvalidBiatlonException("Completed laps cannot be more than required laps.");
        }

        if (result.getMissedShots() < 0) {
            throw new InvalidBiatlonException("Missed shots cannot be negative.");
        }
    }

    private boolean containsParticipant(Biatlon biatlon, Participant participant) {
        for (Participant current : biatlon.getParticipants()) {
            if (current.getId() == participant.getId()) {
                return true;
            }
        }

        return false;
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