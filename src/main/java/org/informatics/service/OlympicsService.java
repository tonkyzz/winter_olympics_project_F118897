package org.informatics.service;

import org.informatics.data.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OlympicsService {

    SkiSlalom createSkiSlalom(Olympics olympics,
                              String name,
                              Gender gender,
                              int minimumAge,
                              List<Participant> participants,
                              int limitForSecondMansh);

    Biatlon createBiatlon(Olympics olympics,
                          String name,
                          Gender gender,
                          int minimumAge,
                          List<Participant> participants,
                          int lapsCount,
                          int shootingsCount,
                          BigDecimal penaltyForOneMiss);

    List<OlympicMedal> getAllMedalists(Olympics olympics);

    Map<String, Integer> getMedalsByCountry(Olympics olympics);

    double getAverageAgeOfParticipants(Olympics olympics);

    Participant getYoungestMedalist(Olympics olympics);

    void printAllFinalResults(Olympics olympics);

    void printMedalsByCountry(Olympics olympics);
}