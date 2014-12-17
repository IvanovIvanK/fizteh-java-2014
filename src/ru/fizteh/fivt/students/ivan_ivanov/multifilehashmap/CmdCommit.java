package ru.fizteh.fivt.students.ivan_ivanov.multifilehashmap;

import ru.fizteh.fivt.students.ivan_ivanov.shell.Command;

import java.io.IOException;

public class CmdCommit implements Command<MultiFileHashMapState> {

    @Override
    public String getName() {

        return "commit";
    }

    @Override
    public void executeCmd(MultiFileHashMapState inState, String[] args) throws IOException {

        if (inState.getCurrentTable() == null) {
            System.out.println("no table");
            return;
        }

        System.out.println(inState.getCurrentTable().commit());
    }
}
