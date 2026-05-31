package org.informatics.app.actions;

import org.informatics.app.AppContext;
import org.informatics.app.MenuAction;
import org.informatics.data.Olympics;

public class NewOlympicsAction implements MenuAction {

    @Override
    public String getName() {
        return "Create new Olympics";
    }

    @Override
    public void execute(AppContext context) {
        String name = context.getConsoleInputService()
                .readString("Enter Olympics name: ");

        Olympics olympics = new Olympics(name);

        context.setOlympics(olympics);

        System.out.println("New Olympics created: " + name);
    }
}
