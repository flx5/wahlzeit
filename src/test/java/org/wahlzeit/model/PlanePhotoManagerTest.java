package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlanePhotoManagerTest {
    @Test(expected = IllegalArgumentException.class)
    public void testWrongType() {
        PlanePhotoManager manager = new PlanePhotoManager();
        manager.doAddPhoto(new Photo());
    }

    @Test
    public void testRightType() {
        PlanePhotoManager manager = new PlanePhotoManager();
        PhotoId id = PhotoId.getIdFromInt(324567);
        Photo photo = new PlanePhoto(id);

        manager.doAddPhoto(photo);
        assertSame(photo, manager.doGetPhotoFromId(id));
    }
}
