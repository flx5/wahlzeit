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

        assertIsFinite(x, y, z);

        return new CartesianCoordinate(x,y,z);
    }

    protected void assertClassInvariants() {
        assertIsFinite(phi, theta, radius);
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return this;
    }

    @Override
    public double getCentralAngle(Coordinate other) {
        assertIsNotNullArgument(other);

        SphericCoordinate otherSpheric = other.asSphericCoordinate();

        assert otherSpheric != null;

        double deltaTheta = Math.abs(theta - otherSpheric.theta);

        double angle = Math.acos(Math.sin(phi) * Math.sin(otherSpheric.phi) +
            Math.cos(theta) * Math.cos(otherSpheric.theta) * Math.cos(deltaTheta));

        assertIsFinite(angle);

        return angle;
    }

    public double getPhi() {
        return phi;
    }

    public void setPhi(double phi) {
        assertIsFinite(phi);
        this.phi = phi;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        assertIsFinite(theta);
        this.theta = theta;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        assertIsFinite(radius);
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
