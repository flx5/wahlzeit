package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlanePhotoFactory extends PhotoFactory {

    public static synchronized PlanePhotoFactory getInstance() {
        return (PlanePhotoFactory) PhotoFactory.getInstance();
    }

    @Override
    public PlanePhoto createPhoto() {
        return new PlanePhoto();
    }

    @Override
    public PlanePhoto createPhoto(PhotoId id) {
        return new PlanePhoto(id);
    }

    @Override
    public PlanePhoto createPhoto(ResultSet rs) throws SQLException {
        return new PlanePhoto(rs);
    }

    /**
     * Hidden singleton instance; needs to be initialized from the outside.
     */
    public static void initialize() {
        setInstance(new PlanePhotoFactory());
    }
}
