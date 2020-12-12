package org.wahlzeit.model;

public abstract class AbstractCoordinate implements Coordinate {
    protected final void assertIsNotNullArgument(Object argument) {
        assert argument != null;
    }

    /**
     * Check that the values are finite and not NaN.
     * @param values
     */
    protected final void assertIsFinite(double... values) {
        for(double value : values) {
            assert Double.isFinite(value);
        }
    }

    @Override
    public double getCartesianDistance(Coordinate other) {
        assertIsNotNullArgument(other);

        return this.asCartesianCoordinate().getDistance(other.asCartesianCoordinate());
    }

    @Override
    public double getCentralAngle(Coordinate other) {
        assertIsNotNullArgument(other);

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
