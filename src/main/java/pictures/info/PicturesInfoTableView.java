package pictures.info;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by michele on 2/16/16.
 */
public class PicturesInfoTableView extends TableView {

    public PicturesInfoTableView() {

        TableColumn fileNameCol = new TableColumn("FileName");
        fileNameCol.setMinWidth(100);
        fileNameCol.setCellValueFactory(new PropertyValueFactory("fileName"));

        TableColumn imageWidthCol = new TableColumn("ImageWidth");
        imageWidthCol.setMinWidth(350);
        imageWidthCol.setCellValueFactory(new PropertyValueFactory("imageWidth"));

        getColumns().addAll(fileNameCol, imageWidthCol);
    }
}
