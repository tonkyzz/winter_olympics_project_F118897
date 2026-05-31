package org.informatics.app.actions;

import org.informatics.app.AppContext;
import org.informatics.app.MenuAction;

public class SaveResultsToFileAction implements MenuAction {

    @Override
    public String getName() {
        return "Save final results to file";
    }

    @Override
    public void execute(AppContext context) {
        if (!ActionUtils.checkOlympicsCreated(context)) {
            return;
        }

        String fileName = context.getConsoleInputService()
                .readString("Enter file name: ");

        context.getFileService()
                .saveFinalResultsToFile(context.getOlympics(), fileName);
    }
}