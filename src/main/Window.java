package main;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import table.Cell;
import table.Sheet;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Window extends Stage {
    private Scene scene;
    private MainLayoutController mainLayout;
    private boolean saved;
    private File file;

    public Window(MainLayoutController controller) {
        mainLayout = controller;
        scene = new Scene(controller);
        setSaved(true);
        getIcons().add(new Image("/assets/program.png"));
        setTitle("Spreadsheet - Untitled");
        setScene(scene);
        initialize();
        show();
    }

    public MainLayoutController getMainWindow() {
        return mainLayout;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public void save() {
        if (file != null) {
            mainLayout.writeToFile(file);
            setSaved(true);
        } else saveAs();
    }

    public void saveAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Spreadsheet file", Sheet.EXTENSION));
        if (file != null) fileChooser.setInitialDirectory(file.getParentFile());
        fileChooser.setTitle("Save As");
        File file = fileChooser.showSaveDialog(this);
        System.out.println(file == null ? "null" : file.getName());
        if (file != null) {
            setSaved(true);
            mainLayout.writeToFile(file);
            this.file = file;
            setTitle("Spreadsheet - [" + this.file.getParent() + "] - " + this.file.getName());
        }
    }

    public void open() {
        if (!saved) {
            Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "" +
                            "Do you want to save " +
                            (file == null ? "Untitled" : file.getName()) +
                            " before opening another file?",
                    ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.setTitle("Save file?");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                save();
            } else if (result.get() == ButtonType.NO) {
            } else if (result.get() == ButtonType.CANCEL) {
                alert.close();
                return;
            }
        }
        FileChooser fileChooser = new FileChooser();
        String extension = Sheet.EXTENSION;
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Spreadsheet file", extension)
        );
        if (file != null)
            fileChooser.setInitialDirectory(file.getParentFile());
        fileChooser.setTitle("Open");
        File file = fileChooser.showOpenDialog(this);
        System.out.println("file chosen");
        if (file != null) {
            this.file = file;
            mainLayout = new MainLayoutController();
            mainLayout.loadFile(file);
            scene = new Scene(mainLayout);
            setTitle("Spreadsheet - [" + this.file.getParent() + "] - " + this.file.getName());
            setScene(scene);
            initialize();
        }
    }

    public void newFile() {
        if (!saved) {
            Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "" +
                            "Do you want to save " +
                            (file == null ? "Untitled" : file.getName()) +
                            " before creating a new file?",
                    ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.setTitle("Save file?");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                save();
            } else if (result.get() == ButtonType.NO) {
            } else if (result.get() == ButtonType.CANCEL) {
                alert.close();
                return;
            }
        }
        this.file = null;
        mainLayout = new MainLayoutController();
        scene = new Scene(mainLayout);
        setTitle("Spreadsheet - Untitled");
        setScene(scene);
        initialize();
    }

    public void createChart() {

    }

    public boolean quit() {
        if (!saved) {
            Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "" +
                            "Do you want to save " +
                            (file == null ? "Untitled" : file.getName()) +
                            " before exiting?",
                    ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.setTitle("Save file?");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                save();
                close();
                return true;
            } else if (result.get() == ButtonType.NO) {
                close();
                return true;
            } else if (result.get() == ButtonType.CANCEL) {
                alert.close();
                return false;
            } else {
                return false;
            }
        } else {
            close();
            return true;
        }
    }

    private void initialize() {
        setResizable(true);
        setMaximized(false);
        setMaximized(true);
        setResizable(false);
        mainLayout.getChartButton().setOnAction(e -> createChart());
        mainLayout.getOpenButton().setOnAction(e -> open());
        mainLayout.getSaveButton().setOnAction(e -> save());
        mainLayout.getSaveAsButton().setOnAction(e -> saveAs());
        mainLayout.getNewButton().setOnAction(e -> newFile());
        scene.focusOwnerProperty().addListener((src, ov, nv) -> {
            if (nv instanceof Cell) {
                mainLayout.getSheetWindow().getSheet().getTable().setSelectedRow(GridPane.getRowIndex(nv));
                mainLayout.getSheetWindow().getSheet().getTable().setSelectedColumn(GridPane.getColumnIndex(nv));
            } else {
                mainLayout.getSheetWindow().getSheet().getTable().setSelectedRow(-1);
                mainLayout.getSheetWindow().getSheet().getTable().setSelectedColumn(-1);
            }
        });
    }

}
