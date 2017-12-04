package main;

import exception.ParserException;
import function.Evaluator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import value.NumberValue;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        MainWindow mainWindow = new MainWindow();
        stage.setTitle("Spreadsheet");
        stage.setScene(new Scene(mainWindow, 1024, 768));
        stage.show();
    }
    public static void main(String[] args) throws ParserException {
        //System.out.println(new NumberValue(5));
        launch(args);
    }

}
