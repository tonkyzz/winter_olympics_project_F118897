package org.informatics.service.impl;

import org.informatics.data.Gender;
import org.informatics.data.Participant;
import org.informatics.service.ConsoleInputService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class ConsoleInputServiceImpl implements ConsoleInputService {

    private Scanner scanner;

    public ConsoleInputServiceImpl() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String readString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    @Override
    public int readInt(String message) {
        System.out.print(message);
        return Integer.parseInt(scanner.nextLine());
    }

    @Override
    public BigDecimal readBigDecimal(String message) {
        System.out.print(message);

        String input = scanner.nextLine();

        // Позволява и 55.321, и 55,321
        input = input.replace(",", ".");

        return new BigDecimal(input);
    }

    @Override
    public LocalDate readDate(String message) {
        System.out.print(message + " YYYY-MM-DD: ");
        String input = scanner.nextLine();

        return LocalDate.parse(input);
    }

    @Override
    public Gender readGender(String message) {
        System.out.print(message + " ");

        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("male") || input.equalsIgnoreCase("m")) {
            return Gender.Male;
        }

        if (input.equalsIgnoreCase("female") || input.equalsIgnoreCase("f")) {
            return Gender.Female;
        }

        throw new IllegalArgumentException("Invalid gender.");
    }

    @Override
    public Participant readParticipant() {
        int id = readInt("Enter participant id: ");
        String name = readString("Enter participant name: ");
        String country = readString("Enter participant country: ");
        Gender gender = readGender("Enter participant gender Male/Female:");
        LocalDate dateOfBirth = readDate("Enter date of birth");

        return new Participant(id, name, country, gender, dateOfBirth);
    }
}