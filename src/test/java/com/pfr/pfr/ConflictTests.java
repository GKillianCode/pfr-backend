package com.pfr.pfr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfr.pfr.classroom.ClassroomService;
import com.pfr.pfr.conflict.ConflictService;
import com.pfr.pfr.conflict.dto.ConflictDTO;
import com.pfr.pfr.entities.*;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

    @Autowired
    private ConflictService conflictService;

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
    void testGetAllConflictsByAPI() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/conflict/all");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<Conflict> conflicts = Arrays.asList(objectMapper.readValue(contentAsString, Conflict[].class));

        Conflict conflict = conflictService.getById(1);
        assert conflicts.contains(conflict);
    }

    @Test
    @Transactional
    void testAdd() throws Exception {
        ConflictDTO conflictDTO = new ConflictDTO("testDeFou", 1, 1, 1);
        String json = objectMapper.writeValueAsString(conflictDTO);
        RequestBuilder request = MockMvcRequestBuilders.post("/api/conflict")
                .contentType("application/json")
                .content(json);
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();
        Conflict conflict = objectMapper.readValue(contentAsString, Conflict.class);
        assert conflict.getId() != null;
    }

    @Test
    @Transactional
    void testDelete() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/api/conflict/1");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        mockMvc.perform(request)
                .andExpect(resultStatus);

        request = MockMvcRequestBuilders.get("/api/conflict/1");
        resultStatus = MockMvcResultMatchers.status().isNotFound();

        mockMvc.perform(request)
                .andExpect(resultStatus);

    }
}

