package com.codecool.gaminventory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class GameInventoryTest {

    @Test
    public void testAdd() {
        GameInventory gameInventory = new GameInventory();
        List<String> toAdd = new ArrayList<String>() {{
            add("gold coin");
            add("gold coin");
            add("gold coin");
            add("sword");
            add("ruby");
            add("ruby");
        }};
        Map<String, Integer> expected = new HashMap<String, Integer>() {
            {
                put("gold coin", 3);
                put("sword", 1);
                put("ruby", 2);
            }
        };
        gameInventory.addToInventory(toAdd);
        assertEquals(expected, gameInventory.getInventory());
    }

    @Test
    public void testRemove() {
        Map<String, Integer> start = new HashMap<String, Integer>() {
            {
                put("gold coin", 3);
                put("sword", 1);
                put("ruby", 2);
            }
        };
        Map<String, Integer> expected = new HashMap<String, Integer>() {
            {
                put("gold coin", 1);
                put("ruby", 2);
            }
        };
        List<String> toRemove = new ArrayList<String>() {{
            add("gold coin");
            add("gold coin");
            add("sword");
        }};
        GameInventory gameInventory = new GameInventory(start);
        gameInventory.removeFromInventory(toRemove);
        assertEquals(expected, gameInventory.getInventory());
    }

    @Test
    public void testDisplay() {
        Map<String, Integer> start = new HashMap<String, Integer>() {
            {
                put("rope", 5);
                put("torch", 6);
            }
        };
        GameInventory gameInventory = new GameInventory(start);
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        gameInventory.display();
        assertTrue(outputStreamCaptor.toString().contains("rope: 5"));
        assertTrue(outputStreamCaptor.toString().contains("torch: 6"));
    }

    @Test
    public void testPrintInventory() {
        Map<String, Integer> start = new HashMap<String, Integer>() {
            {
                put("rope", 1);
                put("torch", 6);

            }
        };
        String expected =
                "-----------------\n" +
                        "item name | count\n" +
                        "-----------------\n" +
                        "     rope |     1\n" +
                        "    torch |     6\n" +
                        "-----------------";
        GameInventory gameInventory = new GameInventory(start);
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        gameInventory.printInventory(SortOrder.ASCENDING);
        assertEquals(expected, outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void testImportInventory() {
        GameInventory gameInventory = new GameInventory();
        Map<String, Integer> expected = new HashMap<String, Integer>() {
            {
                put("gold coin", 1);
                put("dagger", 3);
                put("battleaxe", 1);
            }
        };
        gameInventory.importInventory("test_inventory.csv");
        assertEquals(expected, gameInventory.getInventory());
    }

    @Test
    public void testCorrectErrorMessageIfFileNotExists() {
        GameInventory gameInventory = new GameInventory();
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        gameInventory.importInventory("notexisting.csv");
        assertEquals("File 'notexisting.csv' not found!", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void testExportInventory() throws IOException {
        Map<String, Integer> start = new HashMap<String, Integer>() {
            {
                put("gold coin", 1);
                put("dagger", 2);
            }
        };
        GameInventory gameInventory = new GameInventory(start);
        gameInventory.exportToCSV("exportTest.csv");
        int itemCount = 0;
        int daggerCount = 0;
        int coinCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("exportTest.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] itemsInLine = line.split(",");
                for (String item : itemsInLine) {
                    if (item.equals("gold coin")) {
                        coinCount++;
                    }
                    if (item.equals("dagger")) {
                        daggerCount++;
                    }
                    itemCount++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(1, coinCount);
        assertEquals(2, daggerCount);
        assertEquals(3, itemCount);
        Files.deleteIfExists(Paths.get("exportTest.csv"));
    }
}
