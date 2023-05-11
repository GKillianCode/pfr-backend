package com.pfr.pfr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfr.pfr.entities.Event;
import com.pfr.pfr.entities.EventType;
import com.pfr.pfr.entities.Location;
import com.pfr.pfr.entities.Promo;
import com.pfr.pfr.location.LocationService;
import com.pfr.pfr.promo.dto.PromoWithEvents;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class PromoTests {

    @Autowired
    private LocationService locationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testGetAllPromoByAPI() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/promo/all");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<Promo> promos = Arrays.asList(objectMapper.readValue(contentAsString, Promo[].class));
        assert promos.contains(new Promo("CDA", 13, true));
    }

    @Test
    void testGetPromoWithEvents() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/promo/5/events");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        PromoWithEvents promoWithEvent = objectMapper.readValue(contentAsString, PromoWithEvents.class);

        Promo cda = new Promo("CDA_2_2022", 13, true);

        ArrayList<Event> cdaEvents = new ArrayList<>();
        cdaEvents.add(new Event("Hackathon TechDays",
                    "John",
                    "Doe",
                    "johndoe@email.com",
                    "0123456789",
                    "Developpez votre projet en equipe et relevez des defis techniques lors de notre Hackathon.",
                    50,
                    new EventType("Hackathon", false),
                    cda));

        assert promoWithEvent.equals(new PromoWithEvents(cda, cdaEvents));
    }
}
