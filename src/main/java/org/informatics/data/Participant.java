package org.informatics.data;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Participant {
    private long id;
    private String name;
    private String country;
    private Gender gender;
    private LocalDate dateOfBirth;

    public Participant(long id, String name, String country, Gender gender, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge(){
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
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
