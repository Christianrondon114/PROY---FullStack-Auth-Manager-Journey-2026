package com.attemp3.sc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private MockMvc mvc;


    @Test
    @WithMockUser(roles = "ADMIN") // Simulamos un Admin
    void deleteUser_AsAdmin_ShouldWork() throws Exception {
        this.mvc.perform(delete("/api/users/17"))
                .andExpect(status().isOk()); // El admin puede borrar
    }

    @Test
    @WithMockUser(roles = "USER") // Simulamos un usuario norma@
    void deleteUser_AsUser_ShouldBeForbidden() throws Exception {
        this.mvc.perform(delete("/api/users/18"))
                .andExpect(status().isForbidden()); // 403: El usuario NO puede borrar
    }
}
