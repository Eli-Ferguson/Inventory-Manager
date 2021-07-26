/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Eli Ferguson
 */

 package ucf.assignment5;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FileManager extends errorMessages{
    
    private File currentFile;

    public ObservableList<listItem> currentTable = FXCollections.observableArrayList();

    public FileManager(ObservableList<listItem> tableData) {
        this.currentTable = tableData;
    }

    public boolean saveFile() {
        currentFile = saveToFileChooser();

        return listSaver();
    }

    public boolean listSaver() {

        String path = currentFile.getAbsolutePath();

        String fileType = pathCheck(path);

        if(fileType.equals("TSV")) {
            WriteToTSV();
        } else if(fileType.equals("JSON")) {
            WriteToJSON();
        } else if (fileType.equals("HTML")) {
            WriteToHTML();
        } else {
            return false;
        }

        return true;
    }

    public ObservableList<listItem> loadFile() {
        currentFile = loadFromFileChooser();

        return listLoader();
    }

    public ObservableList<listItem> listLoader() {

        String path = currentFile.getAbsolutePath();

        String fileType = pathCheck(path);

        ObservableList<listItem> tmpTable = FXCollections.observableArrayList();

        if(fileType.equals("TSV")) {
            tmpTable = ReadFromTSV();
        } else if(fileType.equals("JSON")) {
            tmpTable = ReadFromJSON();
        } else if (fileType.equals("HTML")) {
            tmpTable = ReadFromHTML();
        } else {
            failedToLoad();
        }

        if(tmpTable != null) {
            currentTable = tmpTable;
        } else {
            fileIsEmpty();
        }
        
        return currentTable;
    }


    private void WriteToJSON() {
        JSONManager manager = new JSONManager(currentFile, currentTable);
        manager.write();
    }

    private ObservableList<listItem> ReadFromJSON() {
        JSONManager manager = new JSONManager(currentFile, currentTable);
        return manager.read();
    }

    private void WriteToHTML() {
        HTMLManager manager = new HTMLManager(currentFile, currentTable);
        manager.write();
    }

    private ObservableList<listItem> ReadFromHTML() {
        HTMLManager manager = new HTMLManager(currentFile, currentTable);
        return manager.read();
    }

    private void WriteToTSV() {
        TSVManager manager = new TSVManager(currentFile, currentTable);
        manager.write();
    }

    private ObservableList<listItem> ReadFromTSV() {
        TSVManager manager = new TSVManager(currentFile, currentTable);
        return manager.read();
    }    

    private File saveToFileChooser() {
        
        FileChooser fileChooser = new FileChooser();
        
        fileChooser.setTitle("Choose a File to Read In");
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("All Files", "*.*"),
            new ExtensionFilter("TSV Files", "*.txt"),
            new ExtensionFilter("HTML Files", "*.HTML"),
            new ExtensionFilter("Json Files", "*.json"));
        
        File selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile != null) {
            return selectedFile;
        } else {
            return null;
        }
        
    }

    private File loadFromFileChooser() {
        
        FileChooser fileChooser = new FileChooser();
        
        fileChooser.setTitle("Choose a File to Read In");
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("All Files", "*.*"),
            new ExtensionFilter("TSV Files", "*.txt"),
            new ExtensionFilter("HTML Files", "*.HTML"),
            new ExtensionFilter("Json Files", "*.json"));
        
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            return selectedFile;
        } else {
            return null;
        }
        
    }

    private String pathCheck(String path) {

        if(path.toLowerCase().endsWith(".txt")) {
            return "TSV";
        } else if(path.toLowerCase().endsWith(".json")) {
            return "JSON";
        } else if(path.toLowerCase().endsWith(".html")) {
            return "HTML";
        } else {
            return "Invalid";
        }
    }

    public void setCurrentFile(File in) {
        currentFile = in;
    }
}
