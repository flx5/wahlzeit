package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Coordinate {
    private double x;
    private double y;
    private double z;

    public Coordinate(ResultSet rset) throws SQLException {
        readFrom(rset);
    }

    public Coordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void readFrom(ResultSet rset) throws SQLException {
        x = rset.getDouble("location_x");
        y = rset.getDouble("location_y");
        z = rset.getDouble("location_z");
    }

    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateDouble("location_x", x);
        rset.updateDouble("location_y", y);
        rset.updateDouble("location_z", z);
    }

    public double getDistance(Coordinate other) {
        if(other == null) {
            throw new IllegalArgumentException();
        }

        return Math.sqrt(
                  Math.pow(other.x - x, 2)
                + Math.pow(other.y - y, 2)
                + Math.pow(other.z - z, 2)
        );
    }

    public boolean isEqual(Coordinate other) {

        if(this == other) {
            return true;
        }

        if(other == null) {
            return false;
        }

        return this.x == other.x
                && this.y == other.y
                && this.z == other.z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Coordinate) {
            return isEqual((Coordinate) o);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
