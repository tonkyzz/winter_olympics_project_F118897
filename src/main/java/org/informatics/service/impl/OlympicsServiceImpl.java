package org.informatics.service.impl;

import org.informatics.data.*;
import org.informatics.service.BiatlonService;
import org.informatics.service.OlympicsService;
import org.informatics.service.SkiSlalomService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OlympicsServiceImpl implements OlympicsService {

    private SkiSlalomService skiSlalomService;
    private BiatlonService biatlonService;

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
        System.out.println("Olympics: " + olympics.getName());
        System.out.println();

        for (Competition competition : olympics.getCompetitions()) {

            if (competition instanceof SkiSlalom) {
                SkiSlalom skiSlalom = (SkiSlalom) competition;
                skiSlalomService.printFinalResults(skiSlalom);
                System.out.println();
            }

            if (competition instanceof Biatlon) {
                Biatlon biatlon = (Biatlon) competition;
                biatlonService.printFinalResults(biatlon);
                System.out.println();
            }
        }
    }

    @Override
    public List<OlympicMedal> getAllMedalists(Olympics olympics) {
        List<OlympicMedal> medalists = new ArrayList<>();

        for (Competition competition : olympics.getCompetitions()) {

            if (competition instanceof SkiSlalom) {
                SkiSlalom skiSlalom = (SkiSlalom) competition;
                List<SkiSlalomResultOfParticipant> ranking =
                        skiSlalomService.getFinalRanking(skiSlalom);

                addMedalistsFromSkiSlalom(medalists, skiSlalom, ranking);
            }

            if (competition instanceof Biatlon) {
                Biatlon biatlon = (Biatlon) competition;
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

        return null;
    }

    @Override
    public Map<String, Integer> getMedalsByCountry(Olympics olympics) {
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
        Map<String, Integer> medalsByCountry = getMedalsByCountry(olympics);

        System.out.println("Medals by country:");

        for (String country : medalsByCountry.keySet()) {
            System.out.println(country + " - " + medalsByCountry.get(country));
        }
    }

    @Override
    public double getAverageAgeOfParticipants(Olympics olympics) {
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
        List<OlympicMedal> medals = getAllMedalists(olympics);

        if (medals.isEmpty()) {
            return null;
        }

        Participant youngest = medals.get(0).getParticipant();

        for (OlympicMedal medal : medals) {
            Participant participant = medal.getParticipant();

            if (participant.getAge() < youngest.getAge()) {
                youngest = participant;
            }
        }

        return youngest;
    }

    private List<Participant> getAllUniqueParticipants(Olympics olympics) {
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
}