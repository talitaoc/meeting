package br.com.bootcamp.meetup.service;

import br.com.bootcamp.meetup.model.Meetup;
import br.com.bootcamp.meetup.repository.MeetupRepository;
import br.com.bootcamp.meetup.util.MeetupCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

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
    @DisplayName("Throw error when meetup already exists")
    void whenMeetupAlreadyExistsThrowError(){

        Meetup meetup = MeetupCreator.createValidMeetup();

        when(meetupRepository.existsByEvent(MeetupCreator.createValidMeetup().getEvent())).thenReturn(true);
        //when(meetupRepository.getByEvent(anyString())).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        assertThrows(ResponseStatusException.class, ()->meetupService.save(meetup));

    }
}
