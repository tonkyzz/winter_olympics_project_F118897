package org.informatics.service;

import org.informatics.data.Gender;
import org.informatics.data.Participant;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ConsoleInputService {

    String readString(String message);

    int readInt(String message);

    BigDecimal readBigDecimal(String message);

    LocalDate readDate(String message);

    Gender readGender(String message);

    Participant readParticipant();
}