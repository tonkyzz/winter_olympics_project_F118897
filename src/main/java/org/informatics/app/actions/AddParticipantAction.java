package org.informatics.app.actions;

import org.informatics.app.AppContext;
import org.informatics.app.MenuAction;
import org.informatics.data.Participant;

public class AddParticipantAction implements MenuAction {

    @Override
    public String getName() {
        return "Add participant";
    }

    @Override
    public void execute(AppContext context) {
        if (!context.hasOlympics()) {
            System.out.println("Create Olympics first.");
            return;
        }

        Participant participant = context.getConsoleInputService().readParticipant();

        context.getParticipants().add(participant);

        System.out.println("Participant added: " + participant.getId() + participant.getName());
    }
}
