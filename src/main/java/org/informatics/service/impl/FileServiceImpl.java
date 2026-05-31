package org.informatics.service.impl;

import org.informatics.data.*;
import org.informatics.exceptions.FileServiceException;
import org.informatics.service.BiatlonService;
import org.informatics.service.FileService;
import org.informatics.service.SkiSlalomService;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileServiceImpl implements FileService {

    private final SkiSlalomService skiSlalomService;
    private final BiatlonService biatlonService;

    public FileServiceImpl() {
        this.skiSlalomService = new SkiSlalomServiceImpl();
        this.biatlonService = new BiatlonServiceImpl();
    }

    @Override
    public void saveFinalResultsToFile(Olympics olympics, String fileName) {
        validateOlympics(olympics);
        validateFileName(fileName);

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {

            writer.println("Olympics: " + olympics.getName());
            writer.println("======================================");
            writer.println();

            for (Competition competition : olympics.getCompetitions()) {

                if (competition instanceof SkiSlalom) {
                    SkiSlalom skiSlalom = (SkiSlalom) competition;
                    writeSkiSlalomResults(writer, skiSlalom);

                } else if (competition instanceof Biatlon) {
                    Biatlon biatlon = (Biatlon) competition;
                    writeBiatlonResults(writer, biatlon);
                }

                writer.println();
            }

            System.out.println("Final results saved successfully to file: " + fileName);

        } catch (IOException e) {
            throw new FileServiceException(
                    "Error while saving final results to file: " + fileName,
                    e
            );
        }
    }

    private void writeSkiSlalomResults(PrintWriter writer, SkiSlalom skiSlalom) {
        writer.println("Competition: " + skiSlalom.getName());
        writer.println("Type: Ski Slalom");

        List<SkiSlalomResultOfParticipant> finalRanking =
                skiSlalomService.getFinalRanking(skiSlalom);

        if (finalRanking.isEmpty()) {
            writer.println("No final ranking available.");
            return;
        }

        for (int i = 0; i < finalRanking.size(); i++) {
            SkiSlalomResultOfParticipant result = finalRanking.get(i);

            writer.println((i + 1) + ". "
                    + result.getParticipant().getName()
                    + " | Country: " + result.getParticipant().getCountry()
                    + " | First mansh: " + result.getFirstManshTime()
                    + " | Second mansh: " + result.getSecondManshTime()
                    + " | Total: " + result.getTotal()
                    + " sec");
        }
    }

    private void writeBiatlonResults(PrintWriter writer, Biatlon biatlon) {
        writer.println("Competition: " + biatlon.getName());
        writer.println("Type: Biatlon");

        List<BiatlonResultOfParticipant> finalRanking =
                biatlonService.getFinalRanking(biatlon);

        if (finalRanking.isEmpty()) {
            writer.println("No final ranking available.");
            return;
        }

        for (int i = 0; i < finalRanking.size(); i++) {
            BiatlonResultOfParticipant result = finalRanking.get(i);

            writer.println((i + 1) + ". "
                    + result.getParticipant().getName()
                    + " | Country: " + result.getParticipant().getCountry()
                    + " | Ski time: " + result.getSkiTime()
                    + " | Completed laps: " + result.getCompletedLaps()
                    + " | Missed shots: " + result.getMissedShots()
                    + " | Penalty: " + biatlonService.calculatePenalty(biatlon, result)
                    + " | Total: " + biatlonService.calculateTotalTime(biatlon, result)
                    + " sec");
        }
    }

    private void validateOlympics(Olympics olympics) {
        if (olympics == null) {
            throw new FileServiceException("Olympics cannot be null.");
        }

        if (olympics.getCompetitions() == null) {
            throw new FileServiceException("Olympics competitions list cannot be null.");
        }
    }

    private void validateFileName(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new FileServiceException("File name cannot be empty.");
        }
    }
}