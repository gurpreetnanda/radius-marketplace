package com.radius.marketplace.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private double minBudget;
    private double maxBudget;
    private int minBedroomCount;
    private int maxBedroomCount;
    private int minBathroomCount;
    private int maxBathroomCount;
}
