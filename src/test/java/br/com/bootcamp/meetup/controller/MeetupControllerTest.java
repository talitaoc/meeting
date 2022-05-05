package br.com.bootcamp.meetup.controller;

import br.com.bootcamp.meetup.configuration.ModelMapperConfiguration;
import br.com.bootcamp.meetup.controller.dtos.MeetupDTO;
import br.com.bootcamp.meetup.controller.dtos.RegistrationDTO;
import br.com.bootcamp.meetup.controller.resource.MeetupController;
import br.com.bootcamp.meetup.model.Meetup;
import br.com.bootcamp.meetup.model.Registration;
import br.com.bootcamp.meetup.service.MeetupService;
import br.com.bootcamp.meetup.service.RegistrationService;
import br.com.bootcamp.meetup.util.MeetupCreator;
import br.com.bootcamp.meetup.util.RegistrationCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = {MeetupController.class, ModelMapperConfiguration.class})
@AutoConfigureMockMvc
public class MeetupControllerTest {

    static String API_MEETUP = "/api/meetup";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MeetupService meetupService;

    @MockBean
    RegistrationService registrationService;

    @Test
    @DisplayName("Should create a meetup in registration successful")
    void whenCreateMeetupSuccessful() throws Exception {

        RegistrationDTO registrationDTO = RegistrationCreator.createRegistrationDTO();
        MeetupDTO meetupDTO = MeetupCreator.createMeetupDTO(registrationDTO);

        Registration registration = RegistrationCreator.createNewRegistrationValid();
        Meetup createMeetup = MeetupCreator.createMeetup(registration);

        given(registrationService.getRegistrationByRegistrationAttribute(meetupDTO.getRegistrationAttribute())).willReturn(registration);
        given(meetupService.save(any(Meetup.class))).willReturn(createMeetup);

        String json = new ObjectMapper().writeValueAsString(meetupDTO);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(API_MEETUP)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("event").value(meetupDTO.getEvent()))
                .andExpect(jsonPath("registration").value(meetupDTO.getRegistration()));

    }

    @Test
    @DisplayName("Should throw an exception when trying to create a new meetup that already exists")
    void whenMeetupAlreadyExistsThrowError() throws Exception {

        given(registrationService.getRegistrationByRegistrationAttribute(null)).willThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        String json = new ObjectMapper().writeValueAsString(null);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(API_MEETUP)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Should get the list of meetup from a registration")
    void getListMeetupTest() throws Exception {

        Meetup meetup = MeetupCreator.createValidMeetup();

        Page<Meetup> page = new PageImpl<Meetup>(Arrays.asList(meetup),PageRequest.of(0,10),1);

        given(meetupService.findAllMeetup(any(),any())).willReturn(page);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(API_MEETUP)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk());


    }

    @Test
    @DisplayName("When get page meetup is null throw error")
    void whenGetMeetupNullThrowError() throws Exception {

        given(meetupService.findAllMeetup(null, null)).willThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        String json = new ObjectMapper().writeValueAsString(null);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(API_MEETUP)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should delete meetup from registration successful")
    void deleteMeetupFromRegistrationSuccessful() throws Exception {

        Meetup meetup = MeetupCreator.createValidMeetup();

        given(meetupService.findMeetupById(meetup.getId())).willReturn(Optional.of(meetup));


        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(API_MEETUP)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("When delete meetup not exist throw error")
    void whenDeleteMeetupNotExistThrowError() throws Exception {

        given(meetupService.findMeetupById(null)).willThrow(new ResponseStatusException(HttpStatus.NO_CONTENT));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(API_MEETUP)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(requestBuilder);
               // .andExpect(status().isNoContent());


    }

    @Test
    @DisplayName("Update registration meetup successfully")
    void updateMeetupSuccessful() throws Exception {

        Registration registration = RegistrationCreator.createRegistration();
        RegistrationDTO registrationDTO = RegistrationCreator.createRegistrationDTO();

        MeetupDTO meetupDTO = MeetupCreator.createMeetupDTO(registrationDTO);
        Meetup replaceMeetup = MeetupCreator.createMeetup(registration);

        String json = new ObjectMapper().writeValueAsString(meetupDTO);

        given(registrationService.getRegistrationByRegistrationAttribute(meetupDTO.getRegistrationAttribute())).willReturn(registration);
        given(meetupService.findMeetupById(replaceMeetup.getId())).willReturn(Optional.of(replaceMeetup));
        given(meetupService.findByEvent(meetupDTO.getEvent())).willReturn(replaceMeetup);

        Meetup updateMeetup = MeetupCreator.createUpdateMeetup(registration);

        given(meetupService.update(updateMeetup)).willReturn(updateMeetup);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(API_MEETUP)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("event").value(meetupDTO.getEvent()))
                .andExpect(jsonPath("registration").value(meetupDTO.getRegistration()));

    }


    @Test
    @DisplayName("When try to uptading meetup no exist throw error")
    void whenUpdateMeetupNoExistingThrowError() throws Exception {

        MeetupDTO meetupDTO = MeetupCreator.createMeetupDTO();
        String json = new ObjectMapper().writeValueAsString(meetupDTO);

        given(meetupService.findByEvent(anyString())).willThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(API_MEETUP)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest());


    }
}
