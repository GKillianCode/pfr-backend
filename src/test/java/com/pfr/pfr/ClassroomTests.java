package com.pfr.pfr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfr.pfr.booking.BookingService;
import com.pfr.pfr.classroom.ClassroomService;
import com.pfr.pfr.classroom.dto.ClassroomWithBookings;
import com.pfr.pfr.entities.Booking;
import com.pfr.pfr.entities.Classroom;
import com.pfr.pfr.entities.Location;
import com.pfr.pfr.entities.repository.ClassroomRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
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
public class ClassroomTests {

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    @Lazy
    private BookingService bookingService;

    @Autowired
    private ClassroomRepository classroomRepository;

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

        List<Booking> getBookingsByService = bookingService.getBookingsByClassroom(1);

        ClassroomWithBookings cWB = new ClassroomWithBookings(salle1, getBookingsByService);

        assert classroomWithBookings.equals(cWB);
    }

    @Test
    void testGetAllClassroomsAndItsBookingsByDateAndSlots() throws Exception {

        List<Booking> getBookingsByService = bookingService.getBookingsByClassroom(1);
        ClassroomWithBookings cWB = classroomService.getClassroomWithBookings(1);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/classroom/all/bookings?weekNumber=19&year=2023");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<ClassroomWithBookings> classroomWithBookings = Arrays.asList(objectMapper.readValue(contentAsString, ClassroomWithBookings[].class));

        assert classroomWithBookings.contains(cWB);
    }

    @Test
    @Transactional
    void testPostClassroomAPI() throws Exception {
        Location location = new Location("Tours Mame", "49 Bd Preuilly", "37000", "Tours");
        location.setId(1);
        Classroom classroom = new Classroom("Salle testPostClassroomAPI", 20, location, true);
        RequestBuilder request = MockMvcRequestBuilders.post("/api/classroom")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(classroom));
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();
        assert classroomService.getClassroomsByCapacity(20).contains(classroom);
    }

    @Test
    @Transactional
    void testUpdateClassroomAPI() throws Exception {
        Classroom classroomToUpdate = classroomService.getAll().get(0);
        String classroomName = classroomToUpdate.getName();
        Integer classroomCapacity = classroomToUpdate.getCapacity();
        Boolean classroomIsBookable = classroomToUpdate.getIsBookable();

        Classroom classroom = new Classroom(classroomName + " updated", classroomCapacity + 5, classroomToUpdate.getLocation(), !classroomIsBookable);


        RequestBuilder request = MockMvcRequestBuilders.patch("/api/classroom/" + classroomToUpdate.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(classroom));
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Classroom classroomUpdated = classroomService.getAll().get(0);
        Classroom classroomExpected = new Classroom(classroom.getName(), classroom.getCapacity(), classroom.getLocation(), classroom.getIsBookable());
        assert classroomExpected.equals(classroomUpdated);
    }


    @Test
    @Transactional
    void testArchivedClassroomAPI() throws Exception {
        Classroom classroom = classroomRepository.findClassroomById(1);
        Boolean isArchived = classroom.getIsArchived();
        RequestBuilder request = MockMvcRequestBuilders.patch("/api/classroom/archive/1");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        assert classroom.getIsArchived() != isArchived;
    }
}
