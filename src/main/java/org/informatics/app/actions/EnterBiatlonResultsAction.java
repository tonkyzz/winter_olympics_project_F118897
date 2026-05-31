package org.informatics.app.actions;

import org.informatics.app.AppContext;
import org.informatics.app.MenuAction;
import org.informatics.data.Biatlon;
import org.informatics.data.BiatlonResultOfParticipant;
import org.informatics.data.Participant;

import java.math.BigDecimal;

public class EnterBiatlonResultsAction implements MenuAction {

    @Override
    public String getName() {
        return "Enter Biatlon results";
    }

    @Override
    public void execute(AppContext context) {
        Biatlon biatlon = ActionUtils.chooseBiatlon(context);

        if (biatlon == null) {
            return;
        }

        for (Participant participant : biatlon.getParticipants()) {
            BigDecimal skiTime = context.getConsoleInputService()
                    .readBigDecimal("Enter ski time for " + participant.getName() + ": ");

            int completedLaps = context.getConsoleInputService()
                    .readInt("Enter completed laps for " + participant.getName() + ": ");

            int missedShots = context.getConsoleInputService()
                    .readInt("Enter missed shots for " + participant.getName() + ": ");

            BiatlonResultOfParticipant result =
                    new BiatlonResultOfParticipant(
                            participant,
                            skiTime,
                            completedLaps,
                            missedShots
                    );

            biatlon.addResult(result);
        }

        System.out.println("Biatlon results entered.");
    }
}