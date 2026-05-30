package org.informatics.service;

import org.informatics.data.Biatlon;
import org.informatics.data.BiatlonResultOfParticipant;

import java.math.BigDecimal;
import java.util.List;

public interface BiatlonService
        extends ResultProcessingService<Biatlon, BiatlonResultOfParticipant> {

    BigDecimal calculatePenalty(Biatlon biatlon, BiatlonResultOfParticipant result);

    BigDecimal calculateTotalTime(Biatlon biatlon, BiatlonResultOfParticipant result);

    List<BiatlonResultOfParticipant> getFinishedParticipants(Biatlon biatlon);
}