package main;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Main extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        MainWindow mainWindow = new MainWindow();
        stage.setTitle("Spreadsheet");
        stage.setScene(new Scene(mainWindow, 1024, 768));
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}
