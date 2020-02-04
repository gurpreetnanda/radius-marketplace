package com.radius.marketplace.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequirementSearchResponse {
    private Requirement requirement;
    private double distance;
    private double distanceMatchPercentage;
}
