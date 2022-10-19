package com.example.takehome;

import com.google.common.collect.*;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CountryDatabase {

    // This is a map of countries and their continents we use it to build a graph, so we can find the continent of a country
    // simply. It also centralizes the data, so we can easily update it in the event of a geopolitical change.
    // While getting the data from an external service is possible; I don't believe it is a good idea to create a dependency
    // on external services for what is mostly static data.
    // This service is also overkill for an example like this, but I wanted to show how I would do it in a real project.
    // Using a graph database might would be great for similar data which changes frequently.
    //
    private final ImmutableSetMultimap<String, ImmutableSet<String>> continentCountries = ImmutableSetMultimap.<String, ImmutableSet<String>>builder()
            .put("Africa", ImmutableSet.of("AO","BF","BI","BJ","BW","CD","CF","CG","CI","CM","CV","DJ","DZ","EG","EH","ER","ET","GA","GH","GM","GN","GQ","GW","KE","KM","LR","LS","LY","MA","MG","ML","MR","MU","MW","MZ","NA","NE","NG","RE","RW","SC","SD","SH","SL","SN","SO","SS","ST","SZ","TD","TG","TN","TZ","UG","YT","ZA","ZM","ZW"))
            .put("Antarctica",ImmutableSet.of("AQ","BV","GS","HM","TF"))
            .put("Asia",ImmutableSet.of("AE","AF","AM","AZ","BD","BH","BN","BT","CC","CN","CX","GE","HK","ID","IL","IN","IO","IQ","IR","JO","JP","KG","KH","KP","KR","KW","KZ","LA","LB","LK","MM","MN","MO","MV","MY","NP","OM","PH","PK","PS","QA","SA","SG","SY","TH","TJ","TM","TR","TW","UZ","VN","YE"))
            .put("Europe",ImmutableSet.of("AD","AL","AT","AX","BA","BE","BG","BY","CH","CY","CZ","DE","DK","EE","ES","FI","FO","FR","GB","GG","GI","GR","HR","HU","IE","IM","IS","IT","JE","LI","LT","LU","LV","MC","MD","ME","MK","MT","NL","NO","PL","PT","RO","RS","RU","SE","SI","SJ","SK","SM","UA","VA","XK"))
            .put("North America",ImmutableSet.of("AG","AI","AW","BB","BL","BM","BQ","BS","BZ","CA","CR","CU","CW","DM","DO","GD","GL","GP","GT","HN","HT","JM","KN","KY","LC","MF","MQ","MS","MX","NI","PA","PM","PR","SV","SX","TC","TT","US","VC","VG","VI"))
            .put("Oceania",ImmutableSet.of("AS","AU","CK","FJ","FM","GU","KI","MH","MP","NC","NF","NR","NU","NZ","PF","PG","PN","PW","SB","TK","TL","TO","TV","UM","VU","WF","WS"))
            .put("South America",ImmutableSet.of("AR","BO","BR","CL","CO","EC","FK","GF","GY","PE","PY","SR","UY","VE"))
            .build()
            ;
    private final ImmutableGraph<String> countryContinent;

    CountryDatabase() {
        var graphBuilder = GraphBuilder.undirected()
                .allowsSelfLoops(false)
                .<String>immutable();

        ListMultimap<String,String> builder = MultimapBuilder.hashKeys().arrayListValues().build();
        continentCountries.forEach((continent, countries) ->
                countries.forEach(country ->
                        graphBuilder.putEdge(country,continent)
                )
        );
        countryContinent = graphBuilder.build();
    }

    /// Returns the continent of a country
    public String getContinent(String country) {
        return countryContinent.adjacentNodes(country).iterator().next();
    }

    /// Returns true if the country code is known to the database
    public boolean isCountry(String country) {
        return countryContinent.nodes().contains(country);
    }

    /// get the countries in a continent
    public List<String> getCountries(String continent) {
        return countryContinent.adjacentNodes(continent).stream().toList();
    }
}
