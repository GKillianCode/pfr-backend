package com.pfr.pfr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfr.pfr.entities.Event;
import com.pfr.pfr.entities.EventType;
import com.pfr.pfr.entities.Promo;
import com.pfr.pfr.event.EventService;
import com.pfr.pfr.event.dto.EventDTO;
import com.pfr.pfr.event.dto.EventWithBookings;
import com.pfr.pfr.event_type.EventTypeService;
import com.pfr.pfr.promo.PromoService;
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
public class EventTests {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventTypeService eventTypeService;

    @Autowired
    private PromoService promoService;

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
    void testGetAllEventByAPI() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/event/all");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<Event> events = Arrays.asList(objectMapper.readValue(contentAsString, Event[].class));

        Event hackathon = new Event("Hackathon TechDays",
                "John",
                "Doe",
                "johndoe@email.com",
                "0123456789",
                "Developpez votre projet en equipe et relevez des defis techniques lors de notre Hackathon.",
                50,
                new EventType("Hackathon", false),
                new Promo("CDA_2_2022", 13, true));

        assert events.contains(hackathon);
    }

    @Test
    void testGetEventWithBookings() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/event/1/bookings");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        EventWithBookings eventWithBookings = objectMapper.readValue(contentAsString, EventWithBookings.class);

        Event hackathon = new Event("Hackathon TechDays",
                "John",
                "Doe",
                "johndoe@email.com",
                "0123456789",
                "Developpez votre projet en equipe et relevez des defis techniques lors de notre Hackathon.",
                50,
                new EventType("Hackathon", false),
                new Promo("CDA_2_2022", 13, true));

        assert eventWithBookings.getEvent().getName().equals(hackathon.getName());
    }

    @Test
    @Transactional
    void testSaveEvent() throws Exception {
        EventType conference;
        Event newEvent = new Event("Show Event",
                "George",
                "Sang",
                "george@sang.fr",
                "0102030405",
                "super show event",
                35,
                eventTypeService.getEventTypeById(5),
                null);
        Event existingEvent = new Event("Hackathon TechDays",
                "John",
                "Doe",
                "johndoe@email.com",
                "0123456789",
                "Developpez votre projet en equipe et relevez des defis techniques lors de notre Hackathon.",
                50,
                new EventType("Hackathon", false),
                new Promo("CDA_2_2022", 13, true));

        RequestBuilder request = MockMvcRequestBuilders.post("/api/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newEvent));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();

        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Event event = objectMapper.readValue(contentAsString, Event.class);
        assert event.equals(newEvent);
    }

    @Test
    @Transactional
    void testUpdateEvent() throws Exception {
        Event eventToUpdate = eventService.getAll().get(0);
        String eventName = eventToUpdate.getName();
        String eventSpeakerFN = eventToUpdate.getSpeakerFirstname();
        String eventSpeakerLN = eventToUpdate.getSpeakerLastName();
        String eventSpeakerEM = eventToUpdate.getSpeakerEmail();
        String eventSpeakerPN = eventToUpdate.getSpeakerPhoneNumber();
        String eventDescription = eventToUpdate.getDescription();
        Integer eventNbP = eventToUpdate.getParticipantsNumber();
        Integer eventET = eventToUpdate.getEventType().getId();
        Integer eventPromo = eventToUpdate.getPromo().getId();

        EventDTO eventDTO = new EventDTO(eventName + "Z", eventSpeakerFN + "z", eventSpeakerLN + "z", eventSpeakerEM + "z", eventSpeakerPN + "9", eventDescription + " zombie", eventNbP + 1, eventET, eventPromo);

        RequestBuilder request = MockMvcRequestBuilders.patch("/api/event/" + eventToUpdate.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(eventDTO));
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
            .andExpect(resultStatus)
            .andReturn().getResponse().getContentAsString();

        Event eventUpdated = eventService.getAll().get(0);
        Event eventExpected = new Event(
                eventDTO.getName(),
                eventDTO.getSpeakerFirstname(),
                eventDTO.getSpeakerLastName(),
                eventDTO.getSpeakerEmail(),
                eventDTO.getSpeakerPhoneNumber(),
                eventDTO.getDescription(),
                eventDTO.getParticipantsNumber(),
                eventTypeService.getEventTypeById(eventDTO.getEventTypeId()),
                promoService.getPromoById(eventDTO.getPromoId())
        );
        assert eventExpected.equals(eventUpdated);
    }

    @Test
    @Transactional
    void testArchivedEvent() throws Exception {
        Event eventToArchived = eventService.getAll().get(0);
        RequestBuilder request = MockMvcRequestBuilders.patch("/api/event/" + eventToArchived.getId() + "/archived")
            .contentType(MediaType.APPLICATION_JSON);
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();
        Event eventArchived = objectMapper.readValue(contentAsString, Event.class);
        assert eventArchived.getIsArchived().equals(true);
    }
}
