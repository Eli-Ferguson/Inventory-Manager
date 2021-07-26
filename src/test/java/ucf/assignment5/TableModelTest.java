package ucf.assignment5;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test; 

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableModelTest {
    ObservableList<listItem> TestList = FXCollections.observableArrayList();
    List<String> serials = new ArrayList<>();
    List<String> names = new ArrayList<>();

    private void resetTestList(TableModel manager) {

        ObservableList<listItem> tmpList = FXCollections.observableArrayList();

        for(int i = 0; i < 3; i++) {
            String value = "$" + i*10 + ".00";
            String name = "Test";
            String serial = "" + i;

            listItem newItem = new listItem(value, name, serial);
            serials.add(serial);
            names.add(name);

            tmpList.add(newItem);
            manager.addItem(name, serial, value);
        }

        TestList.setAll(tmpList);
    }

    @Test
    public void addItem() {
        TableModel manager = new TableModel();
            resetTestList(manager);

        manager.tableData.setAll(TestList);

        manager.addItem("Test", "1010", "$10.00");

        boolean expected = true;

        if(manager.tableData.size() > TestList.size()) {
            listItem newItem = new listItem(
                manager.tableData.get(TestList.size()).getValue(),
                manager.tableData.get(TestList.size()).getName(),
                manager.tableData.get(TestList.size()).getSerialNumber()
                );

            if(!newItem.getName().equals("Test")) {expected = false;}
            if(!newItem.getSerialNumber().equals("1010")) {expected = false;}
            if(!newItem.getValue().equals("$10.00")) {expected = false;}

        }

        assertTrue(expected);
    }

    @Test
    public void updateItemCheck() {
        TableModel manager = new TableModel();
            resetTestList(manager);

        manager.tableData.setAll(TestList);

        String name = "Test 0";
        String serial = "1010";
        String value = "$10.00";

        manager.updateItem(name, serial, value, manager.tableData.get(0));

        boolean expected = true;

        listItem newItem = new listItem(
            manager.tableData.get(manager.serials.indexOf(serial)).getValue(),
            manager.tableData.get(manager.serials.indexOf(serial)).getName(),
            manager.tableData.get(manager.serials.indexOf(serial)).getSerialNumber()
            );

        if(!newItem.getName().equals("Test 0")) {expected = false;}
        if(!newItem.getSerialNumber().equals("1010")) {expected = false;}
        if(!newItem.getValue().equals("$10.00")) {expected = false;}

        assertTrue(expected);
    }

    @Test
    public void removeBySerial() {
        TableModel manager = new TableModel();
            resetTestList(manager);

        assertTrue(manager.removeItemBySerial("0"));
    }
    @Test
    public void removeBySerialFail() {
        TableModel manager = new TableModel();
            resetTestList(manager);

        assertFalse(manager.removeItemBySerial("12"));
    }

    @Test
    public void removeByName() {
        TableModel manager = new TableModel();
            resetTestList(manager);

        manager.removeItemByName("Test");

        assertTrue(manager.tableData.size() == 0);
    }
    @Test
    public void removeByFail() {
        TableModel manager = new TableModel();
            resetTestList(manager);

        manager.removeItemByName("Test");

        assertFalse(manager.tableData.size() == 0);
    }

    @Test
    public void removeBySelected() {
        TableModel manager = new TableModel();
            resetTestList(manager);

        assertTrue(manager.removeItemBySerial("0"));
    }
    @Test
    public void removeBySelectedFail() {
        TableModel manager = new TableModel();
            resetTestList(manager);

        assertFalse(manager.removeItemBySerial("12"));
    }

    @Test
    public void getItemsSerial() {
        TableModel manager = new TableModel();
            resetTestList(manager);

        ObservableList<listItem> newList =  manager.getItems("0", "Serial Number");

        boolean expected = newList.get(0).equals(manager.tableData.get(0));

        assertTrue(expected);
    }
    @Test
    public void getItemsSerialFail() {
        TableModel manager = new TableModel();
            resetTestList(manager);

        ObservableList<listItem> newList =  manager.getItems("12", "Serial Number");

        boolean expected = newList.get(0).equals(manager.tableData.get(0));

        assertFalse(expected);
    }

    @Test
    public void getItemsName() {
        TableModel manager = new TableModel();
            resetTestList(manager);

        ObservableList<listItem> newList =  manager.getItems("Test", "Name");

        boolean expected = true;

        for(int i = 0; i < newList.size(); i++) {
            if(!newList.get(i).getName().equals(manager.tableData.get(i).getName())) {expected = false; break;}
        }

        assertTrue(expected);
    }
    @Test
    public void getItemsNameFail() {
        TableModel manager = new TableModel();
            resetTestList(manager);

        ObservableList<listItem> newList =  manager.getItems("Not Exist", "Name");

        assertFalse(newList.size() != 0);
    }

    @Test
    public void serialCompositionTest() {
        TableModel manager = new TableModel();

        assertTrue(manager.serialCheckCompositionRequirements("1a2b3c4d5e"));
    }
    @Test
    public void serialCompositionTestOnlyNumbers() {
        TableModel manager = new TableModel();

        assertTrue(manager.serialCheckCompositionRequirements("1234567891"));
    }
    @Test
    public void serialCompositionTestOnlyLetters() {
        TableModel manager = new TableModel();

        assertTrue(manager.serialCheckCompositionRequirements("abcdefghij"));
    }
    @Test
    public void serialCompositionTestSpecialChar_Exclaim() {
        TableModel manager = new TableModel();

        assertFalse(manager.serialCheckCompositionRequirements("abcdefghi!"));
    }
    @Test
    public void serialCompositionTestSpecialChar_At() {
        TableModel manager = new TableModel();

        assertFalse(manager.serialCheckCompositionRequirements("abcdefghi@"));
    }

    @Test
    public void nameCheckMin() {
        TableModel manager = new TableModel();

        assertTrue(manager.nameCheck("mn"));
    }
    @Test
    public void nameCheckMax() {
        TableModel manager = new TableModel();

        String max = "";

        for(int i = 0; i < 256; i++) {
            max += "a";
        }

        assertTrue(manager.nameCheck(max));
    }
    @Test
    public void nameCheckShort() {
        TableModel manager = new TableModel();

        assertFalse(manager.nameCheck("m"));
    }
    @Test
    public void nameCheckLong() {
        TableModel manager = new TableModel();
        
        String max = "";

        for(int i = 0; i < 257; i++) {
            max += "a";
        }

        assertFalse(manager.nameCheck(max));
    }

    @Test
    public void valueCheckWithDollarSign() {
        TableModel manager = new TableModel();

        assertTrue(manager.valueCheck("$10.00"));
    }
    @Test
    public void valueCheckWithoutDollarSign() {
        TableModel manager = new TableModel();

        assertTrue(manager.valueCheck("10.00"));
    }
    @Test
    public void valueCheckInt() {
        TableModel manager = new TableModel();

        assertTrue(manager.valueCheck("10"));
    }
    @Test
    public void valueCheckDouble() {
        TableModel manager = new TableModel();

        assertFalse(manager.valueCheck("10.001"));
    }
    @Test
    public void valueCheckNeg() {
        TableModel manager = new TableModel();

        assertFalse(manager.valueCheck("-10.00"));
    }
    @Test
    public void valueCheckMultiDollarSigns() {
        TableModel manager = new TableModel();

        assertFalse(manager.valueCheck("$$10"));
    }
}
