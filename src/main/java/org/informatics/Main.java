package org.informatics;

import org.informatics.data.Participant;
import org.informatics.data.Gender;
import org.informatics.data.SkiSlalomResultOfParticipant;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Participant p1 = new Participant(123, "Anton", "Bulgaria", Gender.Male, LocalDate.of(2003,3,10));
        SkiSlalomResultOfParticipant skiSlalomResultOfParticipant = new SkiSlalomResultOfParticipant(p1, BigDecimal.valueOf(10), BigDecimal.valueOf(20));
        System.out.println(skiSlalomResultOfParticipant);
    }
}