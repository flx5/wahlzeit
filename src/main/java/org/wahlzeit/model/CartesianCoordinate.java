package org.wahlzeit.model;

import org.wahlzeit.utils.PrecisionUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@PatternInstance(
    patternName = "Flyweight Pattern",
    participants = {
        "CartesianCoordinate", "ValueObjectManager<CartesianCoordinate>"
    }
)
public class CartesianCoordinate extends AbstractCoordinate {
    private static final ValueObjectManager<CartesianCoordinate> manager = new ValueObjectManager<>();

    private final double x;
    private final double y;
    private final double z;

    private CartesianCoordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;

        assertClassInvariants();
    }

    private CartesianCoordinate(ResultSet rset) throws SQLException {
        assertIsNotNullArgument(rset, "rset");
        x = rset.getDouble("location_x");
        y = rset.getDouble("location_y");
        z = rset.getDouble("location_z");
        assertClassInvariants();
    }

    public static CartesianCoordinate getInstance(double x, double y, double z) {
        return manager.getSharedValueObject(new CartesianCoordinate(x, y, z));
    }

    public static CartesianCoordinate getInstance(ResultSet rset) throws SQLException {
        return manager.getSharedValueObject(new CartesianCoordinate(rset));
    }

    @Override
    protected void assertClassInvariants() {
        assertIsFinite(x, "x");
        assertIsFinite(y, "y");
        assertIsFinite(z, "z");
    }

    public void writeOn(ResultSet rset) throws SQLException {
        assertIsNotNullArgument(rset, "rset");
        assertClassInvariants();

        rset.updateDouble("location_x", x);
        rset.updateDouble("location_y", y);
        rset.updateDouble("location_z", z);
    }

    public double getDistance(CartesianCoordinate other) {
        assertIsNotNullArgument(other, "other");
        assertClassInvariants();

        double distance = Math.sqrt(
            Math.pow(other.x - x, 2)
                + Math.pow(other.y - y, 2)
                + Math.pow(other.z - z, 2)
        );

        assertIsFinite(distance, "distance");

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

        assertIsNotNullArgument(otherCartesian, "otherCartesian");

        return PrecisionUtil.equals(this.x, otherCartesian.x)
                && PrecisionUtil.equals(this.y, otherCartesian.y)
                && PrecisionUtil.equals(this.z, otherCartesian.z);
    }

    public double getX() {
        return x;
    }

    public CartesianCoordinate setX(double x) {
        assertIsFinite(x, "x");
        return getInstance(x, y, z);
    }

    public double getY() {
        return y;
    }

    public CartesianCoordinate setY(double y) {
        assertIsFinite(y, "y");

        return getInstance(x, y, z);
    }

    public double getZ() {
        return z;
    }

    public CartesianCoordinate setZ(double z) {
        assertIsFinite(z, "z");
        return getInstance(x, y, z);
    }

    @Override
    public int hashCode() {
        /*
        This method as well as the equals (transitive!) method basically violate the contracts of Java Objects...
        But since the JDK does not provide us with a way to supply a custom comparator to HashMap
        for shared value objects and the lecture required us to forward the equals method to isEquals...
        *shrugs*
         */
        assertClassInvariants();
        return Objects.hash(Math.round(x), Math.round(y), Math.round(z));
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

        assertInRange(radius, "radius", 0, Double.POSITIVE_INFINITY, true, false);
        assertInRange(theta, "theta", 0, Math.PI);
        assertInRange(phi, "phi", -Math.PI, Math.PI);

        SphericCoordinate sphericCoordinate = SphericCoordinate.getInstance(phi, theta, radius);

        // We need to use precision util here because the returned instance might be a little bit different.

        assert PrecisionUtil.equals(radius, sphericCoordinate.getRadius())
            && PrecisionUtil.equals(phi, sphericCoordinate.getPhi())
            && PrecisionUtil.equals(theta, sphericCoordinate.getTheta());

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
