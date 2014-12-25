package ru.fizteh.fivt.students.ivan_ivanov.multifilehashmap.database;

import ru.fizteh.fivt.students.ivan_ivanov.shell.Command;

import java.io.IOException;

public class CmdCreate implements Command<MultiFileHashMapState> {

    @Override
    public String getName() {

        return "create";
    }

    @Override
    public void executeCmd(MultiFileHashMapState inState, String[] args) throws IOException {

        if (inState.createTable(args[0]) != null) {
            System.out.println("created");
        } else {
            System.out.println(args[0] + " exists");
        }
    }
}
