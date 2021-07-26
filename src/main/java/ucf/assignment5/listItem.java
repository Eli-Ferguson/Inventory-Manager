/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Eli Ferguson
 */

package ucf.assignment5;

import javafx.beans.property.SimpleStringProperty;

public class listItem {
    private SimpleStringProperty value;
    private SimpleStringProperty name;
    private SimpleStringProperty serialNumber;

    public listItem(String VALUE, String NAME, String SERIAL) {
        this.value = new SimpleStringProperty(VALUE);
        this.name = new SimpleStringProperty(NAME);
        this.serialNumber = new SimpleStringProperty(SERIAL);
    }

    public String getValue() {
        return value.get();
    }
    public void setValue(String VALUE) {
        value.set(VALUE);
    }

    public String getName() {
        return name.get();
    }
    public void setName(String NAME) {
        name.set(NAME);
    }

    public String getSerialNumber() {
        return serialNumber.get();
    }
    public void setSerialNumber(String SERIAL) {
        serialNumber.set(SERIAL);
    }
}
