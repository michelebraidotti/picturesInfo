package pictures.info;

import com.drew.imaging.ImageProcessingException;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Iterator;

/**
 * Created by michele on 2/16/16.
 */
public class PicturesInfoStage extends Stage {
    private static final String MAIN_WINDOW_TITLE_TEXT = "Pictures info";
    private static final String FILE_MENU_TEXT = "File";
    private static final String OPEN_DIR_TEXT = "Open dir";
    private static final String HELP_MENU_TEXT = "Help";
    private static final java.lang.String EDIT_MENU = "Edit";
    private static final java.lang.String COPY_TEXT = "Copy";
    private final PicturesInfoTableView picturesInfoTableView;
    private final ObservableList<PictureDetails> picturesDataList;
    private final OpenFileInfoPane openFileInfoPane;
    private File picturesDirectory;

    public PicturesInfoStage() {
        // main pane
        GridPane root = new GridPane();
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(100);
        root.getColumnConstraints().addAll(column);
        int rowNumber = 0;

        // menu bar
        root.add(buildMenuBar(), 0, rowNumber++);

        //button bar
        root.add(buildToolBar(), 0, rowNumber++);

        // main marceTableView
        picturesInfoTableView = new PicturesInfoTableView();
        picturesInfoTableView.setEditable(true);
        picturesInfoTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        picturesDataList = FXCollections.observableArrayList();
        picturesInfoTableView.setItems(picturesDataList);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        picturesInfoTableView.setPrefHeight(700);
        vbox.getChildren().addAll(picturesInfoTableView);
        root.add(vbox, 0, rowNumber++);

        // bottom pane indicating the open file (or dir)
        openFileInfoPane = new OpenFileInfoPane();
        root.add(openFileInfoPane, 0, rowNumber++);

        setTitle(MAIN_WINDOW_TITLE_TEXT);
        setScene(new Scene(root, 1200, 800));
    }
    private ToolBar buildToolBar() {
        ToolBar toolBar = new ToolBar();

        Button buttonOpen = new Button(OPEN_DIR_TEXT, new ImageView(new Image("icons/folder_32.png")));
        buttonOpen.setContentDisplay(ContentDisplay.TOP);
        buttonOpen.setOnAction(new OpenAction());

        toolBar.getItems().addAll(buttonOpen);
        return toolBar;
    }

    private MenuBar buildMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu(FILE_MENU_TEXT);

        MenuItem openItem = new MenuItem(OPEN_DIR_TEXT);
        openItem.setOnAction(new OpenAction());

        menuFile.getItems().addAll(openItem);

        Menu menuEdit = new Menu(EDIT_MENU);

        MenuItem copyItem = new MenuItem(COPY_TEXT);
        copyItem.setOnAction(new CopyAction());

        menuEdit.getItems().addAll(copyItem);

        Menu menuHelp = new Menu(HELP_MENU_TEXT);
        menuBar.getMenus().addAll(menuFile, menuEdit, menuHelp);
        return menuBar;
    }

    private class CopyAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            copySelectionToClipboard(picturesInfoTableView);
        }
    }

    private void copySelectionToClipboard(TableView<?> table) {
        StringBuilder clipboardString = new StringBuilder();
        NumberFormat numberFormatter = NumberFormat.getNumberInstance();
        ObservableList<TablePosition> positionList = table.getSelectionModel().getSelectedCells();
        int prevRow = -1;
        for (TablePosition position : positionList) {
            int row = position.getRow();
            int col = position.getColumn();
            if (prevRow == row) {
                clipboardString.append('\t');
            } else if (prevRow != -1) {
                clipboardString.append('\n');
            }
            String text = "";
            Object observableValue = table.getColumns().get(col).getCellObservableValue( row);
            if (observableValue == null) {
                text = "";
            }
            else if( observableValue instanceof DoubleProperty) { // TODO: handle boolean etc
                text = numberFormatter.format( ((DoubleProperty) observableValue).get());
            }
            else if( observableValue instanceof IntegerProperty) {
                text = numberFormatter.format( ((IntegerProperty) observableValue).get());
            }
            else if( observableValue instanceof StringProperty) {
                text = ((StringProperty) observableValue).get();
            }
            else {
                System.out.println("Unsupported observable value: " + observableValue);
            }
            clipboardString.append(text);
            prevRow = row;
        }
        final ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(clipboardString.toString());
        Clipboard.getSystemClipboard().setContent(clipboardContent);
    }

    private class OpenAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select dir");
            directoryChooser.setInitialDirectory(
                    new File(System.getProperty("user.home"))
            );
            setPicturesDirectory(directoryChooser.showDialog(PicturesInfoStage.this));
            if (getPicturesDirectory() != null) {
                PicturesInfoModel picturesInfoModel= null;
                picturesDataList.removeAll();
                try {
                    picturesInfoModel = new PicturesInfoModel(getPicturesDirectory());
                    picturesDataList.addAll(picturesInfoModel.getPictureDetailsList());
                } catch (IOException|ImageProcessingException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage());
                    alert.show();
                }
            }
        }
    }

    private void setPicturesDirectory(File file) {
        picturesDirectory = file;
        openFileInfoPane.updateFilePathLabel(file.getAbsolutePath());
    }

    private File getPicturesDirectory() {
        return picturesDirectory;
    }

}