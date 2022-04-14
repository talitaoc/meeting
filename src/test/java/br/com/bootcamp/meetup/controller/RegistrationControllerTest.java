package br.com.bootcamp.meetup.controller;

import br.com.bootcamp.meetup.configuration.ModelMapperConfiguration;
import br.com.bootcamp.meetup.controller.dtos.RegistrationDTO;
import br.com.bootcamp.meetup.controller.resource.RegistrationController;
import br.com.bootcamp.meetup.model.Registration;
import br.com.bootcamp.meetup.service.RegistrationService;
import br.com.bootcamp.meetup.util.RegistrationCreator;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = {RegistrationController.class, ModelMapperConfiguration.class})
@AutoConfigureMockMvc
public class RegistrationControllerTest {

    static String API_REGISTRATION = "/api/registration";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RegistrationService registrationService;


    @Test
    @DisplayName("Should create a registration with successful")
    void whenCreateRegistrationSuccessful() throws Exception {

        RegistrationDTO registrationDTO = RegistrationCreator.createRegistrationDTO();
        Registration savedRegistration = RegistrationCreator.createNewRegistrationValid();

        given(registrationService.save(any(Registration.class))).willReturn(savedRegistration);

        String json = new ObjectMapper().writeValueAsString(registrationDTO);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders //configuração da requisição
                .post(API_REGISTRATION)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc //lombok deixa os atributos finais
                .perform(requestBuilder) //busca a URL do endpoint /api/registration verbo POST
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value(registrationDTO.getName()))
                .andExpect(jsonPath("cpf").value(registrationDTO.getCpf()))
                .andExpect(jsonPath("groupName").value(registrationDTO.getGroupName()));
    }

    @Test
    @DisplayName("Should throw an exception when try to create a new registration with an registration already exists ")
    void whenCreateInvalidRegistrationTest() throws Exception {

        String json = new ObjectMapper().writeValueAsString(new RegistrationDTO());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(API_REGISTRATION)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Should get registration information")
    void getRegistrationTest() throws Exception {

        Long cpf = 1234L;
        Registration registration = RegistrationCreator.createNewRegistrationValid();

        given(registrationService.findByCpf(cpf)).willReturn(registration);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(API_REGISTRATION.concat("/" + cpf))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(registration.getName()))
                .andExpect(jsonPath("cpf").value(registration.getCpf()))
                .andExpect(jsonPath("groupName").value(registration.getGroupName()));
    }

    @Test
    @DisplayName("Should return NOT FOUND when the registration doesn't exists")
    void whenRegistrationNotFoundTest() throws Exception {

       given(registrationService.findByCpf(anyLong())).willThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(API_REGISTRATION.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Should find all registration")
    void whenFindAllRegistrationTest() throws Exception {

        List<Registration> registration = RegistrationCreator.ListRegistration();

        given(registrationService.findAllRegistration()).willReturn(registration);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(API_REGISTRATION)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should delete the registration successful")
    void deleteRegistrationSuccessfulTest() throws Exception {

        given(registrationService.findByCpf(anyLong())).willReturn(Registration.builder()
                .cpf(1234L)
                .build());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(API_REGISTRATION.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should update when registration info")
    void updateRegistrationSuccessfulTest() throws Exception {

        Long cpf = 1234L;
        String json = new ObjectMapper().writeValueAsString(RegistrationCreator.createRegistrationDTO());

        Registration replacingRegistration = RegistrationCreator.createRegistration();//lola

        given(registrationService.findByCpf(anyLong())).willReturn(replacingRegistration);

        Registration replacedRegistration = RegistrationCreator.createUpdateRegistration();//lince

        given(registrationService.update(replacingRegistration)).willReturn(replacedRegistration);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(API_REGISTRATION)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(replacingRegistration.getName()))
                .andExpect(jsonPath("cpf").value(cpf))
                .andExpect(jsonPath("groupName").value(replacingRegistration.getGroupName()));

    }

    @Test
    @DisplayName("Should return an error when trying to update a non-existing registration")
    void whenUpdateRegistrationNoExistingError() throws Exception {

        String json = new ObjectMapper().writeValueAsString(RegistrationCreator.createRegistrationDTO());

        given(registrationService.findByCpf(anyLong())).willThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(API_REGISTRATION)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }
}


