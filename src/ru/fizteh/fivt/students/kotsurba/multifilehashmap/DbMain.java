package ru.fizteh.fivt.students.kotsurba.multifilehashmap;

import ru.fizteh.fivt.students.kotsurba.filemap.database.DataBaseException;
import ru.fizteh.fivt.students.kotsurba.filemap.database.DataBaseWrongFileFormat;
import ru.fizteh.fivt.students.kotsurba.filemap.shell.CommandParser;
import ru.fizteh.fivt.students.kotsurba.filemap.shell.InvalidCommandException;
import ru.fizteh.fivt.students.kotsurba.filemap.shell.Shell;

import java.util.Scanner;

public class DbMain {

    static final int END_OF_INPUT = -1;
    static final int END_OF_TRANSMISSION = 4;

    private static boolean isTerminalSymbol(final int character) {
        return ((character == END_OF_INPUT) || (character == END_OF_TRANSMISSION));
    }

    public static boolean hasTerminalSymbol(final String str) {
        for (int i = 0; i < str.length(); ++i) {
            if (isTerminalSymbol(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static void main(final String[] args) {
        try {
            Shell shell = new Shell();
            if (!System.getProperties().containsKey("fizteh.db.dir")) {
                System.err.println("Please set database directory!");
                System.err.println("-Ddb.file=<directory name>");
                System.exit(1);
            }

            DataBaseTable base = new DataBaseTable(System.getProperty("fizteh.db.dir"));

            shell.addCommand(new ShellDbPut(base));
            shell.addCommand(new ShellExit(base));
            shell.addCommand(new ShellDbGet(base));
            shell.addCommand(new ShellDbRemove(base));
            shell.addCommand(new ShellCreateTable(base));
            shell.addCommand(new ShellDropTable(base));
            shell.addCommand(new ShellUseTable(base));
            shell.addCommand(new ShellDbList(base));
            shell.addCommand(new ShellShowTables(base));

            if (args.length > 0) {
                CommandParser parser = new CommandParser(args);
                while (!parser.isEmpty()) {
                    shell.executeCommand(parser.getCommand());
                }
            } else {
                Scanner scanner = new Scanner(System.in);
                System.out.print("$ ");
                while (true) {
                    try {
                        if (!scanner.hasNext()) {
                            System.exit(0);
                        }

                        String command = scanner.nextLine();

                        if (hasTerminalSymbol(command)) {
                            System.exit(0);
                        }

                        CommandParser parser = new CommandParser(command);
                        if (!parser.isEmpty()) {
                            shell.executeCommand(parser.getCommand());
                        }
                    } catch (InvalidCommandException e) {
                        System.err.println(e.getMessage());
                    } finally {
                        System.out.print("$ ");
                    }
                }
            }

        } catch (InvalidCommandException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (DataBaseException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (DataBaseWrongFileFormat e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
