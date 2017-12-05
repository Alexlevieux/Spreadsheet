package main;

import chart.ChartArea;
import exception.ParserException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    private static SheetWindow sheetWindow;

    public static SheetWindow getSheetWindow() {
        return sheetWindow;
    }

    @Override
    public void start(Stage stage) throws Exception {
        sheetWindow = new SheetWindow();
        stage.setTitle("Spreadsheet");
        stage.setScene(new Scene(sheetWindow));
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.show();

        ChartArea ca = new ChartArea();
        Stage sStage = new Stage();
        sStage.setTitle("Edit Chart");
        sStage.setScene(new Scene(ca));
        sStage.show();
    }
    public static void main(String[] args) throws ParserException {
        //System.out.println(new NumberValue(5));
        launch(args);
    }

}
