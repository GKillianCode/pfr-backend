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
