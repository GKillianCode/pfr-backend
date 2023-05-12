package com.pfr.pfr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfr.pfr.classroom.ClassroomService;
import com.pfr.pfr.classroom.dto.ClassroomWithBookings;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ClassroomTests {

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllClassroomsByAPI() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/classroom/all");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<Classroom> classrooms = Arrays.asList(objectMapper.readValue(contentAsString, Classroom[].class));
        Location location = new Location("Tours Mame", "49 Bd Preuilly", "37000", "Tours");
        assert classrooms.contains(new Classroom("Salle 2", 20, location, true));
    }

    @Test
    void testGetAllDistinctCapacities() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/classroom/capacities");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<Integer> capacities = Arrays.asList(objectMapper.readValue(contentAsString, Integer[].class));
        assert capacities.equals(Arrays.asList(15, 20, 22, 24));
    }

    @Test
    void testGetClassroomsByLocation() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/classroom/all/filter/location?id=1");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<Classroom> classrooms = Arrays.asList(objectMapper.readValue(contentAsString, Classroom[].class));
        Location location = new Location("Tours Mame", "49 Bd Preuilly", "37000", "Tours");
        assert classrooms.contains(new Classroom("Salle 2", 20, location, true));
    }

    @Test
    void testGetClassroomsByCapacity() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/classroom/all/filter/capacity?number=20");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<Classroom> classrooms = Arrays.asList(objectMapper.readValue(contentAsString, Classroom[].class));
        Location location = new Location("Tours Mame", "49 Bd Preuilly", "37000", "Tours");
        assert classrooms.contains(new Classroom("Salle 2", 20, location, true));
    }

    @Test
    void testFilterClassroomContaining() throws Exception {
        Classroom classroom = classroomService.getClassroomsByCapacity(20).get(0);
        Classroom classroom2 = new Classroom();
        classroom2.setCapacity(20);
        classroom2.setLocation(classroom.getLocation());

        RequestBuilder request = MockMvcRequestBuilders.post("/api/classroom/filter")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(classroom2));
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<Classroom> classrooms = Arrays.asList(objectMapper.readValue(contentAsString, Classroom[].class));
        assert classrooms.contains(classroom);
    }

    @Test
    void testGetClassroomWithBookings() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/classroom/1/bookings");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        ClassroomWithBookings classroomWithBookings = objectMapper.readValue(contentAsString, ClassroomWithBookings.class);

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

        ArrayList<Booking> salle1Bookings = new ArrayList<>();
        salle1Bookings.add(booking);
        ClassroomWithBookings cWB = new ClassroomWithBookings(salle1, salle1Bookings);

        assert classroomWithBookings.equals(cWB);
    }

}
