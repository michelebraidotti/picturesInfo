package pictures.info;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michele on 2/17/16.
 */
public class PictureDetails {
    private String fileName;
    private String gpsTimeStamp = "NA";
    private String gpsProcessingMethod = "NA";
    private String gpsLongitude = "NA";
    private String gpsLatitudeRef = "NA";
    private String gpsAltitudeRef = "NA";
    private String gpsLatitude = "NA";
    private String gpsAltitude = "NA";
    private String gpsDateStamp = "NA";
    private String gpsLongitudeRef = "NA";

    private List<Exception> errors = new ArrayList<>();

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getGpsTimeStamp() {
        return gpsTimeStamp;
    }

    public void setGpsTimeStamp(String gpsTimeStamp) {
        this.gpsTimeStamp = gpsTimeStamp;
    }

    public String getGpsProcessingMethod() {
        return gpsProcessingMethod;
    }

    public void setGpsProcessingMethod(String gpsProcessingMethod) {
        this.gpsProcessingMethod = gpsProcessingMethod;
    }

    public String getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(String gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public String getGpsLatitudeRef() {
        return gpsLatitudeRef;
    }

    public void setGpsLatitudeRef(String gpsLatitudeRef) {
        this.gpsLatitudeRef = gpsLatitudeRef;
    }

    public String getGpsAltitudeRef() {
        return gpsAltitudeRef;
    }

    public void setGpsAltitudeRef(String gpsAltitudeRef) {
        this.gpsAltitudeRef = gpsAltitudeRef;
    }

    public String getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(String gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public String getGpsAltitude() {
        return gpsAltitude;
    }

    public void setGpsAltitude(String gpsAltitude) {
        this.gpsAltitude = gpsAltitude;
    }

    public String getGpsDateStamp() {
        return gpsDateStamp;
    }

    public void setGpsDateStamp(String gpsDateStamp) {
        this.gpsDateStamp = gpsDateStamp;
    }

    public String getGpsLongitudeRef() {
        return gpsLongitudeRef;
    }

    public void setGpsLongitudeRef(String gpsLongitudeRef) {
        this.gpsLongitudeRef = gpsLongitudeRef;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PictureDetails that = (PictureDetails) o;

        return new EqualsBuilder()
                .append(fileName, that.fileName)
                .append(gpsTimeStamp, that.gpsTimeStamp)
                .append(gpsProcessingMethod, that.gpsProcessingMethod)
                .append(gpsLongitude, that.gpsLongitude)
                .append(gpsLatitudeRef, that.gpsLatitudeRef)
                .append(gpsAltitudeRef, that.gpsAltitudeRef)
                .append(gpsLatitude, that.gpsLatitude)
                .append(gpsAltitude, that.gpsAltitude)
                .append(gpsDateStamp, that.gpsDateStamp)
                .append(gpsLongitudeRef, that.gpsLongitudeRef)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(fileName)
                .append(gpsTimeStamp)
                .append(gpsProcessingMethod)
                .append(gpsLongitude)
                .append(gpsLatitudeRef)
                .append(gpsAltitudeRef)
                .append(gpsLatitude)
                .append(gpsAltitude)
                .append(gpsDateStamp)
                .append(gpsLongitudeRef)
                .toHashCode();
    }
}
