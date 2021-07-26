/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Eli Ferguson
 */

 package ucf.assignment5;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;

public class errorMessages {
    
    private void alert(String s1, String s2) {
        Alert ALERT = new Alert(AlertType.ERROR);
    
        ALERT.setTitle("Error");
        ALERT.setHeaderText(s1);
        ALERT.setContentText(s2);

        DialogPane dialogPane = ALERT.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("Alert.css").toExternalForm());dialogPane.getStyleClass().add("alert");
                
        ALERT.showAndWait();
    }

    public void help() {
        String helpString =
        "At the top of the screen you will see a menu bar which will\n" +
        "help you navigate around the program\n\n" +
        
        "The homescreen shows a larger view of the currnet inventory list loaded\n\n" +
        
        "The Search screen will allow you to search the inventory list\n" +
        "for an item either by name or by serial number\n\n" +
        
        "The edit screen will allow you to manager the current list\n" +
            "\tYou can fill all three propts and click add item\n" +
            "\tto add a new item\n" +
            "\tor by double clicking an existing item you can\n" +
            "\tload it into the side menu and update\n" +
            "\tthe item\n\n" +
        
        "The file menu will allow you to save the current file\n\n" +
        
            "\tIn the save file chooser you can choose to make a new file\n" +
                "\t\tor save over an existing file\n" +
                "\t\tThe file must end in either .txt .html or .json\n\n" +
        
            "\tIn the Load file chooser you only have the option to pick files\n" +
                "\t\tonly files ending in .txt .html and .json will be read in\n\n";
        
        alert("Help Screen\nThis is an inventory manager program", helpString);
    }

    public boolean itemAlreadyExists() {
        alert("Item Already Exists" ,"An Item with this Serial Number Already Exists");
        return false;
    }
    public boolean serialFormatError() {
        alert("Invalid Serial Number Format", "Serial Numbers must take the format XXXXXXXXXX\nWhere X is either a number or a letter ONLY!");
        return false;
    }

    public boolean nameFormatError() {
        alert("Invalid Item Name", "Name must be between 2 and 256 characters inclusive");
        return false;
    }

    public boolean valueFormatError() {
        alert("Invalid Item Value", "Value must be in a valid US dollar format\nEX:\n\t100.00 or 10,000.00 or 1.00");
        return false;
    }
    public void blankField() {
        alert("One or more text fields were blank", "Be sure to fill out all the text boxes");
    }

    public void itemNotAdded() {
        alert("No Item Added", "");
    }

    public void impossibleError() {
        alert("If we are here, IDK how", "Time to run the debugger");
    }

    public void noItemsFound() {
        alert("No Items Found", "The item you were looking for does not appear\nto be on this inventory list");
    }

    public boolean noItemSelected() {
        alert("No Item Selected", "Double click an item from the table to update");
        return false;
    }

    public void failedToSave() {
        alert("FAILED TO SAVE", "There was an error with saving you file");
    }

    public void fileIsEmpty() {
        alert("File Empty", "Failed To Load File");
    }

    public void failedToLoad() {
        alert("Failed To Load File", "No file loaded");
    }
}
