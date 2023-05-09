package com.pfr.pfr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfr.pfr.entities.Location;
import com.pfr.pfr.location.LocationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class LocationTests {

    @Autowired
    private LocationService locationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllLocations() {
        assert locationService
            .getAll()
            .contains(new Location("Tours Mame", "49 Bd Preuilly", "37000", "Tours"));
    }

    @Test
    void testGetAllLocationByAPI() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/location/all");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<Location> locations = Arrays.asList(objectMapper.readValue(contentAsString, Location[].class));
        assert locations.contains(new Location("Tours Mame", "49 Bd Preuilly", "37000", "Tours"));
    }

}
