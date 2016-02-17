package pictures.info;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Created by michele on 2/17/16.
 */
public class OpenFileInfoPane extends GridPane {

    private final Label openFilePathLabel;

    public OpenFileInfoPane() {
        GridPane bottomPane = new GridPane();
        bottomPane.setMaxHeight(90);
        bottomPane.setPadding(new Insets(0, 10, 10, 10));
        Label openFileLabel = new Label("Open file: ");
        bottomPane.add(openFileLabel, 0, 0);
        openFilePathLabel = new Label("No file");
        bottomPane.add(openFilePathLabel, 1, 0);
    }

    public void updateFilePathLabel(String filePathLabel) {
        openFilePathLabel.setText(filePathLabel == null? "No file" : filePathLabel);
    }
}
