package ru.fizteh.fivt.students.ivan_ivanov.multifilehashmap.database;

import ru.fizteh.fivt.students.ivan_ivanov.shell.Command;

import java.io.IOException;

public class MultiFileHashMapPut extends Command<MultiFileHashMapState> {

    private int numArg = 2;

    @Override
    public String getName() {

        return "put";
    }

    @Override
    public void work(MultiFileHashMapState inState, String[] args) throws IOException {

        if (!checkTable(inState)) {
            return;
        }
        String value = inState.putToCurrentTable(args[0], args[1]);
        if (null == value) {
            System.out.println("new");
        } else {
            System.out.println("overwrite");
            System.out.println(value);
        }
    }
}

