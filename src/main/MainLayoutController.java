package main;

import function.CellRange;
import function.CellReference;
import function.CellSingle;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import table.Cell;
import table.Sheet;
import table.SheetWindow;
import value.ComparableValue;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainLayoutController extends VBox implements Initializable {
    @FXML
    private AnchorPane sheetArea;
    @FXML
    private Button newButton;
    @FXML
    private Button openButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button saveAsButton;
    @FXML
    private Button chartButton;

    private SheetWindow sheetWindow;

    public MainLayoutController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainLayout.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public SheetWindow getSheetWindow() {
        return sheetWindow;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sheetWindow = new SheetWindow();
        sheetArea.getChildren().add(sheetWindow);
        AnchorPane.setTopAnchor(sheetWindow, 0.0);
        AnchorPane.setBottomAnchor(sheetWindow, 0.0);
        AnchorPane.setLeftAnchor(sheetWindow, 0.0);
        AnchorPane.setRightAnchor(sheetWindow, 0.0);
    }

    /**
     * Save file format:
     * line := [cell col]-[cell row]|[unprocessed value]|[value]|[dependants, ...]|[cell reference, ...]
     * cellreference := S[C]-[R] or R[C1]-[R1]-[C2]-[R2]
     * cell col := 0-based column index
     * cell row := 0-based row index
     * dependants := [C]-[R]
     *
     * @return
     */
    public void writeToFile(File file) {
        try {
            PrintWriter printWriter = new PrintWriter(file.getAbsolutePath());
            ArrayList<ArrayList<Cell>> cells = getSheetWindow().getSheet().getTable().getCells();
            for (ArrayList<Cell> cellArr : cells) {
                for (Cell cell : cellArr) {
                    if (cell != null) {
                        StringBuilder line = new StringBuilder();

                        //convert cellName
                        StringBuilder cellName = new StringBuilder();
                        cellName.append(GridPane.getColumnIndex(cell)).append("-").append(GridPane.getRowIndex(cell));
                        line.append(cellName).append("|");

                        //convert unprocessed value
                        line.append(cell.getUnprocessedValue()).append("|");

                        //convert value
                        line.append(cell.getValue() == null ? " " : cell.getValue().toString()).append("|");

                        //convert dependants
                        StringBuilder depString = new StringBuilder();
                        boolean first = true;
                        ArrayList<Cell> dependants = cell.getDependants();
                        if (dependants.isEmpty()) depString.append(" ");
                        else for (Cell dep : dependants) {
                            if (first) first = false;
                            else depString.append(",");
                            if (dep != null)
                                depString
                                        .append(GridPane.getColumnIndex(dep))
                                        .append("-")
                                        .append(GridPane.getRowIndex(dep));
                        }
                        line.append(depString).append("|");

                        //convert precedents
                        StringBuilder precString = new StringBuilder();
                        boolean first2 = true;
                        ArrayList<CellReference> precedents = cell.getPrecedents();
                        if (precedents.isEmpty()) precString.append(" ");
                        else for (CellReference ref : precedents) {
                            if (first2) first2 = false;
                            else precString.append(",");
                            if (ref instanceof CellSingle) {
                                precString
                                        .append("S")
                                        .append(((CellSingle) ref).getCol() - 1).append("-")
                                        .append(((CellSingle) ref).getRow() - 1);
                            } else if (ref instanceof CellRange) {
                                precString
                                        .append("R")
                                        .append(((CellRange) ref).getLeftCol() - 1).append("-")
                                        .append(((CellRange) ref).getTopRow() - 1).append("-")
                                        .append(((CellRange) ref).getRightCol() - 1).append("-")
                                        .append(((CellRange) ref).getBottomRow() - 1);
                            }
                        }
                        line.append(precString);
                        printWriter.println(line);
                    }
                }
            }
            printWriter.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error loading file");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void loadFile(File file) {
//        sheetArea.getChildren().remove(sheetWindow);
//        sheetWindow = new SheetWindow();
//        System.out.println("load called " + file.getName());
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            ArrayList<String[]> commands = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                System.out.println(Arrays.toString(line.split("[|]")));
                String[] elements = line.split("[|]");
                commands.add(elements);
            }

            //manually add cell one by one and set each content
            for (String[] command : commands) {
                int col = Integer.valueOf(command[0].split("-")[0]);
                int row = Integer.valueOf(command[0].split("-")[1]);
                sheetWindow.getSheet().getTable().addCell(col, row);
                Cell cell = sheetWindow.getSheet().getTable().getCells().get(col).get(row);
                cell.setUnprocessedValue(command[1]);
                cell.setValue(command[2].matches(" +") ? null : ComparableValue.valueOf(command[2]));
            }

            //manually add dependencies and precedents
            for (String[] command : commands) {
                int col = Integer.valueOf(command[0].split("-")[0]);
                int row = Integer.valueOf(command[0].split("-")[1]);
                Cell cell = sheetWindow.getSheet().getTable().getCells().get(col).get(row);
                if (!command[3].matches(" +")) {
                    String[] dependants = command[3].split(",");
                    for (String dependantString : dependants) {
                        int dcol = Integer.valueOf(dependantString.split("-")[0]);
                        int drow = Integer.valueOf(dependantString.split("-")[1]);
                        Cell dep = sheetWindow.getSheet().getTable().getCells().get(dcol).get(drow);
                        cell.addDependant(dep);
                    }
                }
                if (!command[4].matches(" +")) {
                    String[] precedents = command[4].split(",");
                    for (String precedentString : precedents) {
                        if(precedentString.charAt(0) == 'S'){
                            precedentString = precedentString.substring(1);
                            int pcol = Integer.valueOf(precedentString.split("-")[0]);
                            int prow = Integer.valueOf(precedentString.split("-")[1]);
                            cell.addPrecedent(new CellSingle(
                                    sheetWindow.getSheet().getTable(),
                                    pcol+1,
                                    prow+1));
                        } else if (precedentString.charAt(0) == 'R'){
                            precedentString = precedentString.substring(1);
                            int pcol1 = Integer.valueOf(precedentString.split("-")[0]);
                            int prow1 = Integer.valueOf(precedentString.split("-")[1]);
                            int pcol2 = Integer.valueOf(precedentString.split("-")[2]);
                            int prow2 = Integer.valueOf(precedentString.split("-")[3]);
                            cell.addPrecedent(new CellRange(
                                            sheetWindow.getSheet().getTable(),
                                            pcol1+1,
                                            prow1+1,
                                            pcol2+1,
                                            prow2+1));

                        }
                    }
                }
            }
//            sheetArea.getChildren().add(sheetWindow);
//            AnchorPane.setTopAnchor(sheetWindow, 0.0);
//            AnchorPane.setBottomAnchor(sheetWindow, 0.0);
//            AnchorPane.setLeftAnchor(sheetWindow, 0.0);
//            AnchorPane.setRightAnchor(sheetWindow, 0.0);
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("File not found!");
            alert.setContentText("Error loading " + file.getName());
            alert.showAndWait();
        } catch (IOException ignored) {
        }
    }

    public void newDocument() {

    }

    public AnchorPane getSheetArea() {
        return sheetArea;
    }

    public Button getNewButton() {
        return newButton;
    }

    public Button getOpenButton() {
        return openButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getSaveAsButton() {
        return saveAsButton;
    }

    public Button getChartButton() {
        return chartButton;
    }
}
