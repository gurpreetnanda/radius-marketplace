package com.radius.marketplace.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertySearchResponse {
    private Property property;
    private double distance;
    private double budgetOffPrice;
    private double budgetOffPercentage;
    private double distanceMatchPercentage;
    private double budgetMatchPercentage;

    public double getMatchPercentage() {
        return this.distanceMatchPercentage + this.budgetMatchPercentage;
    }
}
