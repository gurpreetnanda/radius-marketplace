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
@Table(name = "property")
public class Property {
    @Id
    private String id;
    private double latitude;
    private double longitude;
    private double price;
    private int bedroomCount;
    private int bathroomCount;
}
