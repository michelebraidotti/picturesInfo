package pictures.info;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by michele on 2/16/16.
 */
public class PicturesInfoTableView extends TableView {

    public PicturesInfoTableView() {

        TableColumn<PictureDetails, String> fileNameCol = new TableColumn<>("FileName");
        fileNameCol.setMinWidth(100);
        fileNameCol.setCellValueFactory(new PropertyValueFactory<>("fileName"));

        TableColumn<PictureDetails, String> gpsLongCol = new TableColumn<>("GPS Longitude");
        gpsLongCol.setMinWidth(350);
        gpsLongCol.setCellValueFactory(new PropertyValueFactory<>("gpsLongitude"));

        getColumns().addAll(fileNameCol, gpsLongCol);
    }
}
