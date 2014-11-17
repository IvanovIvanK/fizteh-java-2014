package ru.fizteh.fivt.students.Volodin_Denis.JUnit;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class HelpMap {
    public HashMap<String, String> map;
    public HelpMap() {
        map = new HashMap<String, String>();
    }
}

public class DataBase implements Map<String, String>, AutoCloseable {

    private static final int FOLDERS = 16;
    private static final int FILES = 16;
    private static final String ENCODING = "UTF-8";

    private String databasePath;
    private Map<String, String> database;
    private Map<String, String> diff;
    private boolean commit;

    public DataBase(final String dbPath) throws Exception {
        commit = false;
        databasePath = dbPath;
        database = new HashMap<String, String>();
        diff = new HashMap<String, String>();
        if (Paths.get(databasePath).toFile().exists()) {
            readFromDisk();
        } else {
            Files.createDirectory(Paths.get(databasePath).getFileName());
        }
    }

    public String getPath() throws Exception {
        return Paths.get(databasePath).getFileName().toString();
    }

    public void readFromDisk() throws Exception {
        for (int i = 0; i < FOLDERS; ++i) {
            for (int j = 0; j < FILES; ++j) {
                Path helpPath =  Paths.get(databasePath, Integer.toString(i) + ".dir", Integer.toString(j)
                                                                             + ".dat").normalize();
                if (helpPath.toFile().exists()) {
                    try (DataInputStream input = new DataInputStream(new FileInputStream(helpPath.toString()))) {
                        while (true) {
                            try {
                                String key = readOneWordFromDisk(input);
                                String value = readOneWordFromDisk(input);
                                
                                if ((Math.abs(key.hashCode()) % FOLDERS != i)
                                 || (Math.abs(key.hashCode()) / FOLDERS % FILES != j)) {
                                    throw new Exception("wrong input");
                                }
                                database.put(key, value);
                            } catch (EOFException e) {
                                break;
                            }
                        }
                    } catch (Exception e) {
                        ErrorFunctions.errorRead("read");
                    }
                }
            }
        }   
    }
  
    private String readOneWordFromDisk(DataInputStream input) throws Exception {
        byte[] word = new byte[input.readInt()];
        input.readFully(word);
        return new String(word, ENCODING);
    }

    public void writeOnDisk() throws Exception {
        HelpMap[][] helpMap = new HelpMap[FOLDERS][FILES];
        for (int i = 0; i < FOLDERS; ++i) {
            for (int j = 0; j < FILES; ++j) {
                helpMap[i][j] = new HelpMap();
            }
        }
        Set<String> keys = database.keySet();
        for (String key : keys) {
            helpMap[Math.abs(key.hashCode()) % FOLDERS][Math.abs(key.hashCode()) / FOLDERS % FILES].map
                    .put(key, database.get(key));
        }
        for (int i = 0; i < FOLDERS; ++i) {
            for (int j = 0; j < FILES; ++j) {
                Path helpPath =  Paths.get(databasePath, Integer.toString(i) + ".dir", Integer.toString(j)
                                                                             + ".dat").normalize();
                if (helpPath.toFile().exists()) {
                    if (!helpPath.toFile().delete()) {
                        ErrorFunctions.smthWrong("write", "file is not deleted");
                    }
                }
            }
            Path helpPath =  Paths.get(databasePath, Integer.toString(i) + ".dir").normalize();
            if (helpPath.toFile().exists()) {
                if (!helpPath.toFile().delete()) {
                    ErrorFunctions.smthWrong("write", "folder is not deleted");
                }
            }
        }
        for (int i = 0; i < FOLDERS; ++i) {
            for (int j = 0; j < FILES; ++j) {
                Set<String> keyList = helpMap[i][j].map.keySet();
                if (!keyList.isEmpty()) {
                    Path helpPath =  Paths.get(databasePath, Integer.toString(i) + ".dir").normalize();
                    if (!helpPath.toFile().exists()) {
                        Files.createDirectory(helpPath);
                    }
                    helpPath =  Paths.get(helpPath.toString(), j + ".dat").normalize();
                    Files.createFile(helpPath);
                    
                    try (FileOutputStream output = new FileOutputStream(helpPath.toString())) {
                        for (String key : keyList) {
                            writeOneWordOnDisk(key, output);
                            writeOneWordOnDisk(database.get(key), output);
                        }
                    } catch (Exception e) {
                        ErrorFunctions.errorWrite("write");
                    }
                }
            }
        }
    }

