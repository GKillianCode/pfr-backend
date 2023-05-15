package com.pfr.pfr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfr.pfr.entities.Location;
import com.pfr.pfr.entities.Promo;
import com.pfr.pfr.location.LocationService;
import com.pfr.pfr.location.dto.LocationDTO;
import com.pfr.pfr.promo.dto.PromoDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

    @Autowired
    private WebApplicationContext webApplicationContext;
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8"); // this is crucial
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }
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
        Location location = new Location("Tours Mame", "49 Bd Preuilly", "37000", "Tours");
        assert locations.contains(location);
    }

    @Test
    @Transactional
    void testSavePromo() throws Exception {
        Location location = new Location("Name", "Address", "Zip Code", "City");

        RequestBuilder request = MockMvcRequestBuilders.post("/api/location/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(location));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();

        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Location savedLocation = objectMapper.readValue(contentAsString, Location.class);
        assert locationService.getAll().contains(savedLocation);
    }
    @Test
    @Transactional
    void testUpdateLocation() throws Exception {
        Location locationToUpdate = locationService.getAll().get(0);
        String locationName = locationToUpdate.getName();
        String locationAddress = locationToUpdate.getAddress();
        String locationCity = locationToUpdate.getCity();

        LocationDTO locationDTO = new LocationDTO(locationName + "Z", locationAddress + "2", locationToUpdate.getZipCode(), locationCity);

        RequestBuilder request = MockMvcRequestBuilders.patch("/api/location/" + locationToUpdate.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(locationDTO));
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Location locationUpdated = locationService.getAll().get(0);
        Location locationExpected = new Location(locationDTO.getName(), locationDTO.getAddress(), locationToUpdate.getZipCode(), locationDTO.getCity());
        assert locationExpected.equals(locationUpdated);
    }

    @Test
    @Transactional
    void testArchivedLocation() throws Exception {
        Location locationToArchived = locationService.getAll().get(0);

        RequestBuilder request = MockMvcRequestBuilders.patch("/api/location/" + locationToArchived.getId() + "/archived")
                .contentType(MediaType.APPLICATION_JSON);
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Location locationArchived = objectMapper.readValue(contentAsString, Location.class);

        assert locationArchived.getIsArchived().equals(true);
    }
}
