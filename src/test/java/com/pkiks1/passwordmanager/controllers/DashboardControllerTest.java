package com.pkiks1.passwordmanager.controllers;

import com.pkiks1.passwordmanager.dto.CredentialDto;
import com.pkiks1.passwordmanager.security.FakeUserDetailsService;
import com.pkiks1.passwordmanager.services.CredentialService;
import com.pkiks1.passwordmanager.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DashboardController.class)
@Import(FakeUserDetailsService.class)
class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private CredentialService credentialService;

    @Test
    @WithUserDetails("testUser")
    void test_listAllCredentials() throws Exception {
        List<CredentialDto> credentials = new ArrayList<>();
        credentials.add(new CredentialDto.CredentialDtoBuilder()
                .withId("id1")
                .withTitle("title1")
                .withEmail("useremail1@email.com")
                .withPassword("pass1".toCharArray())
                .withUserId("userId1")
                .build());
        credentials.add(new CredentialDto.CredentialDtoBuilder()
                .withId("id2")
                .withTitle("title2")
                .withEmail("useremail2@email.com")
                .withPassword("pass2".toCharArray())
                .withUserId("userId2")
                .build());

        when(credentialService.getAllCredentialsForUserId(anyString())).thenReturn(credentials);

        this.mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attributeExists("credentials",
                        "credentialsSize",
                        "user"))
                .andExpect(model().attribute("credentials", credentials));

        verify(credentialService, times(2)).getAllCredentialsForUserId(anyString());
    }

    @Test
    @WithUserDetails("testUser")
    void test_getSelectedCredential() throws Exception {
        String userId = "userId1";
        CredentialDto credential = new CredentialDto.CredentialDtoBuilder()
                .withId("id1")
                .withTitle("title1")
                .withEmail("useremail1@email.com")
                .withPassword("pass1".toCharArray())
                .withUserId(userId)
                .build();

        when(credentialService.getCredentialForUser(any(CredentialDto.class))).thenReturn(Optional.of(credential));

        this.mockMvc.perform(get("/dashboard/" + userId))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attributeExists("viewCredential",
                        "credential",
                        "user"))
                .andExpect(model().attribute("credential", credential));

        verify(credentialService, times(1)).getCredentialForUser(any(CredentialDto.class));
    }

    @Test
    @WithUserDetails("testUser")
    void test_addCredential() throws Exception {
        String createdCredentialId = "credential1";

        when(credentialService.createCredential(any(CredentialDto.class))).thenReturn(createdCredentialId);

        this.mockMvc.perform(post("/dashboard")
                .param("title", "credentialTitle")
                .param("email", "userEmail")
                .param("password", "credentialPassword")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attributeExists("createdCredentialId",
                        "credentials",
                        "user"))
                .andExpect(model().attribute("createdCredentialId", createdCredentialId));

        verify(credentialService, times(1)).createCredential(any(CredentialDto.class));
    }
}