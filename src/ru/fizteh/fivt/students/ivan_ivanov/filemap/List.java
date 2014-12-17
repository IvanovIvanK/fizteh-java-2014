package ru.fizteh.fivt.students.ivan_ivanov.filemap;

import ru.fizteh.fivt.students.ivan_ivanov.shell.Command;

import java.io.IOException;
import java.util.Set;

public class List implements Command<FileMapState> {

    public final String getName() {
        return "list";
    }

    public final void executeCmd(FileMapState inState, String[] args) throws IOException {
        Set<String> keys = inState.getDataBase().keySet();
        System.out.println(String.join(", ", keys));
    }
}
