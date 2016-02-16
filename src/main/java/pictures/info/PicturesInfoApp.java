package pictures.info;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by michele on 2/16/16.
 */
public class PicturesInfoApp extends Application {


    public static void main( String[] args ) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage = new PicturesInfoStage();
        primaryStage.show();
    }
}
