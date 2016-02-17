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
            metadata = ImageMetadataReader.readMetadata(file);
            PictureDetails pictureDetails = new PictureDetails();
            pictureDetails.setFileName(file.getName());
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    if ( tag.getTagName().equals("GPS Longitude")) {
                        pictureDetails.setGpsLongitude(tag.getDescription());
                    }
                    // System.out.format("[%s] - %s = %s\n", directory.getName(), tag.getTagName(), tag.getDescription());
                }
                if (directory.hasErrors()) {
                    for (String error : directory.getErrors()) {
                        System.err.format("ERROR: %s\n", error);
                    }
                }
            }
            pictureDetailsList.add(pictureDetails);
        }
    }

    public List<PictureDetails> getPictureDetailsList() {
        return pictureDetailsList;
    }
}
