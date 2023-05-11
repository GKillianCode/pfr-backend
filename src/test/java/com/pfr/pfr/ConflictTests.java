package com.pfr.pfr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfr.pfr.classroom.ClassroomService;
import com.pfr.pfr.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ConflictTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllConflictsByAPI() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/conflict/all");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<Conflict> conflicts = Arrays.asList(objectMapper.readValue(contentAsString, Conflict[].class));

        Location toursMame = new Location("Tours Mame", "49 Bd Preuilly", "37000", "Tours");
        Classroom salle1 = new Classroom("Salle 1", 15, toursMame, false);

        Slot lundiMatin = new Slot("lundi", "matin", true);
        EventType hackathon = new EventType("Hackathon", false);

        Promo cda22022 = new Promo("CDA_2_2022", 13, true);

        Event hackathonTechDays = new Event(
                "Hackathon TechDays",
                "John", "Doe",
                "johndoe@email.com",
                "0123456789",
                "Developpez votre projet en equipe et relevez des defis techniques lors de notre Hackathon.",
                50,
                hackathon,
                cda22022
                );

        Role roleFormateur = new Role("ROLE_FORMATEUR");

        Booking booking = new Booking(
                LocalDate.of(2023, 5, 10),
                salle1,
                lundiMatin,
                hackathonTechDays,
                new User("John", "Doe", "johndoe@gmail.com", "root", true, roleFormateur)
        );

        Event reunion = new Event(
                "Reunion equipe developpement",
                "Alexandre",
                "Meyer",
                "alexandremeyer@email.com",
                "0123456789",
                "Reunion de l'equipe de developpement pour discuter des projets en cours.",
                10,
                new EventType("Réunion", false),
                new Promo("TLOG_1", 19, true));

        User jason = new User("Jason", "Jensen", "jj@gmail.com", "12345", true, roleFormateur);

        Conflict conflict = new Conflict("Besoin urgent pour une reunion importante",
                LocalDateTime.of(2023, 5, 10, 9, 30, 0),
                booking,
                jason,
                reunion);
        assert conflicts.contains(conflict);
    }
}

