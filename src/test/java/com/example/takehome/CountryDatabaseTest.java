package com.example.takehome;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringBootTest
class CountryDatabaseTest {

    @Autowired
    private CountryDatabase countryDatabase;

    // Test that some countries are in the database, this is a classic testing anti-pattern
    // we're testing static data which means the test and the code are tightly coupled
    // let's work under the assumption that US, CA and Asia will keep existing
    @Test
    void countryDatabaseTest() {
        // smoke test
        assertThat(countryDatabase).isNotNull();

        // getContinent
        assertThat(countryDatabase.getContinent("CA"))
                .isEqualTo("North America");
        assertThat(countryDatabase.getContinent("FR"))
                .isEqualTo("Europe");

        // isCountry
        assertThat(countryDatabase .isCountry("US"))
                .isTrue();

        // getCountries
        assertThat(countryDatabase.getCountries("North America"))
                .isNotNull()
                .isNotEmpty()
                .isInstanceOf(List.class)
                .contains("US", "CA");
        // this is all we should be testing in a unit test
        assertThat(countryDatabase.getCountries("Asia"))
                .isNotNull()
                .isInstanceOf(List.class)
                .isNotEmpty();
    }
}