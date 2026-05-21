package org.informatics;

import org.informatics.data.Participant;
import org.informatics.data.Gender;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Participant p1 = new Participant(123, "Anton", "Bulgaria", Gender.Male, LocalDate.of(2003,3,10));
        System.out.println(p1);
    }
}