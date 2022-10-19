package com.example.takehome;

import lombok.Data;
import lombok.Builder;

@Builder
public @Data class ContinentResponse {
    private Continent[] continents;
}
