package br.com.bootcamp.meetup.controller;

import br.com.bootcamp.meetup.configuration.ModelMapperConfiguration;
import br.com.bootcamp.meetup.dtos.RegistrationDTO;
import br.com.bootcamp.meetup.model.Registration;
import br.com.bootcamp.meetup.service.RegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;



@Slf4j
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = {RegistrationController.class, ModelMapperConfiguration.class})
@AutoConfigureMockMvc
public class RegistrationControllerTest {

    static String MEETUP = "/meetup";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RegistrationService registrationService;


    @Test
    @DisplayName("Should create a registration with successful")
    void createRegistrationWhenSuccessful() throws Exception {

        RegistrationDTO registrationDTO = createNewRegistration();
        Registration savedRegistration = Registration.builder()
                .id(1L)
                .name(registrationDTO.getName())
                .cpf(registrationDTO.getCpf())
                .groupName(registrationDTO.getGroupName())
                .build();

        given(registrationService.save(any(Registration.class))).willReturn(savedRegistration);

        String json = new ObjectMapper().writeValueAsString(registrationDTO);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders //configuração da requisição
                .post(MEETUP)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc //lombok deixa os atributos finais
                .perform(requestBuilder) //busca a URL do endpoint /meetup verbo POST
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value(registrationDTO.getName()))
                .andExpect(jsonPath("cpf").value(registrationDTO.getCpf()))
                .andExpect(jsonPath("groupName").value(registrationDTO.getGroupName()));


    }

    private RegistrationDTO createNewRegistration(){
        return RegistrationDTO.builder()
                .name("Lola")
                .cpf(1234L)
                .groupName("Meetup kitten")
                .build();
    }
}


