package org.informatics.service;

import java.util.List;

public interface ResultProcessingService<C, R> {

    List<R> getFinalRanking(C competition);

    void printFinalResults(C competition);
}