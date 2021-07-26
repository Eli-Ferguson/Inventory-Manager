/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Eli Ferguson
 */

package ucf.assignment5;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class searchTableController implements Initializable{
    
    private SceneManager manager;
    private Stage stage;
    private TableModel model;

    @FXML TextField existingSerialNumber;
    @FXML TextField existingName;

    @FXML TableView<listItem> wholeTableObject;

    @FXML TableColumn<listItem, String> valueColumn;
    @FXML TableColumn<listItem, String> serialNumberColumn;
    @FXML TableColumn<listItem, String> nameColumn;  

    public searchTableController(TableModel Table, SceneManager Manager, Stage Primary) {
        this.model = Table;
        this.manager = Manager;
        this.stage = Primary;
        //
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //wholeTableObject.getColumns().addAll(valueColumn, serialNumberColumn, nameColumn);

        valueColumn.setCellValueFactory(new PropertyValueFactory<listItem, String>("value"));
        serialNumberColumn.setCellValueFactory(new PropertyValueFactory<listItem, String>("serialNumber"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<listItem, String>("name"));
        
        updateTable();

        model.initSearch(this);

        existingSerialNumber.textProperty().addListener((obserable, oldText, NewText) -> {
            dynamicSearchSerial();
        });
        existingName.textProperty().addListener((obserable, oldText, NewText) -> {
            dynamicSearchName();
        });
    }

    @FXML
    public void HomeScreenButton(ActionEvent actionEvent) {

        System.out.println("Home Screen");
        stage.setTitle("Home");
        stage.setScene(manager.getScene("HomeScreen"));
        stage.show();
    }

    @FXML
    public void EditTableButton(ActionEvent actionEvent) {

        System.out.println("Edit Table");
        stage.setTitle("Edit Table");
        stage.setScene(manager.getScene("EditTable"));
        stage.show();
    }

    @FXML
    public void SearchTableButton(ActionEvent actionEvent) {
        System.out.println("Search Table");
    }

    public void saveFile(ActionEvent actionEvent) {
        System.out.println("Save File");

        model.saveFile();

        updateTable();
    }

    public void loadFile(ActionEvent actionEvent) {
        System.out.println("Load File");

        model.loadFile();

        updateTable();
    }

    public void help(ActionEvent actionEvent) {
        System.out.println("Help");

        model.help();
    }

    public void searchBySerialButton(ActionEvent actionEvent) {
        System.out.println("Search by Serial");

        changeTableToFoundSerials();

        blank();
    }

    public void searchByNameButton(ActionEvent actionEvent) {
        System.out.println("Search by Name");

        changeTableToFoundNames();

        blank();
    }

    private void dynamicSearchSerial() {
        changeTableToFoundSerials();
    }

    private void dynamicSearchName() {
        changeTableToFoundNames();
    }

    public void ViewAllButton(ActionEvent actionEvent) {
        System.out.println("View All");

        updateTable();

        blank();
    }

    private void changeTableToFoundSerials() {
        ObservableList<listItem> searchData = FXCollections.observableArrayList();

        searchData = model.getItemsBySerial();

        if(searchData.size() == 0) {
            updateTable();
        } else {
            wholeTableObject.setItems(searchData);
        }
    }

    private void changeTableToFoundNames() {
        ObservableList<listItem> searchData = FXCollections.observableArrayList();

        searchData = model.getItemsByName();

        if(searchData.size() == 0) {
            updateTable();
        } else {
            wholeTableObject.setItems(searchData);
        }
    }

    private void updateTable() {
        wholeTableObject.setItems(model.tableData);
    }

    private void blank() {
        existingName.setText("");
        existingSerialNumber.setText("");
    }
}
