package ru.fizteh.fivt.students.ivan_ivanov.filemap;

import ru.fizteh.fivt.students.ivan_ivanov.shell.Command;

import java.io.IOException;

public class Exit extends Command<FileMapState> {

    public String getName() {

        return "exit";
    }

    public void executeCmd(FileMapState inState, String[] args) throws IOException {

        FileMapUtils.write(inState.getDataBase(), inState.getDataFile());
        System.exit(0);
    }
}
