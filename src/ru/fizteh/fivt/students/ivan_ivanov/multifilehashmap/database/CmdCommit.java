package ru.fizteh.fivt.students.ivan_ivanov.multifilehashmap.database;

import ru.fizteh.fivt.students.ivan_ivanov.shell.Command;

import java.io.IOException;

public class CmdCommit extends Command<MultiFileHashMapState> {

    private int numArg = 0;

    @Override
    public String getName() {

        return "commit";
    }

    @Override
    public void work(MultiFileHashMapState inState, String[] args) throws IOException {

        if (!checkTable(inState)) {
            return;
        }

        try {
            System.out.println(inState.getCurrentTable().commit());
        } catch (RuntimeException e) {
            throw new IOException(e.getMessage());
        }
    }
}
