package ru.fizteh.fivt.students.ivan_ivanov.filemap;

import ru.fizteh.fivt.students.ivan_ivanov.shell.Command;

import java.io.IOException;

public class Remove extends Command<FileMapState> {

    public String getName() {

        return "remove";
    }

    public void executeCmd(FileMapState inState, String[] args) throws IOException {

        if (args.length != 1) {
            System.out.println("incorrect number of arguments");
            return;
        }

        String value = inState.getDataBase().remove(args[0]);
        if (value == null) {
            System.out.println("not found");
        } else {
            System.out.println("removed");
        }
    }
}
