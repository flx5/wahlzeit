package org.wahlzeit.model;

import org.junit.Test;
import org.wahlzeit.model.exceptions.IllegalRangeException;
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

        CartesianCoordinate coordinate = new CartesianCoordinate(rs);

        assertEquals(2.0, coordinate.getX(), 0.0);
        assertEquals(5.0, coordinate.getY(), 0.0);
        assertEquals(3.0, coordinate.getZ(), 0.0);
    }

    @Test
    public void testStore() throws SQLException {
        ResultSet rs = mock(ResultSet.class);


        CartesianCoordinate coordinate = new CartesianCoordinate(2.3, 1.4, 4.2);

        coordinate.writeOn(rs);

        verify(rs).updateDouble("location_x", 2.3);
        verify(rs).updateDouble("location_y", 1.4);
        verify(rs).updateDouble("location_z", 4.2);
    }

    @Test
    public void testEquality() {
        CartesianCoordinate x = new CartesianCoordinate(2.3, 1.4, 4.2);

        double myEpsilon = PrecisionUtil.EPSILON / 2;

        Coordinate y = new CartesianCoordinate(2.3+myEpsilon, 1.4-myEpsilon, 4.2);
        Coordinate z = new CartesianCoordinate(2.3+myEpsilon, 1.4-myEpsilon, 4.5);

        assertTrue(x.equals(y));
        assertFalse(x.equals(z));
    }

    @Test
    public void testDistance() {
        CartesianCoordinate x = new CartesianCoordinate(13, 8, 7);
        CartesianCoordinate y = new CartesianCoordinate(7, 6, 10);

        assertEquals(7.0, x.getCartesianDistance(y), PrecisionUtil.EPSILON);
    }

    @Test
    public void testSphericCartesianConversion() {
        CartesianCoordinate coordinate = new CartesianCoordinate(13, 8, 7);
        SphericCoordinate expected = new SphericCoordinate(0.55165, 1.1408, 16.7929);

        SphericCoordinate result = coordinate.asSphericCoordinate();
        assertEquals(expected, result);
    }

    @Test
    public void testCartesianSphericConversion() {
        CartesianCoordinate expected = new CartesianCoordinate(13, 8, 7);
        SphericCoordinate coordinate = new SphericCoordinate(0.551655, 1.14083, 16.7929);

        CartesianCoordinate result = coordinate.asCartesianCoordinate();
        assertEquals(expected, result);
    }

    @Test
    public void testNullEquals() {
        CartesianCoordinate a = new CartesianCoordinate(13, 8, 7);
        assertFalse(a.equals(null));
    }

    @Test(expected = IllegalRangeException.class)
    public void testOverflow() {
        CartesianCoordinate a = new CartesianCoordinate(Double.MAX_VALUE, 8, 7);
        a.asSphericCoordinate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNull() throws SQLException {
        CartesianCoordinate a = new CartesianCoordinate(null);
    }
}
