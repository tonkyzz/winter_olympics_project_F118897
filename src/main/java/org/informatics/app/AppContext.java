package org.informatics.app;

import org.informatics.data.Olympics;
import org.informatics.data.Participant;
import org.informatics.exceptions.InvalidApplicationStateException;
import org.informatics.service.*;
import org.informatics.service.impl.*;

import java.util.ArrayList;
import java.util.List;

public class AppContext {

    private Olympics olympics;
    private final List<Participant> participants;

    private final OlympicsService olympicsService;
    private final SkiSlalomService skiSlalomService;
    private final BiatlonService biatlonService;
    private final FileService fileService;
    private final BiatlonSerializationService biatlonSerializationService;
    private final ConsoleInputService consoleInputService;

    public AppContext() {
        this.participants = new ArrayList<>();

        this.olympicsService = new OlympicsServiceImpl();
        this.skiSlalomService = new SkiSlalomServiceImpl();
        this.biatlonService = new BiatlonServiceImpl();
        this.fileService = new FileServiceImpl();
        this.biatlonSerializationService = new BiatlonSerializationServiceImpl();
        this.consoleInputService = new ConsoleInputServiceImpl();
    }

    public Olympics getOlympics() {
        return olympics;
    }

    public void setOlympics(Olympics olympics) {
        if (olympics == null) {
            throw new InvalidApplicationStateException("Olympics cannot be null.");
        }

        this.olympics = olympics;
    }

    public boolean hasOlympics() {
        return olympics != null;
    }

    public void addParticipant(Participant participant) {
        if (participant == null) {
            throw new InvalidApplicationStateException("Participant cannot be null.");
        }

        participants.add(participant);
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public OlympicsService getOlympicsService() {
        return olympicsService;
    }

    public SkiSlalomService getSkiSlalomService() {
        return skiSlalomService;
    }

    public BiatlonService getBiatlonService() {
        return biatlonService;
    }

    public FileService getFileService() {
        return fileService;
    }

    public BiatlonSerializationService getBiatlonSerializationService() {
        return biatlonSerializationService;
    }

    public ConsoleInputService getConsoleInputService() {
        return consoleInputService;
    }
}