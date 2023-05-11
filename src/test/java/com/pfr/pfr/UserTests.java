package com.pfr.pfr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfr.pfr.entities.Role;
import com.pfr.pfr.entities.User;
import com.pfr.pfr.user.UserService;
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
public class UserTests {

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
}
