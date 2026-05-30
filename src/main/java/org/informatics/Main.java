package org.informatics;

import org.informatics.data.*;
import org.informatics.service.BiatlonService;
import org.informatics.service.BiatlonServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Participant p1 = new Participant(1, "Anton", "Bulgaria", Gender.Male, LocalDate.of(2003, 3, 10));
        Participant p2 = new Participant(2, "Ivan", "Bulgaria", Gender.Male, LocalDate.of(2001, 5, 20));
        Participant p3 = new Participant(3, "Georgi", "Germany", Gender.Male, LocalDate.of(2000, 1, 15));

        List<Participant> participants = new ArrayList<>();
        participants.add(p1);
        participants.add(p2);
        participants.add(p3);

        Biatlon biatlon = new Biatlon(
                "Men Biatlon",
                Gender.Male,
                18,
                participants,
                4,
                2,
                new BigDecimal("60.000")
        );

        BiatlonResultOfParticipant r1 =
                new BiatlonResultOfParticipant(p1, new BigDecimal("1500.000"), 4, 2);

        BiatlonResultOfParticipant r2 =
                new BiatlonResultOfParticipant(p2, new BigDecimal("1480.000"), 4, 1);

        BiatlonResultOfParticipant r3 =
                new BiatlonResultOfParticipant(p3, new BigDecimal("1400.000"), 3, 0);

        biatlon.addResult(r1);
        biatlon.addResult(r2);
        biatlon.addResult(r3);

        BiatlonService biatlonService = new BiatlonServiceImpl();

        biatlonService.printFinalResults(biatlon);
    }
}