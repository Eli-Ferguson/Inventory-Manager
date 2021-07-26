/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Eli Ferguson
 */

package ucf.assignment5;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableModel extends errorMessages {

    public ObservableList<listItem> tableData = FXCollections.observableArrayList();
    public List<String> serials = new ArrayList<>();
    private List<String> names = new ArrayList<>();

    private editTableController editor;
    private searchTableController searcher;
    //private homeScreenController home;
    
    public void initEdit(editTableController controller) {
        editor = controller;
    }
    public void initSearch(searchTableController controller) {
        searcher = controller;
    }
    public void initHome(homeScreenController controller) {
        //home = controller;
    }

    public void addItemCheck() {
        //add item to table
        //create instance of listItem

        String name = editor.NameBox.getText();
        String serial = editor.SerialBox.getText();
        String value = editor.ValueBox.getText();        

        if(!serial.isBlank() && !name.isBlank() && !value.isBlank()) {
            if(
                serialCheckCompositionRequirements(serial) ? (
                    !serialCheckAlreadyExists(serial) ? (
                        nameCheck(name) ? (
                            valueCheck(value) ? true :
                            valueFormatError()
                        ) : nameFormatError()
                    ) : itemAlreadyExists()
                ) : serialFormatError() )
            {
                addItem(name, serial, value);
            } else {
                itemNotAdded();
            }
        } else {
            blankField();
        }
    }

    public void updateItemCheck() {
        
        String name = editor.NameBox.getText();
        String serial = editor.SerialBox.getText();
        String value = editor.ValueBox.getText();
        listItem originalItem = editor.wholeTableObject.getSelectionModel().getSelectedItem();

        if(!serial.isBlank() && !name.isBlank() && !value.isBlank()) {
            if(
                serialCheckCompositionRequirements(serial) ? (
                    nameCheck(name) ? (
                        valueCheck(value) ? (
                            originalItem != null ? true :
                            noItemSelected()
                        ) : valueFormatError()
                    ) : nameFormatError()
                ) : serialFormatError() )
            {
                updateItem(name, serial, value, originalItem);
            }
        }

    }

    public void removeItemBySerialCheck() {

        String serial = editor.SerialBox.getText();

        if(!serial.isBlank()) {

            if(serials.contains(serial)) {

                removeItemByName(serial);

            } else {
                noItemsFound();
            }
        } else {
            blankField();
        }

    }

    public void removeItemByNameCheck() {

        String name = editor.NameBox.getText();

        if(!name.isBlank()) {

            if(names.contains(name)) {

                removeItemByName(name);

            } else {
                noItemsFound();
            }
        } else {
            blankField();
        }
    }

    public void removeItemBySelected() {

        String serialNum = editor.wholeTableObject.getSelectionModel().getSelectedItem().getSerialNumber();

        removeItemBySerial(serialNum);
    }

    public ObservableList<listItem> getItemsBySerial() {
        
        String getBySerial = searcher.existingSerialNumber.getText();

        return getItems(getBySerial, "Serial Number");
    }

    public ObservableList<listItem> getItemsByName() {
        
        String getByName = searcher.existingName.getText();
        ObservableList<listItem> returnArr = FXCollections.observableArrayList();

        if(!getByName.equals(null)) {
            returnArr = getItems(getByName, "Name");
            if(returnArr.size() == 0) {
                noItemsFound();
            }
        } else {
            blankField();
        }
        return returnArr;
    }

    public void saveFile() {
        FileManager manager = new FileManager(this.tableData);

        if(!manager.saveFile()) {failedToSave();}
    }

    public void loadFile() {
        FileManager manager = new FileManager(this.tableData);

        tableData.setAll(manager.loadFile());

        for(listItem i : tableData) {
            serials.add(i.getSerialNumber());
            names.add(i.getName());
        }
    }

    public void addItem(String name, String serial, String value) {

        listItem newItem = new listItem(null, null, null);

        newItem.setSerialNumber(serial);
        newItem.setName(name);
        newItem.setValue(currencyFormat(value));

        tableData.add(newItem);
        
        serials.add(serial);
        names.add(name);
    }

    public void updateItem(String name, String serial, String value, listItem originalItem) {
                
        if(!originalItem.getSerialNumber().equals(serial)) {
            if(serialCheckAlreadyExists(serial)) {
                return;
            }
        }

        int indexOf = tableData.indexOf(originalItem);

        listItem updatedItem = new listItem(null, null, null);
            updatedItem.setName(name);
            updatedItem.setSerialNumber(serial);
            updatedItem.setValue(currencyFormat(value));

        tableData.set(indexOf, updatedItem);
        serials.set(indexOf, serial);
        names.set(indexOf, name);
    }

    public boolean removeItemBySerial(String serial) {

        if(serials.contains(serial)) {
            int index = serials.indexOf(serial);
    
            tableData.remove(index); serials.remove(index); names.remove(index);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeItemByName(String name) {

        boolean ret = false;

        while(names.contains(name)) {

            int index = names.indexOf(name);

            tableData.remove(index); serials.remove(index); names.remove(index);

            ret = true;
        }
        
        return ret;
    }

    public ObservableList<listItem> getItems(String get, String getByWhat) {

        ObservableList<listItem> returnArr = FXCollections.observableArrayList();

        if(getByWhat.equals("Serial Number")) {

            for(listItem I : tableData) {

                if( I.getSerialNumber().equals(get) ) {
                    returnArr.add(I);
                }
            }

        } else if (getByWhat.equals("Name")) {

            for(listItem I : tableData) {

                if( I.getName().equals(get) ) {
                    returnArr.add(I);
                }
            }

        } else {
            return returnArr;
        }
        return returnArr;
    }

    public boolean serialCheckCompositionRequirements(String serial) {

        if(serial.length() != 10) {
            return false;
        }

        Pattern pattern = Pattern.compile("\\p{Alnum}+");
        Matcher matcher = pattern.matcher(serial);
        
        if(matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean serialCheckAlreadyExists(String serial) {
        if(serials.contains(serial)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean nameCheck(String name) {

        if(name.length() >= 2 && name.length() <= 256) {
            return true;
        } else {
            return false;
        }
    }

    public boolean valueCheck(String value) {

        if(value.charAt(0) == '$') {
            value = value.substring(1);
        }

        String regex = "(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)?$";
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);

        if(matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    private String currencyFormat(String value) {

        if(value.charAt(0) == '$') {
            value = value.substring(1);
        }

        double val = Double.parseDouble(value);
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("en", "US"));

        return nf.format(val);
    }
}
