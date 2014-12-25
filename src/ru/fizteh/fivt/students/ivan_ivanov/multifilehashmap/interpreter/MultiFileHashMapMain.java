package ru.fizteh.fivt.students.ivan_ivanov.multifilehashmap.interpreter;

import ru.fizteh.fivt.students.ivan_ivanov.multifilehashmap.database.MultiFileHashMapState;
import ru.fizteh.fivt.students.ivan_ivanov.shell.Shell;

import java.io.File;
import java.io.IOException;

public class MultiFileHashMapMain {

    public static void main(String[] args) throws IOException {


        String currentProperty = System.getProperty("fizteh.db.dir");
        try {

            if (currentProperty == null) {
                throw new IOException("Working directory is not specified");
            }

            if (!new File(currentProperty).exists()) {
                throw new IOException("Working directory does not exist");
            }

            if (!new File(currentProperty).isDirectory()) {
                throw new IOException("Working directory is not a directory");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        File base = new File(currentProperty);
        try {
            if (!base.exists()) {
                base.createNewFile();
            }

            base = base.getCanonicalFile();
            MultiFileHashMapState startState = new MultiFileHashMapState(base);
            Shell<MultiFileHashMapState> mfhm = new Shell<MultiFileHashMapState>(startState);

            MultiFileHashMapExecutor exec = new MultiFileHashMapExecutor();

            if (args.length > 0) {
                mfhm.batchState(args, exec);
            } else {
                mfhm.interactiveState(exec);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        System.exit(0);
    }
}
