package ru.fizteh.fivt.students.ivan_ivanov.multifilehashmap;

import ru.fizteh.fivt.students.ivan_ivanov.shell.Command;

import java.io.IOException;
import java.util.List;

public class MultiFileHashMapList implements Command<MultiFileHashMapState> {

    @Override
    public final String getName() {
        return "list";
    }

    @Override
    public final void executeCmd(MultiFileHashMapState inState, String[] args) throws IOException {

        if (args.length != 0) {
            System.out.println("incorrect number of arguments");
            return;
        }
        if (inState.getCurrentTable() == null) {
            System.out.println("no table");
            return;
        }
        List<String> keys = inState.getCurrentTableListOfKeys();
        System.out.println(String.join(", ", keys));
    }
}
