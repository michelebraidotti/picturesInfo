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

        TableColumn<PictureDetails, String> gpsTimeStampCol = new TableColumn<>("GPS Time-Stamp");
        gpsTimeStampCol.setMinWidth(150);
        gpsTimeStampCol.setCellValueFactory(new PropertyValueFactory<>("gpsTimeStamp"));

        TableColumn<PictureDetails, String> gpsProcessingMethodCol = new TableColumn<>("GPS Processing Method");
        gpsProcessingMethodCol.setMinWidth(150);
        gpsProcessingMethodCol.setCellValueFactory(new PropertyValueFactory<>("gpsProcessingMethod"));

        TableColumn<PictureDetails, String> gpsLongitudeDegreesCol = new TableColumn<>("GPS Longitude (Degrees)");
        gpsLongitudeDegreesCol.setMinWidth(150);
        gpsLongitudeDegreesCol.setCellValueFactory(new PropertyValueFactory<>("gpsLongitudeDegrees"));

        TableColumn<PictureDetails, String> gpsLongitudeCol = new TableColumn<>("GPS Longitude");
        gpsLongitudeCol.setMinWidth(150);
        gpsLongitudeCol.setCellValueFactory(new PropertyValueFactory<>("gpsLongitude"));

        TableColumn<PictureDetails, String> gpsLongitudeRefCol = new TableColumn<>("GPS Longitude Ref");
        gpsLongitudeRefCol.setMinWidth(150);
        gpsLongitudeRefCol.setCellValueFactory(new PropertyValueFactory<>("gpsLongitudeRef"));

        TableColumn<PictureDetails, String> gpsLatitudeDegreesCol = new TableColumn<>("GPS Latitude (Degrees)");
        gpsLatitudeDegreesCol.setMinWidth(150);
        gpsLatitudeDegreesCol.setCellValueFactory(new PropertyValueFactory<>("gpsLatitudeDegrees"));

        TableColumn<PictureDetails, String> gpsLatitudeCol = new TableColumn<>("GPS Latitude");
        gpsLatitudeCol.setMinWidth(150);
        gpsLatitudeCol.setCellValueFactory(new PropertyValueFactory<>("gpsLatitude"));

        TableColumn<PictureDetails, String> gpsLatitudeRefCol = new TableColumn<>("GPS Latitude Ref");
        gpsLatitudeRefCol.setMinWidth(150);
        gpsLatitudeRefCol.setCellValueFactory(new PropertyValueFactory<>("gpsLatitudeRef"));

        TableColumn<PictureDetails, String> gpsAltitudeCol = new TableColumn<>("GPS Altitude");
        gpsAltitudeCol.setMinWidth(150);
        gpsAltitudeCol.setCellValueFactory(new PropertyValueFactory<>("gpsAltitude"));

        TableColumn<PictureDetails, String> gpsAltitudeRefCol = new TableColumn<>("GPS Altitude Ref");
        gpsAltitudeRefCol.setMinWidth(150);
        gpsAltitudeRefCol.setCellValueFactory(new PropertyValueFactory<>("gpsAltitudeRef"));

        TableColumn<PictureDetails, String> gpsDateStamp = new TableColumn<>("GPS Date Stamp");
        gpsDateStamp.setMinWidth(150);
        gpsDateStamp.setCellValueFactory(new PropertyValueFactory<>("gpsDateStamp"));

        getColumns().addAll(fileNameCol, gpsDateStamp, gpsTimeStampCol, gpsProcessingMethodCol,
                gpsLongitudeDegreesCol, gpsLongitudeCol, gpsLongitudeRefCol,
                gpsLatitudeDegreesCol, gpsLatitudeCol, gpsLatitudeRefCol,
                gpsAltitudeCol, gpsAltitudeRefCol);
    }
}
