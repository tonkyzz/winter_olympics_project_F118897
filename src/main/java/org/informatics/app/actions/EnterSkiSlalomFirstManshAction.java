package org.informatics.app.actions;

import org.informatics.app.AppContext;
import org.informatics.app.MenuAction;
import org.informatics.data.Participant;
import org.informatics.data.SkiSlalom;
import org.informatics.data.SkiSlalomResultOfParticipant;

import java.math.BigDecimal;

public class EnterSkiSlalomFirstManshAction implements MenuAction {

    @Override
    public String getName() {
        return "Enter Ski Slalom first mansh times";
    }

    @Override
    public void execute(AppContext context) {
        SkiSlalom skiSlalom = ActionUtils.chooseSkiSlalom(context);

        if (skiSlalom == null) {
            return;
        }

        for (Participant participant : skiSlalom.getParticipants()) {
            BigDecimal time = context.getConsoleInputService()
                    .readBigDecimal("Enter first mansh time for " + participant.getName() + ": ");

            SkiSlalomResultOfParticipant result =
                    new SkiSlalomResultOfParticipant(participant);

            result.setFirstManshTime(time);

            skiSlalom.addResult(result);
        }

        System.out.println("First mansh times entered.");
    }
}