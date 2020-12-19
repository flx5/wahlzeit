package org.wahlzeit.model;

import org.wahlzeit.model.exceptions.IllegalRangeException;

public abstract class AbstractCoordinate implements Coordinate {
    protected final void assertIsNotNullArgument(Object argument, String name) {
        if(argument == null) {
            throw new IllegalArgumentException("Argument " + name + " should not be null.");
        }
    }

    /**
     * Check that the value is finite and not NaN.
     * @param value The value to check
     * @param name The name of the value
     */
    protected final void assertIsFinite(double value, String name) {
        assertInRange(value, name, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
            false, false);
    }

    protected final void assertInRange(double value, String name,
                                       double from, double to) {
        this.assertInRange(value, name, from, to, true, true);
    }

    protected final void assertInRange(double value, String name,
                                       double from, double to,
                                       boolean fromInclusive, boolean toInclusive) {

        boolean fromCheck = fromInclusive ? value >= from : value > from;
        boolean toCheck = toInclusive ? value <= to : value < to;

        if(!fromCheck || !toCheck) {
            throw new IllegalRangeException(name, from, to, value, fromInclusive, toInclusive);
        }
    }

    @Override
    public double getCartesianDistance(Coordinate other) {
        this.assertClassInvariants();

        assertIsNotNullArgument(other, "other");

        double distance = this.asCartesianCoordinate().getDistance(other.asCartesianCoordinate());

        assertInRange(distance, "distance", 0, Double.MAX_VALUE);

        return distance;
    }

    protected abstract void assertClassInvariants();

    @Override
    public double getCentralAngle(Coordinate other) {
        this.assertClassInvariants();

        assertIsNotNullArgument(other, "other");

        return this.asSphericCoordinate().getCentralAngle(other);
    }

    @Override
    public boolean equals(Object o) {
        this.assertClassInvariants();

        if(o instanceof Coordinate) {
            return isEqual((Coordinate) o);
        }
        return false;
    }
}
