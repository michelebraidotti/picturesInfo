package pictures.info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michele on 2/17/16.
 */
public class PictureDetails {
    private String fileName;
    private String gpsLongitude;
    private List<Exception> errors = new ArrayList<>();

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(String gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public void addError(Exception error) {
        this.errors.add(error);
    }

    public String getErrorsLocalizedMessages() {
        StringBuilder out = new StringBuilder();
        for (Exception error:errors) {
            out.append(error.getLocalizedMessage());
            out.append("**************************");
        }
        return out.toString();
    }
}
