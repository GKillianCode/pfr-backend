package com.pfr.pfr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfr.pfr.booking.BookingService;
import com.pfr.pfr.booking.dto.BookingWithConflicts;
import com.pfr.pfr.booking.BookingService;
import com.pfr.pfr.booking.dto.BookingDTO;
import com.pfr.pfr.classroom.ClassroomService;
import com.pfr.pfr.entities.*;
import com.pfr.pfr.event_type.EventTypeService;
import org.junit.jupiter.api.BeforeEach;
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
    private EventTypeService eventTypeService;

    @Autowired
    private PromoService promoService;

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

    @Test
    @Transactional
    void testSaveBookingByAPI() throws Exception {
        // 5 février 2024 => lundi
        LocalDate date1 = LocalDate.of(2024, 2, 5);
        Integer slotId1 = slotService.getByDate(date1).get(0).getId();

        // 4 mars 2024 => lundi
        LocalDate date2 = LocalDate.of(2024, 3, 4);
        Integer slotId2 = slotService.getByDate(date2).get(0).getId();

        Location location = locationService.getAll().get(0);
        EventType eventType = eventTypeService.getAll().get(0);
        Integer userId = userService.getAll().get(0).getId();

        Classroom classroom1 = classroomService.getAll().get(0);
        int capacityClassroom1 = classroom1.getCapacity();

        // Récupération d'une promo
        Promo promo = promoService.getAll().get(0);
        int promoEffectif = promo.getStudentsNumber();

        // Création de 2 classrooms
        Classroom classroomSmall = new Classroom("ClassroomSmall", 2, location, true);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/classroom").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(classroomSmall)));
        Classroom classroomBig = new Classroom("ClassroomBig", 200, location, true);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/classroom").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(classroomBig)));
        Integer classroomSmallId = classroomService.getClassroomByExactName("ClassroomSmall").get(0).getId();
        Integer classroomBigId = classroomService.getClassroomByExactName("ClassroomBig").get(0).getId();

        // Création event basique pour 2
        Event event1 = new Event(
                "Event test 1",
                null,
                null,
                null,
                null,
                "description test 1",
                2,
                eventType,
                null
        );
        mockMvc.perform(MockMvcRequestBuilders.post("/api/event").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(event1)));

        // Création event sans effectif (doit se baser sur effectif promo)
        Event event2 = new Event(
                "Event test 2",
                null,
                null,
                null,
                null,
                "description test 2",
                null,
                eventType,
                promo
        );
        mockMvc.perform(MockMvcRequestBuilders.post("/api/event").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(event2)));

        List<Event> eventList = eventService.getAll();
        Event event1Recup = eventList.stream().filter(event -> event.equals(event1)).findFirst().orElse(null);
        int event1RecupId = event1Recup.getId();
        Event event2Recup = eventList.stream().filter(event -> event.equals(event2)).findFirst().orElse(null);
        int event2RecupId = event2Recup.getId();

        // Création booking dans classroom de 2 et event pour 2 -> ça passe
        BookingDTO newBookingDTO1 = new BookingDTO(date1, classroomSmallId, slotId1, event1RecupId, userId);

        RequestBuilder request1 = MockMvcRequestBuilders.post("/api/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBookingDTO1));
        ResultMatcher resultStatus1 = MockMvcResultMatchers.status().isOk();
        String contentAsString1 = mockMvc.perform(request1)
                .andExpect(resultStatus1)
                .andReturn().getResponse().getContentAsString();

        // Création booking dans classroom de 200 et event pour effectif promo -> ça passe
        BookingDTO newBookingDTO2 = new BookingDTO(date1, classroomBigId, slotId1, event2RecupId, userId);

        RequestBuilder request2 = MockMvcRequestBuilders.post("/api/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBookingDTO2));
        ResultMatcher resultStatus2 = MockMvcResultMatchers.status().isOk();
        String contentAsString2 = mockMvc.perform(request2)
                .andExpect(resultStatus2)
                .andReturn().getResponse().getContentAsString();

        // Création booking dans classroom de 2 et event pour effectif promo -> ça ne doit pas passer
//        BookingDTO newBookingDTO3 = new BookingDTO(date2, classroomSmallId, slotId2, event2RecupId, userId);
//
//        RequestBuilder request3 = MockMvcRequestBuilders.post("/api/booking")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(newBookingDTO3));
//        ResultMatcher resultStatus3 = MockMvcResultMatchers.status().isConflict();
//        String contentAsString3 = mockMvc.perform(request3)
//                .andExpect(resultStatus3)
//                .andReturn().getResponse().getContentAsString();

        BookingDTO bookingDTO1 = objectMapper.readValue(contentAsString1, BookingDTO.class);
        BookingDTO bookingDTO2 = objectMapper.readValue(contentAsString2, BookingDTO.class);
//        BookingDTO bookingDTO3 = objectMapper.readValue(contentAsString3, BookingDTO.class);

        assert bookingDTO1.equals(newBookingDTO1);
        assert bookingDTO2.equals(newBookingDTO2);
//        assert !bookingService.getAll().contains(bookingDTO3);
    }

//    @Test
//    @Transactional
//    void testUpdateBookingByAPI() throws Exception {
//        Booking bookingToUpdate = bookingService.getAll().get(0);
//        LocalDate date = LocalDate.of(2025, 4, 10);
//        Integer classroomId = classroomService.getAll().get(0).getId();
//        Integer slotId = slotService.getByDate(date).get(0).getId();
//
//    }
}
