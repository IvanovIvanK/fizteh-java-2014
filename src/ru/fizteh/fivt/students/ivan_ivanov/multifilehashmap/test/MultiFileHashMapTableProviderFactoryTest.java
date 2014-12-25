package ru.fizteh.fivt.students.ivan_ivanov.multifilehashmap.test;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.fizteh.fivt.students.ivan_ivanov.multifilehashmap.database.MultiFileHashMapTableProviderFactory;

import java.io.File;

public class MultiFileHashMapTableProviderFactoryTest {

    private static File database;
    private MultiFileHashMapTableProviderFactory tableProviderFactory = new MultiFileHashMapTableProviderFactory();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        database = new File("newNotExistedTable").getCanonicalFile();
        if (database.isFile()) {
            database.delete();
        }
        database.mkdir();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        database.delete();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNull() throws Exception {
        tableProviderFactory.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateEmpty() throws Exception {
        tableProviderFactory.create(" ");
    }

    @Test
    public void testCreateNotExisted() throws Exception {
        Assert.assertNotNull(tableProviderFactory.create("newNotExistedTable"));
    }
}
