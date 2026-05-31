package org.informatics.app.actions;

import org.informatics.app.AppContext;
import org.informatics.app.MenuAction;
import org.informatics.data.Biatlon;

public class DeserializeBiatlonAction implements MenuAction {

    @Override
    public String getName() {
        return "Deserialize Biatlon";
    }

    @Override
    public void execute(AppContext context) {
        if (!ActionUtils.checkOlympicsCreated(context)) {
            return;
        }

        String fileName = context.getConsoleInputService()
                .readString("Enter file name for deserialization: ");

        Biatlon biatlon = context.getBiatlonSerializationService()
                .deserializeBiatlon(fileName);

        if (biatlon != null) {
            context.getOlympics().addCompetition(biatlon);
            System.out.println("Deserialized Biatlon added to Olympics.");
        }
    }
}