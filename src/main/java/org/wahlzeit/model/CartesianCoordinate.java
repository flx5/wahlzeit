package org.wahlzeit.model;

import org.wahlzeit.utils.PrecisionUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class CartesianCoordinate extends AbstractCoordinate {
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

        assertClassInvariants();
    }

    protected void assertClassInvariants() {
        assertIsFinite(x, y, z);
    }

    public void readFrom(ResultSet rset) throws SQLException {
        assertIsNotNullArgument(rset);

        x = rset.getDouble("location_x");
        y = rset.getDouble("location_y");
        z = rset.getDouble("location_z");

        assertClassInvariants();
    }

    public void writeOn(ResultSet rset) throws SQLException {
        assertIsNotNullArgument(rset);
        assertClassInvariants();

        rset.updateDouble("location_x", x);
        rset.updateDouble("location_y", y);
        rset.updateDouble("location_z", z);
    }

    public double getDistance(CartesianCoordinate other) {
        assertIsNotNullArgument(other);
        assertClassInvariants();

        double distance = Math.sqrt(
            Math.pow(other.x - x, 2)
                + Math.pow(other.y - y, 2)
                + Math.pow(other.z - z, 2)
        );

        assertIsFinite(distance);

        return distance;
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

        assert otherCartesian != null;

        return PrecisionUtil.equals(this.x, otherCartesian.x)
                && PrecisionUtil.equals(this.y, otherCartesian.y)
                && PrecisionUtil.equals(this.z, otherCartesian.z);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        assertIsFinite(x);
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        assertIsFinite(x);
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        assertIsFinite(x);
        this.z = z;
    }

    @Override
    public int hashCode() {
        assertClassInvariants();
        return Objects.hash(x, y, z);
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return this;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        double radius = Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2));
        double phi = Math.atan2(y,x);
        double theta = Math.acos(z/radius);

        assertIsFinite(radius, phi, theta);

        SphericCoordinate sphericCoordinate = new SphericCoordinate(phi, theta, radius);

        // We can use absolute equality here since no calculations
        // should have been done in the meantime on these values.
        assert sphericCoordinate.getRadius() == radius;
        assert sphericCoordinate.getPhi() == phi;
        assert sphericCoordinate.getTheta() == theta;

        return sphericCoordinate;
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
