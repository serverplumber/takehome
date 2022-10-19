package com.example.takehome;

import com.google.common.collect.Maps;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.vavr.collection.List;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class ContinentHandler {
    private final CountryDatabase countryDatabase;

    public ContinentHandler(CountryDatabase countryDatabase) {
        this.countryDatabase = countryDatabase;
    }

    @RateLimiter(name = "countries")
    @PostMapping(path = "/fluxcontinents",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ServerResponse> fluxContinent( ServerRequest countriesRequest ) {
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(countriesRequest
                .bodyToFlux(String.class)
                .distinct()
                .filter(countryDatabase::isCountry)
                .collectMultimap(countryDatabase::getContinent)
                .flux()
                .map(continentMap -> Maps.transformEntries(continentMap, (continent, countries)
                     -> Continent.builder()
                        .name(continent)
                        .countries(List.ofAll(countries))
                        .otherCountries(List.ofAll(countryDatabase.getCountries(continent)))
                        .build()))
                .map(Map::values)
                .next(), ContinentResponse.class
                );
    }
}