    private void writeOneWordOnDisk(final String word, FileOutputStream output) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        byte[] wordByte = buffer.putInt(word.getBytes(ENCODING).length).array();
        output.write(wordByte);
        output.write(word.getBytes(ENCODING));
    }

    public void setCommitTrue() {
        commit = true;
    }

    public void upgrade() throws Exception {
        for (String key : diff.keySet()) {
            database.put(key, diff.get(key));
        }
        diff.clear();
        writeOnDisk();
    }
    
    public void downgrade(){
        diff.clear();
    }
    
    public int numUncommitedChanges(){
        return diff.size();
    }
    
    @Override
    public void close() throws Exception {
        if (commit) {
            writeOnDisk();
            commit = false;
        }
    }

    @Override
    public void clear() {
        database.clear();
        diff.clear();
    }

    @Override
    public boolean containsKey(Object key) {
        if (database.containsKey(key)) {
            if (diff.containsKey(key)) {
                if (diff.get(key) != null) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return diff.containsKey(key);
        }
    }

    @Override
    public boolean containsValue(Object value) {
        if (diff.containsValue(value)) {
            return true;
        } else {
            for (String key : database.keySet()) {
                if (database.get(key).equals(value)) {
                    if (!diff.containsKey(key)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
    
    // I do not know, what is it and how it works.
    @Override
    public Set<java.util.Map.Entry<String, String>> entrySet() {
        return database.entrySet();
    }

    @Override
    public String get(Object key) {
        if (diff.containsKey(key)) {
            return diff.get(key);
        } else {
            return database.get(key);
        }
    }

    @Override
    public boolean isEmpty() {
        return (size() == 0 ? true : false);
    }

    @Override
    public Set<String> keySet() {

        Set<String> keysList = new HashSet<String>();
        if (isEmpty()) {
            return keysList;
        }

        for (String key : database.keySet()) {
            if (diff.containsKey(key)) {
                if (diff.get(key) != null) {
                    keysList.add(key);
                }
            } else {
                keysList.add(key);
            }
        }
        for (String key : diff.keySet()) {
            if (!keysList.contains(key)) {
                keysList.add(key);
            }
        }
        return keysList;
    }

    @Override
    public String put(String key, String value) {
        if (diff.containsKey(key)) {
            if (database.containsKey(key)) {
                if (database.get(key).equals(value)) {
                    String oldValue = diff.get(key);
                    diff.remove(key);
                    return oldValue;
                } else {
                    return diff.put(key, value);
                }
            } else {
                return diff.put(key, value);
            }
        } else {
            if (database.containsKey(key)) {
                if (database.get(key).equals(value)) {
                    return database.get(key);
                } else {
                    diff.put(key, value);
                    return database.get(key);
                }
            } else {
                return diff.put(key, value);
            }
        }
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        for (String key : m.keySet()) {
            database.put(key, m.get(key));
        }
    }

    @Override
    public String remove(Object key) {
        if (diff.containsKey(key)) {
            return diff.remove(key);
        } else {
            if (database.containsKey(key)) {
                diff.put((String) key, null);
            }
            return database.get(key);
        }
    }

    @Override
    public int size() {
        int result = database.size();
        
        for (String key : database.keySet()) {
            if (diff.containsKey(key)) {
                if (diff.get(key) == null) {
                    --result;
                }
            }
        }
        for (String key : diff.keySet()) {
            if (!database.containsKey(key)) {
                ++result;
            }
        }
        return result;
    }
    
    @Override
    public Collection<String> values() {
        Set<String> keyList = keySet();
        Collection<String> valueList = new HashSet<String>();
        for (String key : keyList) {
            valueList.add(get(key));
        }
        return valueList;
    }
}
