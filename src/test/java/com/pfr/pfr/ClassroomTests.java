package com.pfr.pfr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfr.pfr.classroom.ClassroomService;
import com.pfr.pfr.entities.Classroom;
import com.pfr.pfr.entities.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
}
