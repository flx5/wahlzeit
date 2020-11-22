package org.wahlzeit.utils;

public class PrecisionUtil {

    public static final double EPSILON = 0.1;

    public static boolean equals(double x, double y) {
        return equals(x,y, EPSILON);
    }

    public static boolean equals(double x, double y, double eps) {
        return Math.abs(x-y) <= eps;
    }
}
