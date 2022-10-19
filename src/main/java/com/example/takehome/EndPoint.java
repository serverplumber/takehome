package com.example.takehome;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.vavr.collection.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
public class EndPoint {
    private final CountryDatabase countryDatabase;

    public EndPoint(CountryDatabase countryDatabase) {
        this.countryDatabase = countryDatabase;
    }

    @PostMapping("/continents")
    @RateLimiter(name = "countries")
    @ResponseBody
    public Continent[] getContinent(@RequestBody List<String> countries) {
        // remove duplicates
        countries = countries.distinct();
        var continentMap = new HashMap<String, Continent>();
        for (String country : countries) {
            if (countryDatabase.isCountry(country)) {
                var continent = countryDatabase.getContinent(country);
                if (!continentMap.containsKey(continent)) {
                    continentMap.put(
                            continent,
                            Continent.builder()
                                    .name(continent)
                                    .countries(List.ofAll(Collections.singleton(country)))
                                    .otherCountries(List.ofAll(
                                            countryDatabase.getCountries(continent)))
                                    .build()
                    );
                } else {
                    var c = continentMap.get(continent);
                    c.setCountries( c.getCountries().append(country));
                    c.setCountries(c.getOtherCountries().remove(country));
                }
            }
        }
        return continentMap.values().toArray(new Continent[0]);
    }
}
