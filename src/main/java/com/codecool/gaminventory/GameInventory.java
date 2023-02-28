package com.codecool.gaminventory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public GameInventory() {
    }

    public GameInventory(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void display() {
        //TODO: this method should print the inventory to the console in a simple form.
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public void addToInventory(List<String> addedItems) {
        //TODO: this method should add all specified items to the inventory.
        for (String item : addedItems) {
            if (inventory.containsKey(item)) {
                inventory.put(item, inventory.get(item) + 1);
            } else {
                inventory.put(item, 1);
            }

        }

    }

    public void removeFromInventory(List<String> itemsToRemove) {
        //TODO: this method should remove the specified items from the inventory.
        for (String item : itemsToRemove) {
            if (inventory.containsKey(item)) {
                inventory.put(item, inventory.get(item) - 1);
                if(inventory.get(item) == 0){
                    inventory.remove(item);
                }


            }
        }

    }

    public void printInventory(SortOrder order) {
        //TODO: this method should print the inventory to the console,
        // sorted by the specified order, in the following form:
        //-----------------
        //item name | count
        //-----------------
        //     rope |     1
        //    torch |     6
        //-----------------
        System.out.print("-".repeat(17)+"\n");
        System.out.print("item name | count"+"\n");
        System.out.print("-".repeat(17)+"\n");
        Map<String, Integer> sortedMap;
        switch (order){
            case ASCENDING -> {
                sortedMap = new LinkedHashMap<>(inventory);
                sortedMap.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .forEach(entry -> System.out.print(" ".repeat(9-entry.getKey().length()) +entry.getKey() + " |     " + entry.getValue()+"\n"));
            }
            case DESCENDING -> {
                sortedMap = new LinkedHashMap<>(inventory);
                sortedMap.entrySet().stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                        .forEach(entry -> System.out.print(" ".repeat(9-entry.getKey().length()) +entry.getKey() + " |     " + entry.getValue()+"\n"));
            }
            case NOT_SORTED -> {
                sortedMap = inventory;
                sortedMap.forEach((key, value) -> System.out.print(" ".repeat(9 - key.length()) + key + " |     " + value + "\n"));
            }
        }
        System.out.print("-".repeat(17)+"\n");

    }

    public void importInventory(String filename) {
        //TODO: this method should import the inventory from a specified csv file
        String saves = filename;
        try {
            BufferedReader br = new BufferedReader(new FileReader(saves));
            String st =  br.readLine();
            String[] loadedSavefile = st.split(",");
            inventory = new HashMap<>();
            for (String item : loadedSavefile) {
                if (inventory.containsKey(item)) {
                    inventory.put(item, inventory.get(item) + 1);
                } else {
                    inventory.put(item, 1);
                }

            }

        } catch (IOException e) {
            System.out.printf("File '%s' not found!",filename);
        }


    }

    public void exportToCSV(String filename) {
        //TODO: this method should export the inventory to the specified file, in csv form
        String fileName = filename;
        StringBuilder value = new StringBuilder();
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                value.append(entry.getKey())
                        .append(",");
            }
        }

        File file = new File(fileName);
        try{
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(value.toString());
            bw.close();
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

}


