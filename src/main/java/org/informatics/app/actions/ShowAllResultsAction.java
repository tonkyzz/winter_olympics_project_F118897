package org.informatics.app.actions;

import org.informatics.app.AppContext;
import org.informatics.app.MenuAction;

public class ShowAllResultsAction implements MenuAction {

    @Override
    public String getName() {
        return "Show all final results";
    }

    @Override
    public void execute(AppContext context) {
        if (!ActionUtils.checkOlympicsCreated(context)) {
            return;
        }

        context.getOlympicsService().printAllFinalResults(context.getOlympics());
    }
}