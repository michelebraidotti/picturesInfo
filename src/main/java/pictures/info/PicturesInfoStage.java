package pictures.info;

import com.drew.imaging.ImageProcessingException;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by michele on 2/16/16.
 */
public class PicturesInfoStage extends Stage {
    private static final String MAIN_WINDOW_TITLE_TEXT = "Pictures info";
    private static final String FILE_MENU_TEXT = "File";
    private static final String OPEN_DIR_TEXT = "Open dir";
    private static final String HELP_MENU_TEXT = "Help";
    private static final java.lang.String EDIT_MENU = "Edit";
    private static final java.lang.String COPY_TEXT = "Copy selected rows to clipboard";
    private static final java.lang.String SAVE_TEXT = "Save selected rows to file";
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
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        picturesInfoTableView.setPrefHeight(700);
        vbox.getChildren().addAll(picturesInfoTableView);
        root.add(vbox, 0, rowNumber++);

        // bottom pane indicating the open file (or dir)
        openFileInfoPane = new OpenFileInfoPane();
        VBox vboxFilePane = new VBox();
        vboxFilePane.setSpacing(5);
        vboxFilePane.setPadding(new Insets(10, 10, 10, 10));
        vboxFilePane.getChildren().addAll(openFileInfoPane);
        root.add(vboxFilePane, 0, rowNumber++);

        setTitle(MAIN_WINDOW_TITLE_TEXT);
        setScene(new Scene(root, 1200, 800));
    }

    private ToolBar buildToolBar() {
        ToolBar toolBar = new ToolBar();

        Button buttonOpen = new Button(OPEN_DIR_TEXT, new ImageView(new Image("icons/folder_32.png")));
        buttonOpen.setContentDisplay(ContentDisplay.TOP);
        buttonOpen.setOnAction(new OpenAction());

        Button buttonCopy = new Button(COPY_TEXT, new ImageView(new Image("icons/clipboard_32.png")));
        buttonCopy.setContentDisplay(ContentDisplay.TOP);
        buttonCopy.setOnAction(new CopyAction());

        Button buttonSave = new Button(SAVE_TEXT, new ImageView(new Image("icons/save_32.png")));
        buttonSave.setContentDisplay(ContentDisplay.TOP);
        buttonSave.setOnAction(new SaveAction());

        toolBar.getItems().addAll(buttonOpen, buttonCopy, buttonSave);
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

        MenuItem editItem = new MenuItem(SAVE_TEXT);
        editItem.setOnAction(new SaveAction());

        menuEdit.getItems().addAll(copyItem, editItem);

        Menu menuHelp = new Menu(HELP_MENU_TEXT);
        menuBar.getMenus().addAll(menuFile, menuEdit, menuHelp);
        return menuBar;
    }

    private class SaveAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");
            fileChooser.setInitialDirectory(
                    new File(System.getProperty("user.home"))
            );
            fileChooser.setInitialFileName(".csv");
            File outFile = fileChooser.showSaveDialog(PicturesInfoStage.this);
            try {
                FileWriter fStream = new FileWriter(outFile.getAbsolutePath());
                BufferedWriter out = new BufferedWriter(fStream);
                List<PictureDetails> selectedPictureDetails = picturesInfoTableView.getSelectionModel().getSelectedItems();
                PicturesInfoManager picturesInfoManager = new PicturesInfoManager(getPicturesDirectory());
                out.write(picturesInfoManager.toCsvString(selectedPictureDetails));
                out.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, selectedPictureDetails.size() + " rows written to file");
                alert.show();
            } catch (IOException|ImageProcessingException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage());
                alert.show();
            }
        }
    }

    private class CopyAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            try {
                List<PictureDetails> selectedPictureDetails = picturesInfoTableView.getSelectionModel().getSelectedItems();
                PicturesInfoManager picturesInfoManager = new PicturesInfoManager(getPicturesDirectory());
                String csvInfo = picturesInfoManager.toCsvString(selectedPictureDetails);
                final ClipboardContent clipboardContent = new ClipboardContent();
                clipboardContent.putString(csvInfo);
                Clipboard.getSystemClipboard().setContent(clipboardContent);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, selectedPictureDetails.size() + " rows copied");
                alert.show();
            } catch (IOException|ImageProcessingException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage());
                alert.show();
            }
        }
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
                PicturesInfoManager picturesInfoManager = null;
                picturesDataList.removeAll();
                try {
                    picturesInfoManager = new PicturesInfoManager(getPicturesDirectory());
                    picturesDataList.addAll(picturesInfoManager.getPictureDetailsList());
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