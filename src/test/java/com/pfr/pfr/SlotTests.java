package com.pfr.pfr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfr.pfr.entities.Slot;
import com.pfr.pfr.entities.Slot;
import com.pfr.pfr.entities.Slot;
import com.pfr.pfr.slot.dto.SlotDTO;
import com.pfr.pfr.slot.SlotService;
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
public class SlotTests {

    @Autowired
    private SlotService slotService;

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
    void testGetAllSlotsByAPI() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/slot/all");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<Slot> slots = Arrays.asList(objectMapper.readValue(contentAsString, Slot[].class));
        assert slots.contains(new Slot("lundi", "matin", true));
    }

    @Test
    void testGetSlotById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/slot/1");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Slot slot = objectMapper.readValue(contentAsString, Slot.class);

        assert slot.equals(new Slot("lundi", "matin", true));
    }

    @Test
    @Transactional
    void testSaveSlot() throws Exception {
        Slot slot = new Slot("lundi", "nuit",false);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/slot/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(slot));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();

        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Slot savedSlot = objectMapper.readValue(contentAsString, Slot.class);
        assert slotService.getAll().contains(savedSlot);
    }

    @Test
    @Transactional
    void testUpdateSlot() throws Exception {
        Slot slotToUpdate = slotService.getAll().get(0);
        String slotWeekDay = slotToUpdate.getWeekDay();
        String slotDayTime = slotToUpdate.getDaytime();

        SlotDTO slotDTO = new SlotDTO(slotWeekDay + "Z", slotDayTime + "2", slotToUpdate.getIsBookable());

        RequestBuilder request = MockMvcRequestBuilders.patch("/api/slot/" + slotToUpdate.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(slotDTO));
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Slot slotUpdated = slotService.getAll().get(0);
        Slot slotExpected = new Slot(slotDTO.getWeekDay(), slotDTO.getDaytime(), slotToUpdate.getIsBookable());
        assert slotExpected.equals(slotUpdated);
    }


    @Test
    @Transactional
    void testArchivedSlot() throws Exception {
        Slot slotToArchived = slotService.getAll().get(0);

        RequestBuilder request = MockMvcRequestBuilders.patch("/api/slot/" + slotToArchived.getId() + "/archived")
                .contentType(MediaType.APPLICATION_JSON);
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Slot slotArchived = objectMapper.readValue(contentAsString, Slot.class);

        assert slotArchived.getIsArchived().equals(true);
    }
}
