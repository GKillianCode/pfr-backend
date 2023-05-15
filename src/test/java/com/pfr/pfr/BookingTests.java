package com.pfr.pfr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfr.pfr.booking.BookingService;
import com.pfr.pfr.booking.dto.BookingDTO;
import com.pfr.pfr.classroom.ClassroomService;
import com.pfr.pfr.entities.*;
import com.pfr.pfr.event.EventService;
import com.pfr.pfr.location.LocationService;
import com.pfr.pfr.promo.PromoService;
import com.pfr.pfr.slot.SlotService;
import com.pfr.pfr.user.UserService;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private SlotService slotService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ObjectMapper objectMapper;

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
    @Transactional
    void testSaveBookingByAPI() throws Exception {
        LocalDate date = LocalDate.of(2025, 4, 10);
        Location location = locationService.getAll().get(0);
        Classroom classroomSmall = new Classroom("TestSmall", 2, location, true);
        Classroom classroomBig = new Classroom("TestBig", 200, location, true);
        Integer classroomSmallId = classroomService.
        Integer classroomId = classroomService.getAll().get(0).getId();
        Integer slotId = slotService.getByDate(date).get(0).getId();
        Integer eventId = eventService.getAll().get(0).getId();
        Integer userId = userService.getAll().get(0).getId();

        BookingDTO newBookingDTO = new BookingDTO(date, classroomId, slotId, eventId, userId);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBookingDTO));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();

        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        BookingDTO bookingDTO = objectMapper.readValue(contentAsString, BookingDTO.class);
        assert bookingDTO.equals(newBookingDTO);
    }

    @Test
    @Transactional
    void testUpdateBookingByAPI() throws Exception {
        Booking bookingToUpdate = bookingService.getAll().get(0);
        LocalDate date = LocalDate.of(2025, 4, 10);
        Integer classroomId = classroomService.getAll().get(0).getId();
        Integer slotId = slotService.getByDate(date).get(0).getId();

    }
}
