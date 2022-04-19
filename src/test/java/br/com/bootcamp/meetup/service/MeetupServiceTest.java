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
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

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
    @DisplayName("Save a meetup successful")
    void saveMeetupWhenSuccessful(){

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
    @DisplayName("Throw error when meetup already exists in this registration")
    void whenMeetupAlreadyExistsThrowError(){

        Meetup meetup = MeetupCreator.createValidMeetup();

        when(meetupRepository.existsByEvent(MeetupCreator.createValidMeetup().getEvent())).thenReturn(true);
        //when(meetupRepository.getByEvent(anyString())).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        assertThrows(ResponseStatusException.class, ()->meetupService.save(meetup));

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

        when(meetupRepository.findByRegistration(any(Registration.class),any(PageRequest.class))).thenReturn(page);

        Page<Meetup> result = meetupService.getRegistrationByMeetup(registration,pageRequest);

        assertNotNull(result);

        verify(meetupRepository,times(1)).findByRegistration(registration,pageRequest);

    }

    //TODO whenNotFoundRegistrationByMeetupThrowError
    //TODO updateMeetupSuccessful
    //TODO whenUpdateMeetupNotExistThrowError
    //TODO foundByEventSuccessful
    //TODO whenEventNotFoundThrowError
    //TODO foundMeetupAndDeleteSuccessful
    //TODO whenNotFoundMeetupForDeleteThrowError





}
