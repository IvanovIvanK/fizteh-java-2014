package ru.fizteh.fivt.students.ivan_ivanov.shell;

import ru.fizteh.fivt.students.ivan_ivanov.multifilehashmap.database.MultiFileHashMapState;

import java.io.IOException;

public abstract class Command<State> {

    protected int numArg;

    public boolean checkArgs(int needArg, int haveArg) {
        if (needArg != haveArg) {
            System.out.println("incorrect number of arguments");
            return false;
        }
        return true;
    }

    public boolean checkTable(MultiFileHashMapState inState) throws IOException {
        if (inState.getCurrentTable() == null) {
            System.out.println("no table");
            return false;
        }
        return true;
    }

    public abstract String getName();

    public abstract void work(State shell, String[] args) throws IOException;

    public void executeCmd(State shell, String[] args) throws IOException {
        if (!checkArgs(numArg, args.length)) {
            return;
        }
        work(shell, args);
    }


}
