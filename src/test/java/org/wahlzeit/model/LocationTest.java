package org.wahlzeit.model;

import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class LocationTest {
    @Test
    public void testLoad() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getDouble("location_x")).thenReturn(2.0);
        when(rs.getDouble("location_y")).thenReturn(5.0);
        when(rs.getDouble("location_z")).thenReturn(3.0);

        Location location = new Location(rs);
        CartesianCoordinate coordinate = location.getCoordinate().asCartesianCoordinate();

        assertEquals(2.0, coordinate.getX(), 0.0);
        assertEquals(5.0, coordinate.getY(), 0.0);
        assertEquals(3.0, coordinate.getZ(), 0.0);
    }

    @Test
    public void testSave() throws SQLException {
        ResultSet rs = mock(ResultSet.class);

        CartesianCoordinate coordinate = new CartesianCoordinate(2.3, 1.4, 4.2);
        Location location = new Location(coordinate);

        location.writeOn(rs);

        verify(rs).updateDouble("location_x", 2.3);
        verify(rs).updateDouble("location_y", 1.4);
        verify(rs).updateDouble("location_z", 4.2);
    }
}
