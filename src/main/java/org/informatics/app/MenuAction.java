package org.informatics.app;

public interface MenuAction {

    String getName();

    void execute(AppContext context);
}
