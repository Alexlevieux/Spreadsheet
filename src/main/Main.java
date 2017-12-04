package main;

import exception.ParserException;
import function.Evaluator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import value.NumberValue;


public class Main extends Application {
    private static MainWindow mainWindow;

    public static MainWindow getMainWindow() {
        return mainWindow;
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainWindow = new MainWindow();
        stage.setTitle("Spreadsheet");
        stage.setScene(new Scene(mainWindow, 1024, 768));
        stage.show();
    }
    public static void main(String[] args) throws ParserException {
        //System.out.println(new NumberValue(5));
        launch(args);
    }

}
