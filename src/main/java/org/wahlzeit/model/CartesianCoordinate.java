package org.wahlzeit.model;

import org.wahlzeit.utils.PrecisionUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class CartesianCoordinate implements Coordinate {
    private double x;
    private double y;
    private double z;

    public CartesianCoordinate(ResultSet rset) throws SQLException {
        readFrom(rset);
    }

    public CartesianCoordinate(double x, double y, double z) {
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

    public double getDistance(CartesianCoordinate other) {
        if(other == null) {
            throw new IllegalArgumentException();
        }

        return Math.sqrt(
                  Math.pow(other.x - x, 2)
                + Math.pow(other.y - y, 2)
                + Math.pow(other.z - z, 2)
        );
    }

    @Override
    public boolean isEqual(Coordinate other) {

        if(this == other) {
            return true;
        }

        if(other == null) {
            return false;
        }

        CartesianCoordinate otherCartesian = other.asCartesianCoordinate();

        return PrecisionUtil.equals(this.x, otherCartesian.x)
                && PrecisionUtil.equals(this.y, otherCartesian.y)
                && PrecisionUtil.equals(this.z, otherCartesian.z);
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

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return this;
    }

    @Override
    public double getCartesianDistance(Coordinate other) {
        return this.getDistance(other.asCartesianCoordinate());
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        double radius = Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2));
        double phi = Math.atan2(y,x);
        double theta = Math.acos(z/radius);

        return new SphericCoordinate(phi, theta, radius);
    }

    @Override
    public double getCentralAngle(Coordinate other) {
        return this.asSphericCoordinate().getCentralAngle(other);
    }

    @Override
    public String toString() {
        return "CartesianCoordinate{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
