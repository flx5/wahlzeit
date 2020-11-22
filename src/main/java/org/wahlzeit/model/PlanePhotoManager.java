package org.wahlzeit.model;

public class PlanePhotoManager extends PhotoManager {
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
