/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Eli Ferguson
 */

package ucf.assignment5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TSVManager {
 
    private File file;
    private ObservableList<listItem> table = FXCollections.observableArrayList();

    public TSVManager(File newFile, ObservableList<listItem> newList) {
        this.file = newFile;
        this.table = newList;
    }

    public void write() {
        try {
            PrintWriter pw = new PrintWriter(file);

            pw.write(concatList());
            pw.flush();
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<listItem> read() {

        ObservableList<listItem> returnList = FXCollections.observableArrayList();

        try {
            Scanner in = new Scanner(file);

            while (in.hasNextLine()) {
                String[] Split = in.nextLine().split("\t");

                listItem newItem = new listItem(null, null, null);
                newItem.setName(Split[0]);
                newItem.setSerialNumber(Split[1]);
                newItem.setValue(Split[2]);

                returnList.add(newItem);
            }

            in.close();

            return returnList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String concatList() {

        String retStr = "";
        
        for(listItem i : table) {
            retStr += i.getName() + "\t" + i.getSerialNumber() + "\t" + i.getValue() + "\n";
        }

        return retStr;
    }
    
}