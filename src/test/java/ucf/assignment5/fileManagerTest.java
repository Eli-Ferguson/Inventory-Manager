package ucf.assignment5;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class fileManagerTest {
    
    private ObservableList<listItem> TestList = FXCollections.observableArrayList();

    private File file;

    private void resetTestList(String type) {

        ObservableList<listItem> tmpList = FXCollections.observableArrayList();

        for(int i = 0; i < 3; i++) {
            String value = "$" + i*10 + ".00";
            String name = "Test " + i;
            String serial = "" + i;

            listItem newItem = new listItem(value, name, serial);

            tmpList.add(newItem);
        }

        TestList.setAll(tmpList);

        try {
            file = File.createTempFile("tmp", type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void savingAFileJSON() {
        resetTestList(".json");
        FileManager manager = new FileManager(TestList);
        manager.setCurrentFile(file);

        boolean expected = manager.listSaver();

        file.delete();

        assertTrue(expected);
    }

    @Test
    public void savingAFileTSV() {
        resetTestList(".txt");
        FileManager manager = new FileManager(TestList);
        manager.setCurrentFile(file);

        boolean expected = manager.listSaver();

        file.delete();

        assertTrue(expected);
    }

    @Test
    public void savingAFileHTML() {
        resetTestList(".html");
        FileManager manager = new FileManager(TestList);
        manager.setCurrentFile(file);

        boolean expected = manager.listSaver();

        file.delete();

        assertTrue(expected);
    }

    @Test
    public void savingAFileIncorrectFileType() {
        resetTestList(".jpeg");
        FileManager manager = new FileManager(TestList);
        manager.setCurrentFile(file);

        boolean expected = manager.listSaver();

        file.delete();

        assertFalse(expected);
    }

    @Test
    public void loadHTML() {
        resetTestList(".html");
        FileManager manager = new FileManager(TestList);
        manager.setCurrentFile(file);
        manager.listSaver();

        ObservableList<listItem> expected = FXCollections.observableArrayList();

        expected.setAll(manager.listLoader());

        file.delete();

        boolean e = true;

        for(int i = 0; i < expected.size(); i++) {

            if(!expected.get(i).getName().equals(TestList.get(i).getName())) {e = false; break;}
            if(!expected.get(i).getSerialNumber().equals(TestList.get(i).getSerialNumber())) {e = false; break;}
            if(!expected.get(i).getValue().equals(TestList.get(i).getValue())) {e = false; break;}
        }

        assertTrue(e);
    }

    @Test
    public void loadTSV() {
        resetTestList(".txt");
        FileManager manager = new FileManager(TestList);
        manager.setCurrentFile(file);
        manager.listSaver();

        ObservableList<listItem> expected = FXCollections.observableArrayList();

        expected.setAll(manager.listLoader());

        file.delete();

        boolean e = true;

        for(int i = 0; i < expected.size(); i++) {

            if(!expected.get(i).getName().equals(TestList.get(i).getName())) {e = false; break;}
            if(!expected.get(i).getSerialNumber().equals(TestList.get(i).getSerialNumber())) {e = false; break;}
            if(!expected.get(i).getValue().equals(TestList.get(i).getValue())) {e = false; break;}
        }

        assertTrue(e);
    }

    @Test
    public void loadJSON() {
        resetTestList(".json");
        FileManager manager = new FileManager(TestList);
        manager.setCurrentFile(file);
        manager.listSaver();

        ObservableList<listItem> expected = FXCollections.observableArrayList();

        expected.setAll(manager.listLoader());

        file.delete();

        boolean e = true;

        for(int i = 0; i < expected.size(); i++) {

            if(!expected.get(i).getName().equals(TestList.get(i).getName())) {e = false; break;}
            if(!expected.get(i).getSerialNumber().equals(TestList.get(i).getSerialNumber())) {e = false; break;}
            if(!expected.get(i).getValue().equals(TestList.get(i).getValue())) {e = false; break;}
        }

        assertTrue(e);
    }

}
