package org.informatics.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

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
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    // Вземане на възраст ---------------------------------------------------------------------------------------------
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
