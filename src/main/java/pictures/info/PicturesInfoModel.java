package pictures.info;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michele on 2/16/16.
 */
public class PicturesInfoModel {
    private Metadata metadata;
    private List<PictureDetails> pictureDetailsList;

    public PicturesInfoModel(File picturesDirectory) throws ImageProcessingException, IOException {
        pictureDetailsList = new ArrayList<>();
        for (final File file : picturesDirectory.listFiles()) {
            if ( ! file.isDirectory() ) {
                PictureDetails pictureDetails = new PictureDetails();
                pictureDetails.setFileName(file.getName());
                try {
                    metadata = ImageMetadataReader.readMetadata(file);
                    for (Directory directory : metadata.getDirectories()) {
                        for (Tag tag : directory.getTags()) {
                            if (tag.getTagName().equals("GPS Longitude")) {
                                pictureDetails.setGpsLongitude(tag.getDescription());
                            }
                            // System.out.format("[%s] - %s = %s\n", directory.getName(), tag.getTagName(), tag.getDescription());
                            /*
                            [GPS] - GPS Time-Stamp = 09:46:52.00 UTC
                            [GPS] - GPS Processing Method = 0
                            [GPS] - GPS Longitude = 13° 50' 59.87"
                            [GPS] - GPS Latitude Ref = N
                            [GPS] - GPS Altitude Ref = Sea level
                            [GPS] - GPS Latitude = 45° 37' 42.24"
                            [GPS] - GPS Altitude = 399 metres
                            [GPS] - GPS Date Stamp = 2016:02:05
                            [GPS] - GPS Longitude Ref = E
                             */
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

    public List<PictureDetails> getPictureDetailsList() {
        return pictureDetailsList;
    }
}
