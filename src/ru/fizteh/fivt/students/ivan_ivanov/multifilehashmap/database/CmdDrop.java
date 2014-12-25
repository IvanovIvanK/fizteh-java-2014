package ru.fizteh.fivt.students.ivan_ivanov.multifilehashmap.database;

import ru.fizteh.fivt.students.ivan_ivanov.shell.Command;

import java.io.IOException;

public class CmdDrop extends Command<MultiFileHashMapState> {

    private int numArg = 0;

    @Override
    public String getName() {

        return "drop";
    }

    @Override
    public void executeCmd(MultiFileHashMapState inState, String[] args) throws IOException {

        if (!checkArgs(numArg, args.length)) {
            return;
        }
        if (inState.getTable(args[0]) == null) {
            System.out.println(args[0] + " not exists");
            return;
        } else {
            inState.deleteTable(args[0]);
            System.out.println("dropped");
        }
    }
}
