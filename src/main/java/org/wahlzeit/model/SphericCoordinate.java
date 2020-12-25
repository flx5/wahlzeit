package org.wahlzeit.model;

import org.wahlzeit.utils.PrecisionUtil;

import java.util.Objects;

public class SphericCoordinate extends AbstractCoordinate {
    private static final ValueObjectManager<SphericCoordinate> manager = new ValueObjectManager<>();

    private final double phi;
    private final double theta;
    private final double radius;

    private SphericCoordinate(double phi, double theta, double radius) {
        this.phi = phi;
        this.theta = theta;
        this.radius = radius;

        assertClassInvariants();
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        assertClassInvariants();

        double x = radius * Math.sin(theta) * Math.cos(phi);
        double y = radius * Math.sin(theta) * Math.sin(phi);
        double z = radius * Math.cos(theta);

        assertIsFinite(x, "x");
        assertIsFinite(y, "y");
        assertIsFinite(z, "z");

        return CartesianCoordinate.getInstance(x,y,z);
    }

    public static SphericCoordinate getInstance(double phi, double theta, double radius) {
        return manager.getSharedValueObject(new SphericCoordinate(phi, theta, radius));
    }

    @Override
    protected void assertClassInvariants() {
        assertInRange(radius, "radius", 0, Double.POSITIVE_INFINITY, true, false);
        assertInRange(theta, "theta", 0, Math.PI);
        assertInRange(phi, "phi", -Math.PI, Math.PI);
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return this;
    }

    @Override
    public double getCentralAngle(Coordinate other) {
        assertIsNotNullArgument(other, "other");

        SphericCoordinate otherSpheric = other.asSphericCoordinate();

        assertIsNotNullArgument(otherSpheric, "otherSpheric");

        double deltaTheta = Math.abs(theta - otherSpheric.theta);

        double angle = Math.acos(Math.sin(phi) * Math.sin(otherSpheric.phi) +
            Math.cos(theta) * Math.cos(otherSpheric.theta) * Math.cos(deltaTheta));

        assertInRange(angle, "angle", 0, Math.PI);

        return angle;
    }

    public double getPhi() {
        return phi;
    }

    public SphericCoordinate setPhi(double phi) {
        assertInRange(phi, "phi", -Math.PI, Math.PI);
        return getInstance(phi, theta, radius);
    }

    public double getTheta() {
        return theta;
    }

    public SphericCoordinate setTheta(double theta) {
        assertInRange(theta, "theta", 0, Math.PI);
        return getInstance(phi, theta, radius);
    }

    public double getRadius() {
        return radius;
    }

    public SphericCoordinate setRadius(double radius) {
        assertInRange(radius, "radius", 0, Double.POSITIVE_INFINITY, true, false);
        return getInstance(phi, theta, radius);
    }

    @Override
    public boolean isEqual(Coordinate other) {
        if(this == other) {
            return true;
        }

        if(other == null) {
            return false;
        }

        SphericCoordinate otherSpheric = other.asSphericCoordinate();

        assert otherSpheric != null;

        return PrecisionUtil.equals(this.radius, otherSpheric.radius)
                && PrecisionUtil.equals(this.phi, otherSpheric.phi)
                && PrecisionUtil.equals(this.theta, otherSpheric.theta);
    }

    @Override
    public int hashCode() {
        assertClassInvariants();
        return Objects.hash(Math.round(phi), Math.round(theta), Math.round(radius));
    }

    @Override
    public String toString() {
        return "SphericCoordinate{" +
                "phi=" + phi +
                ", theta=" + theta +
                ", radius=" + radius +
                '}';
    }
}
