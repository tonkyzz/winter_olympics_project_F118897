package org.informatics.app.actions;

import org.informatics.app.AppContext;
import org.informatics.data.Biatlon;
import org.informatics.data.Competition;
import org.informatics.data.SkiSlalom;

import java.util.ArrayList;
import java.util.List;

public class ActionUtils {

    public static boolean checkOlympicsCreated(AppContext context) {
        if (!context.hasOlympics()) {
            System.out.println("Create Olympics first.");
            return false;
        }

        return true;
    }

    public static SkiSlalom chooseSkiSlalom(AppContext context) {
        if (!checkOlympicsCreated(context)) {
            return null;
        }

        List<SkiSlalom> skiSlaloms = new ArrayList<>();

        for (Competition competition : context.getOlympics().getCompetitions()) {
            if (competition instanceof SkiSlalom) {
                skiSlaloms.add((SkiSlalom) competition);
            }
        }

        if (skiSlaloms.isEmpty()) {
            System.out.println("No Ski Slalom competitions available.");
            return null;
        }

        System.out.println("Choose Ski Slalom:");

        for (int i = 0; i < skiSlaloms.size(); i++) {
            System.out.println((i + 1) + ". " + skiSlaloms.get(i).getName());
        }

        int index = context.getConsoleInputService()
                .readInt("Choose number: ") - 1;

        if (index < 0 || index >= skiSlaloms.size()) {
            System.out.println("Invalid choice.");
            return null;
        }

        return skiSlaloms.get(index);
    }

    public static Biatlon chooseBiatlon(AppContext context) {
        if (!checkOlympicsCreated(context)) {
            return null;
        }

        List<Biatlon> biatlons = new ArrayList<>();

        for (Competition competition : context.getOlympics().getCompetitions()) {
            if (competition instanceof Biatlon) {
                biatlons.add((Biatlon) competition);
            }
        }

        if (biatlons.isEmpty()) {
            System.out.println("No Biatlon competitions available.");
            return null;
        }

        System.out.println("Choose Biatlon:");

        for (int i = 0; i < biatlons.size(); i++) {
            System.out.println((i + 1) + ". " + biatlons.get(i).getName());
        }

        int index = context.getConsoleInputService()
                .readInt("Choose number: ") - 1;

        if (index < 0 || index >= biatlons.size()) {
            System.out.println("Invalid choice.");
            return null;
        }

        return biatlons.get(index);
    }
}