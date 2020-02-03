package com.radius.marketplace.model;

import com.radius.marketplace.util.SearchUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertySearchResponse {
    private Property property;
    private double distance;

    public double getMatchPercentage() {
        return SearchUtil.calculateMatchPercentage(this.distance);
    }
}
