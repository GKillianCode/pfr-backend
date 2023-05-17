package com.pfr.pfr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfr.pfr.booking.BookingService;
import com.pfr.pfr.entities.*;
import com.pfr.pfr.entities.Event;
import com.pfr.pfr.entities.EventType;
import com.pfr.pfr.entities.Promo;
import com.pfr.pfr.location.LocationService;
import com.pfr.pfr.promo.PromoService;
import com.pfr.pfr.promo.dto.PromoWithBookings;
import com.pfr.pfr.promo.PromoService;
import com.pfr.pfr.promo.dto.PromoDTO;
import com.pfr.pfr.promo.dto.PromoWithEvents;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class PromoTests {

    @Autowired
    private LocationService locationService;

    @Autowired
    private PromoService promoService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookingService bookingService;

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
    void testGetAllPromoByAPI() throws Exception {
        Promo promoToCheck = promoService.getAll().get(0);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/promo/all");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<Promo> promos = Arrays.asList(objectMapper.readValue(contentAsString, Promo[].class));
        assert promos.contains(promoToCheck);
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
        cdaEvents.add(new Event("Entretien",
                "Jenny",
                "Jensen",
                "jenjen@gmail.com",
                "111111111",
                "Entretien individuel avec les étudiants",
                2,
                new EventType("Entretien", false),
                cda));

        cdaEvents.add(new Event("Cours de bases de données",
                "Marie",
                "Dubois",
                "marie.dubois@example.com",
                "9876543210",
                "Conception, modélisation et gestion de bases de données relationnelles",
                13,
                new EventType("Cours", false),
                cda));

        assert promoWithEvent.equals(new PromoWithEvents(cda, cdaEvents));
    }

    @Test
    void testGetPromoWithBookings() throws Exception
    {
        int promoId = 1;
        RequestBuilder request = MockMvcRequestBuilders.get("/api/promo/"+promoId+"/bookings");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        PromoWithBookings promoWithBookingsByApi = objectMapper.readValue(contentAsString, PromoWithBookings.class);

        Promo promo1 = promoService.getPromoById(promoId);
        List<Booking> bookings = bookingService.getBookingsForPromo(promoId);
        PromoWithBookings promoWithBookings = new PromoWithBookings(promo1, bookings);

        assert promoWithBookings.equals(promoWithBookingsByApi);

    }

    @Test
    @Transactional
    void testSavePromo() throws Exception {
        Promo newPromo = new Promo("CDA_1_2025", 24, false);
        Promo existingPromo = new Promo("CDA_2_2022", 13, true);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/promo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPromo));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();

        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Promo promo = objectMapper.readValue(contentAsString, Promo.class);
        assert promo.equals(newPromo);
    }

    @Test
    void testSavePromoConflict() throws Exception {
        Promo promo1 = promoService.getAll().get(0);

        // Vérification conflit à l'ajout
        RequestBuilder request = MockMvcRequestBuilders.post("/api/promo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(promo1));
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isConflict();
        mockMvc.perform(request).andExpect(resultStatus);

        // Vérifcation qu'il y a bien une seule promo avec ce nom (que la nouvelle n'a pas été ajoutée)
        List<Promo> promoList = promoService.getPromoByExactName(promo1.getName());
        assert promoList.size() == 1;
    }

    @Test
    @Transactional
    void testUpdatePromo() throws Exception {
        Promo promoToUpdate = promoService.getAll().get(0);
        String promoName = promoToUpdate.getName();
        Integer promoNbStudents = promoToUpdate.getStudentsNumber();
        Boolean promoIsActive = promoToUpdate.getIsActive();

        PromoDTO promoDTO = new PromoDTO(promoName + "Z", promoNbStudents + 1, null);

        RequestBuilder request = MockMvcRequestBuilders.patch("/api/promo/" + promoToUpdate.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(promoDTO));
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Promo promoUpdated = promoService.getAll().get(0);
        Promo promoExpected = new Promo(promoDTO.getName(), promoDTO.getStudentsNumber(), promoToUpdate.getIsActive());
        assert promoExpected.equals(promoUpdated);
    }

    @Test
    void testUpdatePromoConflictName() throws Exception {
        Promo promoToUpdate = promoService.getAll().get(0);
        String alreadyExistingName = promoService.getAll().get(1).getName();

        PromoDTO promoDTO = new PromoDTO(alreadyExistingName, null, null);

        // Vérification conflit à la modification du nom
        RequestBuilder request = MockMvcRequestBuilders.patch("/api/promo/" + promoToUpdate.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(promoDTO));
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isConflict();
        mockMvc.perform(request).andExpect(resultStatus);

        // Vérifcation qu'il y a bien toujours une seule promo avec le nom qu'on a voulu modifier (que la promo n'a pas été modifiée)
        List<Promo> promoList = promoService.getPromoByExactName(alreadyExistingName);
        assert promoList.size() == 1;
    }
}
