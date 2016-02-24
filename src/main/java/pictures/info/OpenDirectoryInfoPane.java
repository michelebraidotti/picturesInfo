package pictures.info;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Created by michele on 2/17/16.
 */
public class OpenDirectoryInfoPane extends HBox {

    private final Label openDirectoryPathLabel;

    public OpenDirectoryInfoPane() {
        super();
        setPadding(new Insets(0, 10, 10, 10));
        setSpacing(10);
        openDirectoryPathLabel = new Label("No directory selected");
        getChildren().addAll(new Label("Current directory: "), openDirectoryPathLabel);
    }

    public void updateDirectoryPathLabel(String dirPathLabel) {
        openDirectoryPathLabel.setText(dirPathLabel == null? "No directory selected" : dirPathLabel);
    }
}
