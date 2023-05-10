package com.pfr.pfr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfr.pfr.classroom.ClassroomService;
import com.pfr.pfr.entities.*;
import com.pfr.pfr.trainer_promo.TrainerPromoService;
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
public class TrainerPromoTests {

    @Autowired
    private TrainerPromoService trainerPromoService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllTrainerPromosByAPI() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/trainerPromo/all");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<TrainerPromo> trainerPromos = Arrays.asList(objectMapper.readValue(contentAsString, TrainerPromo[].class));
        Promo promo = new Promo("CDA", 13, true);
        //Role role = new Role()
        User user = new User("John", "Doe", "johndoe@gmail.com", "root", true, 1);
        TrainerPromo trainerPromo = new TrainerPromo(promo,user);
        assert trainerPromos.contains(trainerPromo);
    }

}
