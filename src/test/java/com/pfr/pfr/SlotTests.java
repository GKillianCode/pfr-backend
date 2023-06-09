package com.pfr.pfr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfr.pfr.entities.Slot;
import com.pfr.pfr.slot.SlotService;
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
public class SlotTests {

    @Autowired
    private SlotService slotService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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

}
