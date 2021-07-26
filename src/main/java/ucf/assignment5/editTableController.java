/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Eli Ferguson
 */

 package ucf.assignment5;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class editTableController implements Initializable{

    private SceneManager manager;
    private Stage stage;
    private TableModel model;

    @FXML TextField NameBox;
    @FXML TextField SerialBox;
    @FXML TextField ValueBox;

    @FXML TableView<listItem> wholeTableObject;

    @FXML TableColumn<listItem, String> valueColumn;
    @FXML TableColumn<listItem, String> serialNumberColumn;
    @FXML TableColumn<listItem, String> nameColumn;

    @FXML Button TopButton;
    @FXML Button bottomLeftButton;
    @FXML Button bottomRightButton;    

    public editTableController(TableModel Table, SceneManager Manager, Stage Primary) {
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

        model.initEdit(this);
    }

    @FXML
    public void HomeScreenButton(ActionEvent actionEvent) {

        stage.setTitle("Home");
        stage.setScene(manager.getScene("HomeScreen"));
        stage.show();
    }

    @FXML
    public void EditTableButton(ActionEvent actionEvent) {
        System.out.println("Edit Table");
    }

    @FXML
    public void SearchTableButton(ActionEvent actionEvent) {

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

    public void addItemButton(ActionEvent actionEvent) {
        System.out.println("AddItem");
        
        model.addItemCheck();

        updateTable();

        blank();
    }

    public void removeItemSerialButton(ActionEvent actionEvent) {
        System.out.println("Remove Item By Serial");

        model.removeItemBySerialCheck();

        updateTable();

        blank();
    }

    public void removeItemNameButton(ActionEvent actionEvent) {
        System.out.println("Remove Item By Serial");

        model.removeItemByNameCheck();

        updateTable();

        blank();
    }

    public void removeItemSelectedButton(ActionEvent actionEvent) {
        System.out.println("Remove Item By Serial");

        model.removeItemBySelected();

        updateTable();

        blank();

        Deselected();
    }
    
    public void updateSelectedItem(ActionEvent actionEvent) {
        System.out.println("Update Selected");

        model.updateItemCheck();

        updateTable();

        blank();

        Deselected();
    }

    public void deselectItemButton(ActionEvent actionEvent) {
        System.out.println("Deselect Item");

        blank();

        Deselected();
    }

    public void clickTableItem(MouseEvent event) {
        if(event.getClickCount() == 2) {

            Selected();

            System.out.println("Double-Clicked");

            listItem updateItem = wholeTableObject.getSelectionModel().getSelectedItem();

            NameBox.setText(updateItem.getName());
            SerialBox.setText(updateItem.getSerialNumber());
            ValueBox.setText(updateItem.getValue());
            
        }
    }

    private void topButtonManager(String in) {

        if(in.equals("Update")){
            TopButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    updateSelectedItem(event);
                }
            });

            TopButton.setText("Update Item");
        } else if(in.equals("Add")) {
            TopButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    addItemButton(event);
                }
            });

            TopButton.setText("Add New Item");
        }
    }

    private void bottomLeftButtonManager(String in) {

        if(in.equals("Remove Serial")){
            bottomLeftButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    removeItemSerialButton(event);
                }
            });
            bottomLeftButton.setText("Serial Number");

        } else if(in.equals("Remove Selected")) {
            bottomLeftButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    removeItemSelectedButton(event);
                }
            });

            bottomLeftButton.setText("Selected Item");
        }
    }

    private void bottomRightButtonManager(String in) {

        if(in.equals("Remove Name")){
            bottomRightButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    removeItemNameButton(event);
                }
            });
            bottomRightButton.setText("Name");

        } else if(in.equals("Deselect")) {
            bottomRightButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    deselectItemButton(event);
                }
            });

            bottomRightButton.setText("Deselect");
        }
    }

    private void updateTable() {
        wholeTableObject.setItems(model.tableData);
    }

    private void blank() {
        ValueBox.setText("");
        SerialBox.setText("");
        NameBox.setText("");
    }

    private void Selected() {
        topButtonManager("Update");
        bottomLeftButtonManager("Remove Selected");
        bottomRightButtonManager("Deselect");
    }

    private void Deselected() {
        topButtonManager("Add");
        bottomLeftButtonManager("Remove Serial");
        bottomRightButtonManager("Remove Name");
    }
}
