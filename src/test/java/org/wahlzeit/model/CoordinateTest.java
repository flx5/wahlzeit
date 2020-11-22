package org.wahlzeit.model;

import org.junit.Test;
import org.wahlzeit.utils.PrecisionUtil;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CoordinateTest {
    @Test
    public void testLoad() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getDouble("location_x")).thenReturn(2.0);
        when(rs.getDouble("location_y")).thenReturn(5.0);
        when(rs.getDouble("location_z")).thenReturn(3.0);

        Coordinate coordinate = new Coordinate(rs);

        assertEquals(2.0, coordinate.getX(), 0.0);
        assertEquals(5.0, coordinate.getY(), 0.0);
        assertEquals(3.0, coordinate.getZ(), 0.0);
    }

    @Test
    public void testStore() throws SQLException {
        ResultSet rs = mock(ResultSet.class);


        Coordinate coordinate = new Coordinate(2.3, 1.4, 4.2);

        coordinate.writeOn(rs);

        verify(rs).updateDouble("location_x", 2.3);
        verify(rs).updateDouble("location_y", 1.4);
        verify(rs).updateDouble("location_z", 4.2);
    }

    @Test
    public void testEquality() {
        Coordinate x = new Coordinate(2.3, 1.4, 4.2);

        double myEpsilon = PrecisionUtil.EPSILON / 2;

        Coordinate y = new Coordinate(2.3+myEpsilon, 1.4-myEpsilon, 4.2);
        Coordinate z = new Coordinate(2.3+myEpsilon, 1.4-myEpsilon, 4.5);

        assertTrue(x.equals(y));
        assertFalse(x.equals(z));
    }

    @Test
    public void testDistance() {
        Coordinate x = new Coordinate(13, 8, 7);
        Coordinate y = new Coordinate(7, 6, 10);

        assertEquals(7.0, x.getDistance(y), PrecisionUtil.EPSILON);
    }
}
