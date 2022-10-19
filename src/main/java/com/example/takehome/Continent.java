package com.example.takehome;

import io.vavr.collection.List;
import lombok.Builder;
import lombok.Data;


@Builder
public @Data class Continent {
    private String name;
    private List<String> countries;
    private List<String> otherCountries;
}
