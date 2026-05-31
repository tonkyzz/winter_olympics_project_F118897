package org.informatics.app.actions;

import org.informatics.app.AppContext;
import org.informatics.app.MenuAction;

public class ShowAverageAgeAction implements MenuAction {

    @Override
    public String getName() {
        return "Show average age of participants";
    }

    @Override
    public void execute(AppContext context) {
        if (!ActionUtils.checkOlympicsCreated(context)) {
            return;
        }

        double averageAge = context.getOlympicsService()
                .getAverageAgeOfParticipants(context.getOlympics());

        System.out.println("Average age of participants: " + averageAge);
    }
}