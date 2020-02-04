package com.radius.marketplace.util;

import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;

public class SearchUtil {
    public static final int BEST_DISTANCE = 2;
    public static final int MAX_DISTANCE = 10;
    public static final int DISTANCE_RANGE = MAX_DISTANCE - BEST_DISTANCE;
    public static final int MAX_DISTANCE_SCORE = 30;
    public static final Distance SEARCH_RADIUS = new Distance(MAX_DISTANCE, Metrics.MILES);

    public static final int BEST_BUDGET = 10;
    public static final int MAX_BUDGET = 25;
    public static final int BUDGET_RANGE = MAX_BUDGET - BEST_BUDGET;
    public static final int MAX_BUDGET_SCORE = 30;

    public static final int ZERO = 0;
    public static final int HUNDRED = 100;

    private SearchUtil() throws IllegalAccessException {
        throw new IllegalAccessException("Cannot instantiate util class");
    }

    public static Point createPoint(double latitude, double longitude) {
        return new Point(longitude, latitude);
    }

    public static Circle createSearchCircle(double latitude, double longitude) {
        return new Circle(createPoint(latitude, longitude), SEARCH_RADIUS);
    }

    public static double calculateDistanceMatchPercentage(double distance) {
        if (distance > MAX_DISTANCE)
            return ZERO;
        if (distance <= BEST_DISTANCE)
            return MAX_DISTANCE_SCORE;
        // todo: recheck
        return MAX_DISTANCE_SCORE - (calculatePercentage(distance - BEST_DISTANCE, DISTANCE_RANGE, MAX_DISTANCE_SCORE));
    }

    public static double calculateBudgetMatchPercentage(double budgetOffPercentage) {
        double offPercentage = Math.abs(budgetOffPercentage);
        if (offPercentage > MAX_BUDGET)
            return ZERO;
        if (offPercentage <= BEST_BUDGET)
            return MAX_BUDGET_SCORE;
        return calculatePercentage(offPercentage, BUDGET_RANGE, MAX_BUDGET_SCORE);
    }

    public static double calculateBudgetOffPrice(double price, double minBudget, double maxBudget) {
        if (minBudget <= price && maxBudget >= price)
            return ZERO;
        return minBudget > price ? price - minBudget : maxBudget - price;
    }

    public static double calculateBudgetOffPercentage(double price, double minBudget, double maxBudget) {
        double priceOffBudget = calculateBudgetOffPrice(price, minBudget, maxBudget);
        double divisor = priceOffBudget < ZERO ? minBudget : maxBudget;
        return calculatePercentage(priceOffBudget, divisor, HUNDRED);
    }

    private static double calculatePercentage(double dividend, double divisor, int maxPercentage) {
        return (dividend / divisor) * maxPercentage;
    }
}
