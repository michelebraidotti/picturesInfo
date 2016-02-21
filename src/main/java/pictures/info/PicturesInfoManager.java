package pictures.info;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by michele on 2/16/16.
 */
public class PicturesInfoManager {
    private Metadata metadata;
    private List<PictureDetails> pictureDetailsList;

    public PicturesInfoManager(File picturesDirectory) throws ImageProcessingException, IOException {
        pictureDetailsList = new ArrayList<>();
        for (final File file : picturesDirectory.listFiles()) {
            if ( ! file.isDirectory() ) {
                PictureDetails pictureDetails = new PictureDetails();
                pictureDetails.setFileName(file.getName());
                try {
                    metadata = ImageMetadataReader.readMetadata(file);
                    for (Directory directory : metadata.getDirectories()) {
                        for (Tag tag : directory.getTags()) {
                            //[GPS] - GPS Time-Stamp = 09:46:52.00 UTC
                            if (tag.getTagName().equals("GPS Time-Stamp")) {
                                pictureDetails.setGpsTimeStamp(tag.getDescription());
                            }
                            //[GPS] - GPS Processing Method = 0
                            if (tag.getTagName().equals("GPS Processing Method")) {
                                pictureDetails.setGpsProcessingMethod(tag.getDescription());
                            }
                            //[GPS] - GPS Longitude = 13° 50' 59.87"
                            if (tag.getTagName().equals("GPS Longitude")) {
                                pictureDetails.setGpsLongitude(convertCoordinates(tag.getDescription()));
                            }
                            //[GPS] - GPS Longitude Ref = E
                            if (tag.getTagName().equals("GPS Longitude Ref")) {
                                pictureDetails.setGpsLongitudeRef(tag.getDescription());
                            }
                            //[GPS] - GPS Latitude = 45° 37' 42.24"
                            if (tag.getTagName().equals("GPS Latitude")) {
                                pictureDetails.setGpsLatitude(convertCoordinates(tag.getDescription()));
                            }
                            //[GPS] - GPS Latitude Ref = N
                            if (tag.getTagName().equals("GPS Latitude Ref")) {
                                pictureDetails.setGpsLatitudeRef(tag.getDescription());
                            }
                            //[GPS] - GPS Altitude = 399 metres
                            if (tag.getTagName().equals("GPS Altitude")) {
                                pictureDetails.setGpsAltitude(tag.getDescription());
                            }
                            //[GPS] - GPS Altitude Ref = Sea level
                            if (tag.getTagName().equals("GPS Altitude Ref")) {
                                pictureDetails.setGpsAltitudeRef(tag.getDescription());
                            }
                            //[GPS] - GPS Date Stamp = 2016:02:05
                            if (tag.getTagName().equals("GPS Date Stamp")) {
                                pictureDetails.setGpsDateStamp(tag.getDescription());
                            }

                        }
                        if (directory.hasErrors()) {
                            for (String error : directory.getErrors()) {
                                pictureDetails.addError(new Exception(error));
                            }
                        }
                    }
                } catch (ImageProcessingException e) {
                    pictureDetails.addError(e);
                }
                pictureDetailsList.add(pictureDetails);
            }
        }
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
            System.out.print(degrees + " " + minutes + " " + seconds + "\n");
            System.out.printf("%f\n", degrees);
            newCoordinates = String.format("%f ", degrees);
        }
        return  newCoordinates;
    }

    public List<PictureDetails> getPictureDetailsList() {
        return pictureDetailsList;
    }

    public String toCsvString(List<PictureDetails> pictureDetailsToExportList) {
        StringBuilder out = new StringBuilder();
        out.append("File Name,GPS Date Stamp,GPS Time-Stamp,GPS Processing Method,GPS Longitude,GPS Longitude Ref,GPS Latitude,GPS Latitude Ref,GPS Altitude,GPS Altitude Ref,\n");
        for (PictureDetails pictureDetails:getPictureDetailsList()) {
            for ( PictureDetails pictureDetailsToExport:pictureDetailsToExportList) {
                if ( pictureDetails.equals(pictureDetailsToExport) ) {
                    out.append(convertToCsvRow(pictureDetailsToExport));
                }
            }
        }
        return out.toString();
    }

    private StringBuilder convertToCsvRow(PictureDetails pictureDetailsToExport) {
        StringBuilder out = new StringBuilder();
        out.append(StringEscapeUtils.escapeCsv(pictureDetailsToExport.getFileName()) + ",");
        out.append(StringEscapeUtils.escapeCsv(pictureDetailsToExport.getGpsDateStamp()) + ",");
        out.append(StringEscapeUtils.escapeCsv(pictureDetailsToExport.getGpsTimeStamp()) + ",");
        out.append(StringEscapeUtils.escapeCsv(pictureDetailsToExport.getGpsProcessingMethod()) + ",");
        out.append(StringEscapeUtils.escapeCsv(pictureDetailsToExport.getGpsLongitude()) + ",");
        out.append(StringEscapeUtils.escapeCsv(pictureDetailsToExport.getGpsLongitudeRef()) + ",");
        out.append(StringEscapeUtils.escapeCsv(pictureDetailsToExport.getGpsLatitude()) + ",");
        out.append(StringEscapeUtils.escapeCsv(pictureDetailsToExport.getGpsLatitudeRef()) + ",");
        out.append(StringEscapeUtils.escapeCsv(pictureDetailsToExport.getGpsAltitude()) + ",");
        out.append(StringEscapeUtils.escapeCsv(pictureDetailsToExport.getGpsAltitudeRef()) + ",");
        out.append("\n");
        return out;
    }
}
