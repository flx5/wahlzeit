package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlanePhotoManager extends PhotoManager {
    public static PlanePhotoManager getInstance() {
        return (PlanePhotoManager) instance;
    }

    @Override
    protected PlanePhoto createObject(ResultSet rset) throws SQLException {
        return PlanePhotoFactory.getInstance().createPhoto(rset);
    }

    private void checkPhotoType(Photo photo) {
        if(!(photo instanceof PlanePhoto)) {
            throw new IllegalArgumentException("Expected PlanePhoto.");
        }
    }

    @Override
    protected void doAddPhoto(Photo myPhoto) {
        checkPhotoType(myPhoto);
        super.doAddPhoto(myPhoto);
    }
}
