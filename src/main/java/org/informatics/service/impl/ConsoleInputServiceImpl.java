package org.informatics.service.impl;

import org.informatics.data.Gender;
import org.informatics.data.Participant;
import org.informatics.exceptions.InvalidConsoleInputException;
import org.informatics.service.ConsoleInputService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleInputServiceImpl implements ConsoleInputService {

    private final Scanner scanner;

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

        String input = scanner.nextLine();

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidConsoleInputException("Invalid number format. Please enter an integer.");
        }
    }

    @Override
    public BigDecimal readBigDecimal(String message) {
        System.out.print(message);

        String input = scanner.nextLine();
        input = input.replace(",", ".");

        try {
            return new BigDecimal(input);
        } catch (NumberFormatException e) {
            throw new InvalidConsoleInputException("Invalid decimal number format.");
        }
    }

    @Override
    public LocalDate readDate(String message) {
        System.out.print(message + " YYYY-MM-DD: ");

        String input = scanner.nextLine();

        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            throw new InvalidConsoleInputException("Invalid date format. Use YYYY-MM-DD.");
        }
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

        throw new InvalidConsoleInputException("Invalid gender. Use Male/Female.");
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