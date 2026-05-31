package org.informatics.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.informatics.exceptions.InvalidParticipantException;

public class Participant implements Serializable {

    private static final long serialVersionUID = 1L;

    private final long id;
    private String name;
    private String country;
    private Gender gender;
    private LocalDate dateOfBirth;

    // Конструктор ---------------------------------------------------------------------------------------------------
    public Participant(long id,
                       String name,
                       String country,
                       Gender gender,
                       LocalDate dateOfBirth)
    {
        validateId(id);
        validateName(name);
        validateCountry(country);
        validateGender(gender);
        validateDateOfBirth(dateOfBirth);

        this.id = id;
        this.name = name;
        this.country = country;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }
    // Getter-и -------------------------------------------------------------------------------------------------------
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    // Setter-и -------------------------------------------------------------------------------------------------------
    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    public void setCountry(String country) {
        validateCountry(country);
        this.country = country;
    }

    public void setGender(Gender gender) {
        validateGender(gender);
        this.gender = gender;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        validateDateOfBirth(dateOfBirth);
        this.dateOfBirth = dateOfBirth;
    }

    // Вземане на възраст ---------------------------------------------------------------------------------------------
    public int getAge(){
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }


    // Проверки -------------------------------------------------------------------------------------------------------
    private void validateId(long id) {
        if (id <= 0) {
            throw new InvalidParticipantException("Participant id must be positive.");
        }
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidParticipantException("Participant name cannot be empty.");
        }
    }

    private void validateCountry(String country) {
        if (country == null || country.isBlank()) {
            throw new InvalidParticipantException("Participant country cannot be empty.");
        }
    }

    private void validateGender(Gender gender) {
        if (gender == null) {
            throw new InvalidParticipantException("Participant gender cannot be null.");
        }
    }

    private void validateDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            throw new InvalidParticipantException("Date of birth cannot be null.");
        }

        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new InvalidParticipantException("Date of birth cannot be in the future.");
        }
    }


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return "Participant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", gender=" + gender +
                ", dateOfBirth=" + dateOfBirth.format(formatter) +
                '}';
    }
}
