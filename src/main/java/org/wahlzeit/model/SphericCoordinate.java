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
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        double x = radius * Math.sin(theta) * Math.cos(phi);
        double y = radius * Math.sin(theta) * Math.sin(phi);
        double z = radius * Math.cos(theta);

        return new CartesianCoordinate(x,y,z);
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return this;
    }

    @Override
    public double getCentralAngle(Coordinate other) {
        SphericCoordinate otherSpheric = other.asSphericCoordinate();

        double deltaTheta = Math.abs(theta - otherSpheric.theta);

        return Math.acos(Math.sin(phi)*Math.sin(otherSpheric.phi) +
                Math.cos(theta)*Math.cos(otherSpheric.theta)*Math.cos(deltaTheta));
    }

    public double getPhi() {
        return phi;
    }

    public void setPhi(double phi) {
        this.phi = phi;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
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

        return PrecisionUtil.equals(this.radius, otherSpheric.radius)
                && PrecisionUtil.equals(this.phi, otherSpheric.phi)
                && PrecisionUtil.equals(this.theta, otherSpheric.theta);
    }

    @Override
    public int hashCode() {
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
