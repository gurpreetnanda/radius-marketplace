package com.radius.marketplace.util;

import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;

public class SearchUtil {
    public static final int BEST_MATCH_DISTANCE = 2;
    public static final int MAX_MATCH_DISTANCE = 10;
    public static final Distance DISTANCE = new Distance(MAX_MATCH_DISTANCE, Metrics.MILES);
    public static final int MAX_DISTANCE_SCORE = 30;

    private SearchUtil() throws IllegalAccessException {
        throw new IllegalAccessException("Cannot instantiate util class");
    }

    public static Point createPoint(double latitude, double longitude) {
        return new Point(longitude, latitude);
    }

    public static Circle createSearchCircle(double latitude, double longitude) {
        return new Circle(createPoint(latitude, longitude), DISTANCE);
    }

    public static double calculateMatchPercentage(double distance) {
        // todo: add budget
        if (distance <= BEST_MATCH_DISTANCE)
            return MAX_DISTANCE_SCORE;
        return MAX_DISTANCE_SCORE - ((distance / (MAX_MATCH_DISTANCE - BEST_MATCH_DISTANCE)) * MAX_DISTANCE_SCORE);
    }
}
