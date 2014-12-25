package ru.fizteh.fivt.students.ivan_ivanov.multifilehashmap.database;

import ru.fizteh.fivt.students.ivan_ivanov.shell.Command;

import java.io.IOException;

public class CmdSize extends Command<MultiFileHashMapState> {

    private int numArg = 0;

    @Override
    public String getName() {

        return "size";
    }

    @Override
    public void executeCmd(MultiFileHashMapState inState, String[] args) throws IOException {


        if (!checkArgs(numArg, args.length)) {
            return;
        }
        if (!checkTable(inState)) {
            return;
        }

        System.out.println(inState.getCurrentTable().size());
    }
}
