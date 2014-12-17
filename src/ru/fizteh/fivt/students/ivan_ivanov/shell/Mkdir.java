package ru.fizteh.fivt.students.ivan_ivanov.shell;

import java.io.File;
import java.io.IOException;

public class Mkdir implements Command<ShellState> {

    public String getName() {

        return "mkdir";
    }

    public void executeCmd(ShellState inState, String[] args) throws IOException {

        if (1 == args.length) {
            File theFile = new File(inState.getPath().resolve(args[0]).toString());
            theFile.mkdir();
        } else {
            throw new IOException("Inorrect name of directory");
        }
    }
}
