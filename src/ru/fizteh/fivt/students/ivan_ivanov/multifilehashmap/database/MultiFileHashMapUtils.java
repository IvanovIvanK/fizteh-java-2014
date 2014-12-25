package ru.fizteh.fivt.students.ivan_ivanov.multifilehashmap.database;

import ru.fizteh.fivt.students.ivan_ivanov.filemap.FileMapState;
import ru.fizteh.fivt.students.ivan_ivanov.filemap.FileMapUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MultiFileHashMapUtils {

    private static final int MAX_DIRS = 16;
    private static final int MAX_FILES = 16;
    private static final String DIR_SUFFIX = ".dir";
    private static final String FILE_SUFFIX = ".dat";

    public static void read(File currentDir, Map<String, String> currentMap) throws IOException {

        for (int directNumber = 0; directNumber < MAX_DIRS; ++directNumber) {
            File subDir = new File(currentDir, directNumber + DIR_SUFFIX);
            if (!subDir.exists()) {
                continue;
            }
            if (!subDir.isDirectory()) {
                throw new IOException(subDir.getName() + "isn't directory");
            }

            for (int fileNumber = 0; fileNumber < MAX_FILES; ++fileNumber) {
                File currentFile = new File(subDir, fileNumber + FILE_SUFFIX);
                if (!currentFile.exists()) {
                    continue;
                }
                FileMapState state = new FileMapState(currentFile);
                state.setDataBase(currentMap);
                FileMapUtils.readDataBase(state);
            }
        }
    }

    public static void deleteDirectory(File directory) throws IOException {

        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                deleteDirectory(f);
            }
        }
        boolean success = directory.delete();
        if (!success) {
            throw new IOException("cannot remove " + directory.getName() + ": unknown error");
        }
    }

    public static void write(File currentDir, Map<String, String> currentMap) throws IOException {

        Map<String, String>[][] arrayOfMap = new Map[MAX_DIRS][MAX_FILES];
        for (String key : currentMap.keySet()) {
            int byteOfKey = key.getBytes(StandardCharsets.UTF_8)[0];
            int nDirectory = Math.abs(byteOfKey) % MAX_DIRS;
            int nFile = Math.abs(byteOfKey) / MAX_DIRS % MAX_FILES;
            if (arrayOfMap[nDirectory][nFile] == null) {
                arrayOfMap[nDirectory][nFile] = new HashMap<String, String>();
            }
            arrayOfMap[nDirectory][nFile].put(key, currentMap.get(key));
        }

        for (int i = 0; i < MAX_DIRS; i++) {
            File dir = new File(currentDir, i + DIR_SUFFIX);
            for (int j = 0; j < 16; j++) {
                File file = new File(dir, j + FILE_SUFFIX);
                if (null == arrayOfMap[i][j]) {
                    if (file.exists()) {
                        file.delete();
                    }
                    continue;
                }

                if (!dir.exists()) {
                    dir.mkdir();
                }

                if (!file.exists()) {
                    file.createNewFile();
                }
                FileMapUtils.write(arrayOfMap[i][j], file);
            }

            if (dir.exists()) {
                if (dir.listFiles().length == 0) {
                    deleteDirectory(dir);
                }
            }
        }
    }
}
