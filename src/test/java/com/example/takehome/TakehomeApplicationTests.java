package com.example.takehome;

import io.vavr.collection.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = EndPoint.class)
class TakehomeApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TakehomeApplication takehomeApplication;

    @MockBean
    private CountryDatabase countryDatabase;

    @Test
    void contextLoads() {
        // smoke test; it starts and listens
        assertThat(takehomeApplication).isNotNull();
    }

    @Test
    void testGetContinent() throws Exception {
        mockMvc.perform(post("/continents")
                    .contentType("application/json"))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(post("/continents")
                    .contentType("application/json")
                    .content("[]"))
                .andExpect(status().isOk());
        mockMvc.perform(post("/continents")
                        .contentType("application/json")
                        .content("[\"US\", \"CA\"]"))
                .andExpect(status().isOk());
    }

}
