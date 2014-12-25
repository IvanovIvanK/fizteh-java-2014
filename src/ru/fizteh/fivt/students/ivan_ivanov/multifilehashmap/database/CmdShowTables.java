package ru.fizteh.fivt.students.ivan_ivanov.multifilehashmap.database;

import ru.fizteh.fivt.students.ivan_ivanov.shell.Command;

import java.io.IOException;
import java.util.Set;

public class CmdShowTables extends Command<MultiFileHashMapState> {

    @Override
    public final String getName() {
        return "show";
    }

    @Override
    public final void executeCmd(MultiFileHashMapState inState, String[] args) throws IOException {

        if (args.length != 1) {
            throw new IOException("Can't find key");
        }

        if (!args[0].equals("tables")) {
            throw new IOException("Can't find key");
        }

        work(inState, args);
    }

    @Override
    public final void work(MultiFileHashMapState inState, String[] args) throws IOException {
        System.out.println("table_name row_count");

        Set<String> tables = inState.getTableSet();
        for (String table : tables) {
            System.out.println(String.join(" ", table,
                    String.valueOf(inState.getTable(table).getDataBase().keySet().size())));
        }
    }
}
