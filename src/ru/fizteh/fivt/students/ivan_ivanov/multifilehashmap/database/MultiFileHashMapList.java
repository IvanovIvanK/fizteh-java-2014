package ru.fizteh.fivt.students.ivan_ivanov.multifilehashmap.database;

import ru.fizteh.fivt.students.ivan_ivanov.shell.Command;

import java.io.IOException;
import java.util.List;

public class MultiFileHashMapList extends Command<MultiFileHashMapState> {

    private int numArg = 0;

    @Override
    public final String getName() {
        return "list";
    }

    @Override
    public final void executeCmd(MultiFileHashMapState inState, String[] args) throws IOException {

        if (!checkArgs(numArg, args.length)) {
            return;
        }
        if (!checkTable(inState)) {
            return;
        }
        List<String> keys = inState.getCurrentTableListOfKeys();
        System.out.println(String.join(", ", keys));
    }
}
