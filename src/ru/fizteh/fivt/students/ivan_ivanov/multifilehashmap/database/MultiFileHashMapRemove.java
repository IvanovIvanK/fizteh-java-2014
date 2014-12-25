package ru.fizteh.fivt.students.ivan_ivanov.multifilehashmap.database;

import ru.fizteh.fivt.students.ivan_ivanov.shell.Command;

import java.io.IOException;

public class MultiFileHashMapRemove extends Command<MultiFileHashMapState> {

    private int numArg = 1;

    @Override
    public String getName() {

        return "remove";
    }

    @Override
    public void executeCmd(MultiFileHashMapState inState, String[] args) throws IOException {
        if (!checkArgs(numArg, args.length)) {
            return;
        }
        if (!checkTable(inState)) {
            return;
        }
        String value = inState.removeFromCurrentTable(args[0]);
        if (null == value) {
            System.out.println("not found");
        } else {
            System.out.println("removed");
        }
    }
}
