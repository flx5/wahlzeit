package org.wahlzeit.model;

public abstract class AbstractCoordinate implements Coordinate {
    @Override
    public double getCartesianDistance(Coordinate other) {
        return this.asCartesianCoordinate().getDistance(other.asCartesianCoordinate());
    }

    @Override
    public double getCentralAngle(Coordinate other) {
        return this.asSphericCoordinate().getCentralAngle(other);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Coordinate) {
            return isEqual((Coordinate) o);
        }
        return false;
    }
}
