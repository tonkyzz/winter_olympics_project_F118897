package org.informatics.app;

import org.informatics.app.actions.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConsoleApplication {

    private AppContext context;
    private Map<Integer, MenuAction> actions;

    public ConsoleApplication() {
        this.context = new AppContext();
        this.actions = new LinkedHashMap<>();

        registerActions();
    }

    public void start() {
        boolean running = true;

        while (running) {
            printMenu();

            int choice = context.getConsoleInputService().readInt("Choose option: ");

            if (choice == 0) {
                running = false;
                System.out.println("Application stopped.");
            } else {
                MenuAction action = actions.get(choice);

                if (action == null) {
                    System.out.println("Invalid option.");
                } else {
                    action.execute(context);
                }
            }

            System.out.println();
        }
    }

    private void registerActions() {
        actions.put(1, new NewOlympicsAction());
        actions.put(2, new AddParticipantAction());
        actions.put(3, new CreateSkiSlalomAction());
        actions.put(4, new EnterSkiSlalomFirstManshAction());
        actions.put(5, new EnterSkiSlalomSecondManshAction());
        actions.put(6, new CreateBiatlonAction());
        actions.put(7, new EnterBiatlonResultsAction());
        actions.put(8, new ShowAllResultsAction());
        actions.put(9, new ShowMedalsByCountryAction());
        actions.put(10, new ShowAverageAgeAction());
        actions.put(11, new ShowYoungestMedalistAction());
        actions.put(12, new SaveResultsToFileAction());
        actions.put(13, new SerializeBiatlonAction());
        actions.put(14, new DeserializeBiatlonAction());
    }

    private void printMenu() {
        System.out.println("========== WINTER OLYMPICS ==========");

        for (Integer key : actions.keySet()) {
            System.out.println(key + ". " + actions.get(key).getName());
        }

        System.out.println("0. Exit");
        System.out.println("=====================================");
    }
}