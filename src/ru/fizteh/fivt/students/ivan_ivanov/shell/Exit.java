package ru.fizteh.fivt.students.ivan_ivanov.shell;

import java.io.IOException;

public class Exit extends Command<ShellState> {

    @Override
    public String getName() {

        return "exit";
    }

    @Override
    public void executeCmd(ShellState inState, String[] args) throws IOException {

        System.exit(0);
    }
}
