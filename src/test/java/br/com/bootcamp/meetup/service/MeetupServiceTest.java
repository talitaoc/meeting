package br.com.bootcamp.meetup.service;

import br.com.bootcamp.meetup.model.Meetup;
import br.com.bootcamp.meetup.model.Registration;
import br.com.bootcamp.meetup.repository.MeetupRepository;
import br.com.bootcamp.meetup.util.MeetupCreator;
import br.com.bootcamp.meetup.util.RegistrationCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Optional;


import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class MeetupServiceTest {

    @SpyBean
    private MeetupService meetupService;

    @MockBean
    private MeetupRepository meetupRepository;


    @Test
    @DisplayName("Create a meetup successful")
    void createMeetupWhenSuccessful(){

        Meetup meetupToBeSaved = MeetupCreator.createValidMeetup();

        when(meetupRepository.existsByEvent(MeetupCreator.createValidMeetup().getEvent())).thenReturn(false);
        when(meetupRepository.save(meetupToBeSaved)).thenReturn(MeetupCreator.createValidMeetup());

        Meetup savedMeetup = meetupService.save(meetupToBeSaved);

        assertNotNull(savedMeetup);
        assertNotNull(savedMeetup.getId());
        assertNotNull(savedMeetup.getEvent());
        assertNotNull(savedMeetup.getRegistration());
        assertTrue(savedMeetup.getRegistered());

        verify(meetupRepository,times(1)).save(meetupToBeSaved);
    }


    @Test
    @DisplayName("Should get a meetup by id")
    void foundByIdAndReturnMeetup(){

        Meetup meetup = MeetupCreator.createValidMeetup();

        when(meetupRepository.getById(MeetupCreator.createValidMeetup().getId())).thenReturn(meetup);

        Optional<Meetup> foundMeetup = meetupService.findMeetupById(MeetupCreator.createValidMeetup().getId());

        assertNotNull(foundMeetup);

        verify(meetupRepository,times(1)).findById(MeetupCreator.createValidMeetup().getId());

    }

    @Test
    @DisplayName("Should return error when id not found")
    void whenMeetupNotFoundIdThrowError(){

        when(meetupRepository.getById(null)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        assertThrows(ResponseStatusException.class,()-> meetupService.findMeetupById(null));

        verify(meetupRepository,never()).findById(null);

    }

    @Test
    @DisplayName("Found registrations by meetup successful")
    void foundRegistrationByMeetupSuccessful(){

        Registration registration = RegistrationCreator.createNewRegistrationValid();
        Meetup meetup = MeetupCreator.createValidMeetup();
        PageRequest pageRequest = PageRequest.of(0,10);

        Page<Meetup> page = new PageImpl<Meetup>(Arrays.asList(meetup),PageRequest.of(0,10),1);

        when(meetupRepository.existsByRegistration(registration)).thenReturn(true);
        when(meetupRepository.findByRegistration(registration,pageRequest)).thenReturn(page);

        Page<Meetup> result = meetupService.getRegistrationByMeetup(registration,pageRequest);

        assertNotNull(result);

        verify(meetupRepository,times(1)).findByRegistration(registration,pageRequest);

    }

    @Test
    @DisplayName("When Not found Registration by Meetup throw error")
    void whenNotFoundRegistrationByMeetupThrowError(){

        Registration registration = null;
        PageRequest pageRequest = null;

        when(meetupRepository.existsByRegistration(any(Registration.class))).thenReturn(false);

        assertThrows(ResponseStatusException.class,()-> meetupService.getRegistrationByMeetup(null, null));

        verify(meetupRepository,never()).findByRegistration(registration,pageRequest);

    }

    @Test
    @DisplayName("Update meetup successful")
    void updateMeetupSuccessful(){

        Meetup meetupToBeUpdate = MeetupCreator.createValidMeetup();

        when(meetupRepository.existsByEvent(meetupToBeUpdate.getEvent())).thenReturn(true);
        when(meetupRepository.save(meetupToBeUpdate)).thenReturn(MeetupCreator.createValidMeetup());

        Meetup updatedMeetup = meetupService.update(meetupToBeUpdate);

        assertNotNull(updatedMeetup);
        assertNotNull(updatedMeetup.getId());
        assertNotNull(updatedMeetup.getEvent());
        assertNotNull(updatedMeetup.getMeetupDate());
        assertNotNull(updatedMeetup.getRegistration());
        assertNotNull(updatedMeetup.getRegistered());

        verify(meetupRepository,times(1)).save(meetupToBeUpdate);

    }

    @Test
    @DisplayName("When update meetup not found throw error")
    void whenUpdateMeetupNotExistThrowError(){

        Meetup meetup = null;

        when(meetupRepository.existsByEvent(null)).thenReturn(false);

        assertThrows(ResponseStatusException.class,()-> meetupService.update(null));

        verify(meetupRepository,never()).save(meetup);

    }

    @Test
    @DisplayName("Found meetup by event return meetup successful")
    void foundByEventReturnMeetupSuccessful(){

        Meetup meetup = MeetupCreator.createValidMeetup();

        when(meetupRepository.getMeetupByEvent(meetup.getEvent())).thenReturn(meetup);

        Meetup foundMeetup = meetupService.findByEvent(meetup.getEvent());

        assertNotNull(foundMeetup);

        verify(meetupRepository,times(1)).getMeetupByEvent(meetup.getEvent());

    }

    @Test
    @DisplayName("When event not found throw error")
    void whenEventNotFoundThrowError(){


        when(meetupRepository.getMeetupByEvent(null)).thenReturn(null);

        assertThrows(ResponseStatusException.class,()-> meetupService.findByEvent(null));

        verify(meetupRepository,never()).getMeetupByEvent(null);


    }

    @Test
    @DisplayName("Delete meetup successful")
    void whenDeleteMeetupSuccessful(){

        Meetup meetupToBeDelete = MeetupCreator.createValidMeetup();

        when(meetupRepository.getMeetupByEvent(meetupToBeDelete.getEvent())).thenReturn(meetupToBeDelete);

        assertDoesNotThrow(()-> meetupService.delete(meetupToBeDelete));

        verify(meetupRepository,times(1)).delete(meetupToBeDelete);

    }

    @Test
    @DisplayName("When meetup not found for delete throw error")
    void whenNotFoundMeetupForDeleteThrowError(){

        Meetup meetupNotFound = null;

        when(meetupRepository.getMeetupByEvent(null)).thenReturn(null);

        assertThrows(ResponseStatusException.class, ()-> meetupService.delete(null));

        verify(meetupRepository,never()).delete(meetupNotFound);

    }

    //TODO whenNotFoundMeetupForDeleteThrowError





}
