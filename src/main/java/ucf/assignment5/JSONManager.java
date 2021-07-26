/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Eli Ferguson
 */

 package ucf.assignment5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class JSONManager {
    
    private File file;
    private ObservableList<listItem> table = FXCollections.observableArrayList();

    public JSONManager(File newFile, ObservableList<listItem> newList) {
        this.file = newFile;
        this.table = newList;
    }

    public void write() {
        try {
            PrintWriter pw = new PrintWriter(file);

            JsonArray arr = new JsonArray();

            for(listItem i : table) {

                JsonObject obj = new JsonObject();

                obj.addProperty("value", i.getValue());
                obj.addProperty("name", i.getName());
                obj.addProperty("serialNumber", i.getSerialNumber());
                
                arr.add(obj);
            }

            pw.write(pretty(arr));

            pw.flush();
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<listItem> read() {
        ObservableList<listItem> returnList = FXCollections.observableArrayList();

        try{
            Reader reader = Files.newBufferedReader(Paths.get(file.getAbsolutePath()));

            JsonParser parser = new JsonParser();

            JsonArray arr = (JsonArray) parser.parse(reader);

            for(JsonElement o : arr) {
                JsonObject newObj = o.getAsJsonObject();

                listItem newItem = new listItem(null, null, null);

                newItem.setName(newObj.get("name").getAsString());
                newItem.setValue(newObj.get("value").getAsString());
                newItem.setSerialNumber(newObj.get("serialNumber").getAsString());

                returnList.add(newItem);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnList;
    }

    private String pretty(JsonArray json) {
        String formattedData = new GsonBuilder().setPrettyPrinting().create().toJson(json);

        return formattedData;
    }
}
