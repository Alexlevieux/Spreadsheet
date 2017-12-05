package main;

import exception.ParserException;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
    private static Window window;

    public static MainLayoutController getMainWindow() {
        return window.getMainWindow();
    }

    @Override
    public void start(Stage stage) throws Exception {
        MainLayoutController mainWindow = new MainLayoutController();
        window = new Window(mainWindow);
        window.setOnCloseRequest(e->{
            e.consume();
            boolean stop = window.quit();
            if(stop) try {
                stop();
            } catch (Exception ignored) {
            }
        });
    }

    public static void main(String[] args) throws ParserException {
        //System.out.println(new NumberValue(5));
        launch(args);
    }

    public static Window getWindow() {
        return window;
    }

    public static void setWindow(Window window) {
        Main.window = window;
    }
}
