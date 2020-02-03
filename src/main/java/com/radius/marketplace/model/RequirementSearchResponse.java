package com.radius.marketplace.model;

import com.radius.marketplace.util.SearchUtil;
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

    public double getMatchPercentage() {
        return SearchUtil.calculateMatchPercentage(this.distance);
    }
}
