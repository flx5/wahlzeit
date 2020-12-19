package org.wahlzeit.model;

import org.wahlzeit.utils.PrecisionUtil;

import java.util.Objects;

public class SphericCoordinate extends AbstractCoordinate {
    private double phi;
    private double theta;
    private double radius;

    public SphericCoordinate(double phi, double theta, double radius) {
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

        return new CartesianCoordinate(x,y,z);
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

    public void setPhi(double phi) {
        assertInRange(phi, "phi", -Math.PI, Math.PI);
        this.phi = phi;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        assertInRange(theta, "theta", 0, Math.PI);
        this.theta = theta;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        assertInRange(radius, "radius", 0, Double.POSITIVE_INFINITY, true, false);
        this.radius = radius;
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
        return Objects.hash(phi, theta, radius);
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
