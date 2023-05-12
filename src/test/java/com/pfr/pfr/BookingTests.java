package com.pfr.pfr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfr.pfr.booking.BookingService;
import com.pfr.pfr.booking.dto.BookingWithConflicts;
import com.pfr.pfr.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BookingService bookingService;

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
    void testGetAllBookingsByAPI() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/booking/all");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<Booking> bookings = Arrays.asList(objectMapper.readValue(contentAsString, Booking[].class));

        Classroom salle1 = new Classroom("Salle 1", 15, new Location("Tours Mame", "49 Bd Preuilly", "37000", "Tours"), false);

        Slot lundiMatin = new Slot("lundi", "matin", true);

        Event hackathon = new Event("Hackathon TechDays",
                "John",
                "Doe",
                "johndoe@email.com",
                "0123456789",
                "Developpez votre projet en equipe et relevez des defis techniques lors de notre Hackathon.",
                50,
                new EventType("Hackathon", false),
                new Promo("CDA_2_2022", 13, true));

        User johnDoe = new User("John", "Doe", "johndoe@gmail.com", "root", true, new Role("ROLE_FORMATEUR"));

        Booking booking = new Booking(
                LocalDate.of(2023, 5, 10),
                salle1,
                lundiMatin,
                hackathon,
                johnDoe
        );
        assert bookings.contains(booking);
    }

    @Test
    void testGetBookingWithConflicts() throws Exception {
        Integer bookingId = 1;
        RequestBuilder request = MockMvcRequestBuilders.get("/api/booking/"+bookingId+"/conflicts");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();
        BookingWithConflicts bookingWithConflictsByApi = objectMapper.readValue(contentAsString, BookingWithConflicts.class);

        BookingWithConflicts bookingWithConflicts = bookingService.getBookingWithConflicts(bookingId);

        assert bookingWithConflicts.equals(bookingWithConflictsByApi);

    }
}
