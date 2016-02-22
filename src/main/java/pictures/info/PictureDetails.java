package pictures.info;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by michele on 2/17/16.
 */
public class PictureDetails {
    private String fileName;
    private String gpsTimeStamp = "NA";
    private String gpsProcessingMethod = "NA";
    private String gpsLongitudeDegrees = "NA";
    private String gpsLatitudeRef = "NA";
    private String gpsAltitudeRef = "NA";
    private String gpsLatitudeDegrees = "NA";
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

    public String getGpsLongitudeDegrees() {
        return gpsLongitudeDegrees;
    }

    public void setGpsLongitudeDegrees(String gpsLongitudeDegrees) {
        this.gpsLongitudeDegrees = gpsLongitudeDegrees;
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

    public String getGpsLatitudeDegrees() {
        return gpsLatitudeDegrees;
    }

    public void setGpsLatitudeDegrees(String gpsLatitudeDegrees) {
        this.gpsLatitudeDegrees = gpsLatitudeDegrees;
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

    public String getGpsLatitude() {
        return convertCoordinates(gpsLatitudeDegrees);
    }

    public String getGpsLongitude() {
        return convertCoordinates(gpsLongitudeDegrees);
    }

    private String convertCoordinates(String oldCoordinates) {
        String newCoordinates = "??";
        // 45° 37' 42.29""
        Pattern pattern = Pattern.compile("(\\d\\d)° (\\d\\d)' (\\d\\d.\\d\\d)\"");
        Matcher matcher = pattern.matcher(oldCoordinates);
        if ( matcher.find() ) {
            Float degrees = Float.parseFloat(matcher.group(1));
            Float minutes = Float.parseFloat(matcher.group(2));
            Float seconds = Float.parseFloat(matcher.group(3));
            degrees += minutes/60;
            degrees += seconds/3600;
            newCoordinates = String.format("%f ", degrees);
        }
        return  newCoordinates;
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
                .append(gpsLongitudeDegrees, that.gpsLongitudeDegrees)
                .append(gpsLatitudeRef, that.gpsLatitudeRef)
                .append(gpsAltitudeRef, that.gpsAltitudeRef)
                .append(gpsLatitudeDegrees, that.gpsLatitudeDegrees)
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
                .append(gpsLongitudeDegrees)
                .append(gpsLatitudeRef)
                .append(gpsAltitudeRef)
                .append(gpsLatitudeDegrees)
                .append(gpsAltitude)
                .append(gpsDateStamp)
                .append(gpsLongitudeRef)
                .toHashCode();
    }
}
