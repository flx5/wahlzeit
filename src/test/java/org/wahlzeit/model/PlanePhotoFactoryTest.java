package org.wahlzeit.model;

import org.junit.Test;
import org.wahlzeit.services.Language;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.Mockito.*;

public class PlanePhotoFactoryTest {
    @Test
    public void testCreationSimple() {
        PlanePhotoFactory factory = new PlanePhotoFactory();
        assertThat(factory.createPhoto(), instanceOf(PlanePhoto.class));
    }

    @Test
    public void testCreationWithId() {
        PlanePhotoFactory factory = new PlanePhotoFactory();
        Photo photo = factory.createPhoto(PhotoId.getNextId());
        assertThat(photo, instanceOf(PlanePhoto.class));
    }

    @Test
    public void testCreationWithRS() throws SQLException {
        PlanePhotoFactory factory = new PlanePhotoFactory();

        ResultSet rs = mock(ResultSet.class);

        when(rs.getDouble("location_x")).thenReturn(2.0);
        when(rs.getDouble("location_y")).thenReturn(5.0);
        when(rs.getDouble("location_z")).thenReturn(3.0);
        when(rs.getInt("id")).thenReturn(345);
        when(rs.getInt("owner_id")).thenReturn(23);
        when(rs.getString("owner_name")).thenReturn("OwnerName");
        when(rs.getBoolean("owner_notify_about_praise")).thenReturn(false);
        when(rs.getString("owner_email_address")).thenReturn("test@example.com");
        when(rs.getInt("owner_language")).thenReturn(Language.ENGLISH.asInt());
        when(rs.getString("owner_home_page")).thenReturn("http://example.com");
        when(rs.getInt("width")).thenReturn(2);
        when(rs.getInt("height")).thenReturn(3);
        when(rs.getString("tags")).thenReturn("tag1,tag2");
        when(rs.getInt("status")).thenReturn(PhotoStatus.VISIBLE.asInt());
        when(rs.getInt("praise_sum")).thenReturn(3);
        when(rs.getInt("no_votes")).thenReturn(2);
        when(rs.getLong("creation_time")).thenReturn(0l);
        when(rs.getString("type_name")).thenReturn("Airbus A380-700");

        Photo photo = factory.createPhoto(rs);
        assertThat(photo, instanceOf(PlanePhoto.class));

        assertEquals("OwnerName", photo.getOwnerName());
    }
}
