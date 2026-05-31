package org.informatics.service;

import org.informatics.data.SkiSlalom;
import org.informatics.data.SkiSlalomResultOfParticipant;

import java.util.List;

public interface SkiSlalomService extends ResultProcessingService<SkiSlalom, SkiSlalomResultOfParticipant> {

    List<SkiSlalomResultOfParticipant> getFirstManshRanking(SkiSlalom skiSlalom);

    List<SkiSlalomResultOfParticipant> getQualifiedForSecondMansh(SkiSlalom skiSlalom);

    List<SkiSlalomResultOfParticipant> getSecondManshRanking(SkiSlalom skiSlalom);

    void printFirstManshRanking(SkiSlalom skiSlalom);

    void printQualifiedForSecondMansh(SkiSlalom skiSlalom);
}
