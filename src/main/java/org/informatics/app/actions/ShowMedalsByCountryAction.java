package org.informatics.app.actions;

import org.informatics.app.AppContext;
import org.informatics.app.MenuAction;

public class ShowMedalsByCountryAction implements MenuAction {

    @Override
    public String getName() {
        return "Show medals by country";
    }

    @Override
    public void execute(AppContext context) {
        if (!ActionUtils.checkOlympicsCreated(context)) {
            return;
        }

        context.getOlympicsService().printMedalsByCountry(context.getOlympics());
    }
}