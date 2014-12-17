package ru.fizteh.fivt.students.ivan_ivanov.multifilehashmap;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.*;

public class MultiFileHashMapTableTest {

    private static MultiFileHashMapTable table;

    @Before
    public void setUp() throws Exception {
        File file = new File("testingTable");
        file.mkdir();
        table = new MultiFileHashMapTable(file);
    }

    @Test
    public void testGetName() throws Exception {

        assertEquals("testingTable", table.getName());
    }

    @Test
    public void testGetInEnglish() throws Exception {

        table.put("getEnglishKey", "getEnglishValue");
        assertEquals("getEnglishValue", table.get("getEnglishKey"));
    }

    @Test
    public void testGetInRussian() throws Exception {

        table.put("ключ", "значение");
        assertEquals("значение", table.get("ключ"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNull() throws Exception {

        table.get(null);
    }

    @Test
    public void testPutNew() throws Exception {

        assertNull(table.put("putNewKey", "putNewValue"));
        table.remove("putNewKey");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutNullKey() throws Exception {

        table.put(null, "valueOfNullKey");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutEmptyKey() throws Exception {

        table.put("", "valueOfEmptyKey");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNull() throws Exception {

        table.remove(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveWhitespace() throws Exception {
        table.remove(" ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetWhitespace() throws Exception {
        table.get(" ");
    }

    @Test
    public void testRemoveExisted() throws Exception {

        table.put("Key", "Value");
        assertEquals("Value", table.remove("Key"));
    }

    @Test
    public void testRemoveNotExisted() throws Exception {

        assertNull(table.remove("Key"));
    }

    @Test
    public void testSize() throws Exception {
        table.put("Key1", "Value1");
        table.put("Key2", "Value2");
        table.put("Key3", "Value3");
        assertEquals(3, table.size());
    }

    @Test
    public void testPutAndRollback() throws Exception {
        table.put("Key1", "Value1");
        table.put("Key2", "Value2");
        table.put("Key3", "Value3");
        assertEquals(3, table.rollback());
    }

    @Test
    public void testPutCommit() throws Exception {
        table.put("Key1", "Value1");
        table.put("Key2", "Value2");
        table.put("Key3", "Value3");
        assertEquals(3, table.commit());
    }

    @Test
    public void testRemovePutCommit() throws Exception {
        table.put("Key", "Value");
        table.commit();
        table.remove("Key");
        table.put("Key", "Value");
        assertEquals(0, table.commit());
    }

    @Test
    public void testList() throws Exception {
        table.put("Key1", "Value1");
        table.put("Key2", "Value2");
        table.put("Key3", "Value3");
        table.commit();
        List<String> ans = new ArrayList<String>(Arrays.asList("Key1", "Key2", "Key3"));
        assertEquals(ans, table.list());
    }
}
