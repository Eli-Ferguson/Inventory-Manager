/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Eli Ferguson
 */

package ucf.assignment5; 

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    
    private Map<String, Scene> Scenes = new HashMap<String, Scene>();
    private FXMLLoader loader;
    private Stage primary;

    public SceneManager(Stage Primary) {
        this.primary = Primary;
    }

    public void loader() {
        
        TableModel table = new TableModel();

        //Get instances of all the controllers
        homeScreenController HomeScreenController = new homeScreenController(table, this, primary);
        editTableController EditTableController = new editTableController(table, this, primary);
        searchTableController SearchTableController = new searchTableController(table, this, primary);

        Parent root;

        //load First Scene
        loader = new FXMLLoader(getClass().getResource("HomeScreen.fxml"));
        loader.setController(HomeScreenController);

        try {
            root = loader.load();
            Scenes.put("HomeScreen", new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
            //return false;
        }

        //Load Next Scene
        loader = new FXMLLoader(getClass().getResource("EditTable.fxml"));
        loader.setController(EditTableController);

        try {
            root = loader.load();
            Scenes.put("EditTable", new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
            //return false;
        }

        //load next Scene
        loader = new FXMLLoader(getClass().getResource("SearchTable.fxml"));
        loader.setController(SearchTableController);

        try {
            root = loader.load();
            Scenes.put("SearchTable", new Scene(root));

            //return true;
        } catch (Exception e) {
            e.printStackTrace();
            //return false;
        }
    }

    public Scene getScene(String sceneName) {
        return Scenes.get(sceneName);
    }
}
