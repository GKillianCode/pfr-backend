package com.pfr.pfr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfr.pfr.entities.EventType;
import com.pfr.pfr.event_type.EventTypeService;
import com.pfr.pfr.event_type.dto.EventTypeDTO;
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
public class EventTypeTests {

    @Autowired
    private EventTypeService eventTypeService;

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
    void testGetAllEventTypeByAPI() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/event-type/all");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<EventType> eventTypes = Arrays.asList(objectMapper.readValue(contentAsString, EventType[].class));
        assert eventTypes.contains(new EventType("Hackathon", false));
    }


    @Test
    @Transactional
    void testSaveEventType() throws Exception {
        EventType eventType = new EventType("Name", false);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/event-type/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventType));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();

        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        EventType savedEventType = objectMapper.readValue(contentAsString, EventType.class);
        assert eventTypeService.getAll().contains(savedEventType);
    }
    @Test
    @Transactional
    void testUpdateEventType() throws Exception {
        EventType eventTypeToUpdate = eventTypeService.getAll().get(0);
        String eventTypeName = eventTypeToUpdate.getName();
        Boolean eventTypeIsExceptionnalClosure = eventTypeToUpdate.getIsExceptionalClosure();

        EventTypeDTO eventTypeDTO = new EventTypeDTO(eventTypeName + "Z", !eventTypeIsExceptionnalClosure);

        RequestBuilder request = MockMvcRequestBuilders.patch("/api/event-type/" + eventTypeToUpdate.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventTypeDTO));
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        EventType eventTypeUpdated = eventTypeService.getAll().get(0);
        EventType eventTypeExpected = new EventType(eventTypeDTO.getName(), eventTypeDTO.getIsExceptionalClosure());
        assert eventTypeExpected.equals(eventTypeUpdated);
    }

    @Test
    @Transactional
    void testArchivedEventType() throws Exception {
        EventType eventTypeToArchived = eventTypeService.getAll().get(0);

        RequestBuilder request = MockMvcRequestBuilders.patch("/api/event-type/" + eventTypeToArchived.getId() + "/archived")
                .contentType(MediaType.APPLICATION_JSON);
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        EventType eventTypeArchived = objectMapper.readValue(contentAsString, EventType.class);

        assert eventTypeArchived.getIsArchived().equals(true);
    }
}
