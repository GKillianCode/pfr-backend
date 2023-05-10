package com.pfr.pfr;

import com.pfr.pfr.entities.Role;
import com.pfr.pfr.role.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleTests {
    @Autowired
    private RoleService roleService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllRoles() {

        System.out.println(roleService.getAll());

        assert roleService
                .getAll()
                .contains(new Role("ROLE_ADMIN"));
    }

    @Test
    void testGetAllRolesByAPI() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/role/all");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<Role> roles = Arrays.asList(objectMapper.readValue(contentAsString, Role[].class));
        assert roles.contains(new Role("ROLE_ADMIN"));
    }


}
