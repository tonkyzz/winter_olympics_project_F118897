package org.informatics.service.impl;

import org.informatics.data.*;
import org.informatics.exceptions.InvalidOlympicsException;
import org.informatics.service.BiatlonService;
import org.informatics.service.OlympicsService;
import org.informatics.service.SkiSlalomService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OlympicsServiceImpl implements OlympicsService {

    private final SkiSlalomService skiSlalomService;
    private final BiatlonService biatlonService;

    public OlympicsServiceImpl() {
        this.skiSlalomService = new SkiSlalomServiceImpl();
        this.biatlonService = new BiatlonServiceImpl();
    }

    @Override
    public SkiSlalom createSkiSlalom(Olympics olympics,
                                     String name,
                                     Gender gender,
                                     int minimumAge,
                                     List<Participant> participants,
                                     int limitForSecondMansh) {

        validateOlympics(olympics);
        validateCompetitionInput(name, gender, minimumAge, participants);

        if (limitForSecondMansh <= 0) {
            throw new InvalidOlympicsException("Limit for second mansh must be greater than 0.");
        }

        if (limitForSecondMansh > participants.size()) {
            throw new InvalidOlympicsException(
                    "Limit for second mansh cannot be greater than participants count."
            );
        }

        SkiSlalom skiSlalom = new SkiSlalom(
                name,
                gender,
                minimumAge,
                participants,
                limitForSecondMansh
        );

        olympics.addCompetition(skiSlalom);

        return skiSlalom;
    }

    @Override
    public Biatlon createBiatlon(Olympics olympics,
                                 String name,
                                 Gender gender,
                                 int minimumAge,
                                 List<Participant> participants,
                                 int lapsCount,
                                 int shootingsCount,
                                 BigDecimal penaltyForOneMiss) {

        validateOlympics(olympics);
        validateCompetitionInput(name, gender, minimumAge, participants);

        if (lapsCount <= 0) {
            throw new InvalidOlympicsException("Laps count must be greater than 0.");
        }

        if (shootingsCount <= 0) {
            throw new InvalidOlympicsException("Shootings count must be greater than 0.");
        }

        if (penaltyForOneMiss == null) {
            throw new InvalidOlympicsException("Penalty for one miss cannot be null.");
        }

        if (penaltyForOneMiss.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidOlympicsException("Penalty for one miss must be greater than 0.");
        }

        Biatlon biatlon = new Biatlon(
                name,
                gender,
                minimumAge,
                participants,
                lapsCount,
                shootingsCount,
                penaltyForOneMiss
        );

        olympics.addCompetition(biatlon);

        return biatlon;
    }

    @Override
    public void printAllFinalResults(Olympics olympics) {
        validateOlympics(olympics);

        System.out.println("Olympics: " + olympics.getName());
        System.out.println();

        for (Competition competition : olympics.getCompetitions()) {

            if (competition instanceof SkiSlalom skiSlalom) {
                skiSlalomService.printFinalResults(skiSlalom);
                System.out.println();

            } else if (competition instanceof Biatlon biatlon) {
                biatlonService.printFinalResults(biatlon);
                System.out.println();
            }
        }
    }

    @Override
    public List<OlympicMedal> getAllMedalists(Olympics olympics) {
        validateOlympics(olympics);

        List<OlympicMedal> medalists = new ArrayList<>();

        for (Competition competition : olympics.getCompetitions()) {

            if (competition instanceof SkiSlalom skiSlalom) {

                List<SkiSlalomResultOfParticipant> ranking =
                        skiSlalomService.getFinalRanking(skiSlalom);

                addMedalistsFromSkiSlalom(medalists, skiSlalom, ranking);

            } else if (competition instanceof Biatlon biatlon) {

                List<BiatlonResultOfParticipant> ranking =
                        biatlonService.getFinalRanking(biatlon);

                addMedalistsFromBiatlon(medalists, biatlon, ranking);
            }
        }

        return medalists;
    }

    private void addMedalistsFromSkiSlalom(List<OlympicMedal> medalists,
                                           SkiSlalom skiSlalom,
                                           List<SkiSlalomResultOfParticipant> ranking) {

        if (ranking == null) {
            throw new InvalidOlympicsException("Ski slalom ranking cannot be null.");
        }

        int count = Math.min(3, ranking.size());

        for (int i = 0; i < count; i++) {
            SkiSlalomResultOfParticipant result = ranking.get(i);

            OlympicMedal medal = new OlympicMedal(
                    result.getParticipant(),
                    skiSlalom.getName(),
                    i + 1,
                    getMedalType(i + 1)
            );

            medalists.add(medal);
        }
    }

    private void addMedalistsFromBiatlon(List<OlympicMedal> medalists,
                                         Biatlon biatlon,
                                         List<BiatlonResultOfParticipant> ranking) {

        if (ranking == null) {
            throw new InvalidOlympicsException("Biatlon ranking cannot be null.");
        }

        int count = Math.min(3, ranking.size());

        for (int i = 0; i < count; i++) {
            BiatlonResultOfParticipant result = ranking.get(i);

            OlympicMedal medal = new OlympicMedal(
                    result.getParticipant(),
                    biatlon.getName(),
                    i + 1,
                    getMedalType(i + 1)
            );

            medalists.add(medal);
        }
    }

    private MedalType getMedalType(int place) {
        if (place == 1) {
            return MedalType.GOLD;
        }

        if (place == 2) {
            return MedalType.SILVER;
        }

        if (place == 3) {
            return MedalType.BRONZE;
        }

        throw new InvalidOlympicsException("Invalid medal place.");
    }

    @Override
    public Map<String, Integer> getMedalsByCountry(Olympics olympics) {
        validateOlympics(olympics);

        Map<String, Integer> medalsByCountry = new HashMap<>();
        List<OlympicMedal> medalists = getAllMedalists(olympics);

        for (OlympicMedal medal : medalists) {
            String country = medal.getParticipant().getCountry();

            if (medalsByCountry.containsKey(country)) {
                int currentCount = medalsByCountry.get(country);
                medalsByCountry.put(country, currentCount + 1);
            } else {
                medalsByCountry.put(country, 1);
            }
        }

        return medalsByCountry;
    }

    @Override
    public void printMedalsByCountry(Olympics olympics) {
        validateOlympics(olympics);

        Map<String, Integer> medalsByCountry = getMedalsByCountry(olympics);

        System.out.println("Medals by country:");

        if (medalsByCountry.isEmpty()) {
            System.out.println("No medals yet.");
            return;
        }

        for (String country : medalsByCountry.keySet()) {
            System.out.println(country + " - " + medalsByCountry.get(country));
        }
    }

    @Override
    public double getAverageAgeOfParticipants(Olympics olympics) {
        validateOlympics(olympics);

        List<Participant> participants = getAllUniqueParticipants(olympics);

        if (participants.isEmpty()) {
            return 0;
        }

        int sum = 0;

        for (Participant participant : participants) {
            sum = sum + participant.getAge();
        }

        return (double) sum / participants.size();
    }

    @Override
    public Participant getYoungestMedalist(Olympics olympics) {
        validateOlympics(olympics);

        List<OlympicMedal> medals = getAllMedalists(olympics);

        if (medals.isEmpty()) {
            return null;
        }

        Participant youngest = medals.getFirst().getParticipant();

        for (OlympicMedal medal : medals) {
            Participant participant = medal.getParticipant();

            if (participant.getAge() < youngest.getAge()) {
                youngest = participant;
            }
        }

        return youngest;
    }

    private List<Participant> getAllUniqueParticipants(Olympics olympics) {
        validateOlympics(olympics);

        List<Participant> uniqueParticipants = new ArrayList<>();

        for (Competition competition : olympics.getCompetitions()) {
            List<Participant> participants = competition.getParticipants();

            for (Participant participant : participants) {
                if (!containsParticipant(uniqueParticipants, participant)) {
                    uniqueParticipants.add(participant);
                }
            }
        }

        return uniqueParticipants;
    }

    private boolean containsParticipant(List<Participant> participants, Participant participant) {
        for (Participant current : participants) {
            if (current.getId() == participant.getId()) {
                return true;
            }
        }

        return false;
    }

    private void validateOlympics(Olympics olympics) {
        if (olympics == null) {
            throw new InvalidOlympicsException("Olympics cannot be null.");
        }

        if (olympics.getName() == null || olympics.getName().isBlank()) {
            throw new InvalidOlympicsException("Olympics name cannot be empty.");
        }

        if (olympics.getCompetitions() == null) {
            throw new InvalidOlympicsException("Olympics competitions list cannot be null.");
        }
    }

    private void validateCompetitionInput(String name,
                                          Gender gender,
                                          int minimumAge,
                                          List<Participant> participants) {

        if (name == null || name.isBlank()) {
            throw new InvalidOlympicsException("Competition name cannot be empty.");
        }

        if (gender == null) {
            throw new InvalidOlympicsException("Competition gender cannot be null.");
        }

        if (minimumAge <= 0) {
            throw new InvalidOlympicsException("Minimum age must be greater than 0.");
        }

        if (participants == null || participants.isEmpty()) {
            throw new InvalidOlympicsException("Competition must have participants.");
        }
    }
}