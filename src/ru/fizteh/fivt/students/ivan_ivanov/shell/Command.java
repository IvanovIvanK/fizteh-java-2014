package ru.fizteh.fivt.students.ivan_ivanov.shell;

import java.io.IOException;

public interface Command<State> {

    String getName();
    void executeCmd(State shell, String[] args) throws IOException;
}
