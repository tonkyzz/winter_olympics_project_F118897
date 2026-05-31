package org.informatics.app.actions;

import org.informatics.app.AppContext;
import org.informatics.app.MenuAction;
import org.informatics.data.Gender;
import org.informatics.data.SkiSlalom;

public class CreateSkiSlalomAction implements MenuAction {

    @Override
    public String getName() {
        return "Create Ski Slalom";
    }

    @Override
    public void execute(AppContext context) {
        if (!context.hasOlympics()) {
            System.out.println("Create Olympics first.");
            return;
        }

        if (context.getParticipants().isEmpty()) {
            System.out.println("Add participants first.");
            return;
        }

        String name = context.getConsoleInputService()
                .readString("Enter Ski Slalom name: ");

        Gender gender = context.getConsoleInputService()
                .readGender("Enter gender Male/Female:");

        int minimumAge = context.getConsoleInputService()
                .readInt("Enter minimum age: ");

        int limitForSecondMansh = context.getConsoleInputService()
                .readInt("Enter limit for second mansh: ");

        SkiSlalom skiSlalom = context.getOlympicsService().createSkiSlalom(
                context.getOlympics(),
                name,
                gender,
                minimumAge,
                context.getParticipants(),
                limitForSecondMansh
        );

        System.out.println("Ski Slalom created: " + skiSlalom.getName());
    }
}