package org.informatics.app.actions;

import org.informatics.app.AppContext;
import org.informatics.app.MenuAction;
import org.informatics.data.Biatlon;

public class SerializeBiatlonAction implements MenuAction {

    @Override
    public String getName() {
        return "Serialize Biatlon";
    }

    @Override
    public void execute(AppContext context) {
        Biatlon biatlon = ActionUtils.chooseBiatlon(context);

        if (biatlon == null) {
            return;
        }

        String fileName = context.getConsoleInputService()
                .readString("Enter serialization file name: ");

        context.getBiatlonSerializationService()
                .serializeBiatlon(biatlon, fileName);
    }
}