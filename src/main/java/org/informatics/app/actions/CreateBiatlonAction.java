package org.informatics.app.actions;

import org.informatics.app.AppContext;
import org.informatics.app.MenuAction;
import org.informatics.data.Biatlon;
import org.informatics.data.Gender;

import java.math.BigDecimal;

public class CreateBiatlonAction implements MenuAction {

    @Override
    public String getName() {
        return "Create Biatlon";
    }

    @Override
    public void execute(AppContext context) {
        if (!ActionUtils.checkOlympicsCreated(context)) {
            return;
        }

        if (context.getParticipants().isEmpty()) {
            System.out.println("Add participants first.");
            return;
        }

        String name = context.getConsoleInputService()
                .readString("Enter Biatlon name: ");

        Gender gender = context.getConsoleInputService()
                .readGender("Enter gender Male/Female: ");

        int minimumAge = context.getConsoleInputService()
                .readInt("Enter minimum age: ");

        int lapsCount = context.getConsoleInputService()
                .readInt("Enter laps count: ");

        int shootingsCount = context.getConsoleInputService()
                .readInt("Enter shootings count: ");

        BigDecimal penalty = context.getConsoleInputService()
                .readBigDecimal("Enter penalty for one missed shot in seconds: ");

        Biatlon biatlon = context.getOlympicsService().createBiatlon(
                context.getOlympics(),
                name,
                gender,
                minimumAge,
                context.getParticipants(),
                lapsCount,
                shootingsCount,
                penalty
        );

        System.out.println("Biatlon created: " + biatlon.getName());
    }
}