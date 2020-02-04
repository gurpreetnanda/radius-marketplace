package com.radius.marketplace.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requirement")
public class Requirement {
    @Id
    private String id;
    private double latitude;
    private double longitude;
    private double minBudget = -1;
    private double maxBudget = -1;
    private int minBedroomCount;
    private int maxBedroomCount;
    private int minBathroomCount;
    private int maxBathroomCount;

    public double getMinBudget() {
        if (this.minBudget == -1)
            this.minBudget = this.maxBudget;
        return this.minBudget;
    }

    public double getMaxBudget() {
        if (this.maxBudget == -1)
            this.maxBudget = this.minBudget;
        return this.maxBudget;
    }
}
