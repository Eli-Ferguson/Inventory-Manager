/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Eli Ferguson
 */

package ucf.assignment5;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class homeScreenController implements Initializable{
    
    private SceneManager manager;
    private Stage stage;
    private TableModel model;

    @FXML TextField valueAddItem;
    @FXML TextField serialNumberAddItem;
    @FXML TextField nameAddItem;

    @FXML TableView<listItem> wholeTableObject;

    @FXML TableColumn<listItem, String> valueColumn;
    @FXML TableColumn<listItem, String> serialNumberColumn;
    @FXML TableColumn<listItem, String> nameColumn;    

    public homeScreenController(TableModel Table, SceneManager Manager, Stage Primary) {
        this.model = Table;
        this.manager = Manager;
        this.stage = Primary;
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //wholeTableObject.getColumns().addAll(valueColumn, serialNumberColumn, nameColumn);

        valueColumn.setCellValueFactory(new PropertyValueFactory<listItem, String>("value"));
        serialNumberColumn.setCellValueFactory(new PropertyValueFactory<listItem, String>("serialNumber"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<listItem, String>("name"));

        updateTable();

        model.initHome(this);
    }

    @FXML
    public void HomeScreenButton(ActionEvent actionEvent) {
        System.out.println("Home Screen");
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
        stage.setTitle("Search Table");
        stage.setScene(manager.getScene("SearchTable"));
        stage.show();
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

    private void updateTable() {
        wholeTableObject.setItems(model.tableData);
    }
}
