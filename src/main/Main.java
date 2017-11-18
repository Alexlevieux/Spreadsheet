package main;

import function.Evaluator;
import function.ParserException;
import function.Value;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Main /*extends Application*/{
//    @Override
//    public void start(Stage stage) throws Exception {
//
//    }
    public static void main(String[] args) throws ParserException{
        System.out.println(Evaluator.evaluate("sum()+sum(5*6,5+7,average(5,4))"));
//        launch(args);
    }

}
