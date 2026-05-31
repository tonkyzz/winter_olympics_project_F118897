package org.informatics.app.actions;

import org.informatics.app.AppContext;
import org.informatics.app.MenuAction;
import org.informatics.data.Participant;

public class ShowYoungestMedalistAction implements MenuAction {

    @Override
    public String getName() {
        return "Show youngest medalist";
    }

    @Override
    public void execute(AppContext context) {
        if (!ActionUtils.checkOlympicsCreated(context)) {
            return;
        }

        Participant youngest = context.getOlympicsService()
                .getYoungestMedalist(context.getOlympics());

        if (youngest == null) {
            System.out.println("No medalists yet.");
        } else {
            System.out.println("Youngest medalist: "
                    + youngest.getName()
                    + " from "
                    + youngest.getCountry());
        }
    }
}