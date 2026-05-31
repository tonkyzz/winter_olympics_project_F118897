package org.informatics.app.actions;

import org.informatics.app.AppContext;
import org.informatics.app.MenuAction;
import org.informatics.data.SkiSlalom;
import org.informatics.data.SkiSlalomResultOfParticipant;

import java.math.BigDecimal;
import java.util.List;

public class EnterSkiSlalomSecondManshAction implements MenuAction {

    @Override
    public String getName() {
        return "Enter Ski Slalom second mansh times";
    }

    @Override
    public void execute(AppContext context) {
        SkiSlalom skiSlalom = ActionUtils.chooseSkiSlalom(context);

        if (skiSlalom == null) {
            return;
        }

        List<SkiSlalomResultOfParticipant> qualified =
                context.getSkiSlalomService().getQualifiedForSecondMansh(skiSlalom);

        if (qualified.isEmpty()) {
            System.out.println("No qualified participants.");
            return;
        }

        System.out.println("Qualified participants:");

        for (SkiSlalomResultOfParticipant result : qualified) {
            System.out.println(result.getParticipant().getName()
                    + " | First mansh: "
                    + result.getFirstManshTime());
        }

        for (SkiSlalomResultOfParticipant result : qualified) {
            BigDecimal time = context.getConsoleInputService()
                    .readBigDecimal("Enter second mansh time for "
                            + result.getParticipant().getName()
                            + ": ");

            result.setSecondManshTime(time);
        }

        System.out.println("Second mansh times entered.");
    }
}