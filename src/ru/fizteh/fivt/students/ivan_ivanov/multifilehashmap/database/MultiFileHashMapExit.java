package ru.fizteh.fivt.students.ivan_ivanov.multifilehashmap.database;

import ru.fizteh.fivt.students.ivan_ivanov.shell.Command;

import java.io.IOException;

public class MultiFileHashMapExit extends Command<MultiFileHashMapState> {

    private int numArg = 0;

    @Override
    public String getName() {

        return "exit";
    }

    @Override
    public void work(MultiFileHashMapState inState, String[] args) throws IOException {


        if (inState.getCurrentTable() != null) {
            inState.getCurrentTable().commit();
        }
        System.exit(0);
    }
}
