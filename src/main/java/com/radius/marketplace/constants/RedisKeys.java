package com.radius.marketplace.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisKeys {
    PROPERTIES("properties"),
    REQUIREMENTS("requirements");

    private String key;
}
