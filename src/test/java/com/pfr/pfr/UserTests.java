package com.pfr.pfr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfr.pfr.entities.Role;
import com.pfr.pfr.entities.Promo;
import com.pfr.pfr.entities.User;
import com.pfr.pfr.user.UserService;
import com.pfr.pfr.user.dto.UserWithBookings;
import com.pfr.pfr.user.dto.UserWithPromos;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTests {

    @Autowired
    private UserService userService;

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
    void testGetAllUsers() {
        assert userService
                .getAll()
                .contains(new User("John", "Doe", "johndoe@gmail.com", "root", true, new Role("ROLE_FORMATEUR")));
    }

    @Test
    void testGetAllUsersByAPI() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/user/all");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<User> users = Arrays.asList(objectMapper.readValue(contentAsString, User[].class));
        assert users.contains(new User("John", "Doe", "johndoe@gmail.com", "root", true, new Role("ROLE_FORMATEUR")));
    }

    @Test
    void testGetUserWithPromosByAPI() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/user/1/promos");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        User user = new User("John", "Doe", "johndoe@gmail.com", "root", true, new Role("ROLE_FORMATEUR"));
        Promo promo1 = new Promo("CDA", 13, true);
        Promo promo9 = new Promo("CDA_1_2021", 6, false);
        List<Promo> listPromos = new ArrayList<Promo>();
        listPromos.add(promo1);
        listPromos.add(promo9);
        UserWithPromos userWithPromos = new UserWithPromos(user, listPromos);
        UserWithPromos userWithPromosFromAPI = objectMapper.readValue(contentAsString, UserWithPromos.class);

        assert userWithPromos.equals(userWithPromosFromAPI);
    }

    @Test
    void testConnectUser() throws Exception {
        User user = new User("John", "Doe", "johndoe@gmail.com", "root", true, new Role("ROLE_FORMATEUR"));
        RequestBuilder request = MockMvcRequestBuilders.post("/api/user/connect")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user));
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();
        assert contentAsString.equals("lorem ipsum connected");
    }

    @Test
    public void getUserWithBookings() {
        UserWithBookings user = userService.getUserWithBookings(1, 0, 10);
        assert user.getUser().getFirstname().equals("John");
    }

    @Test
    public void getUserWithBookingsAPI() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/user/1/bookings?pageNumber=0&itemsPerPage=10");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        UserWithBookings userWithBookings = objectMapper.readValue(contentAsString, UserWithBookings.class);

        assert userWithBookings.getUser().getFirstname().equals("John");
    }
}
