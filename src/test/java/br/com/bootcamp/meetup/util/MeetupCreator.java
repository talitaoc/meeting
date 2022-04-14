package br.com.bootcamp.meetup.util;

import br.com.bootcamp.meetup.model.Meetup;

import java.time.LocalDateTime;

public class MeetupCreator {

    public static Meetup createValidMeetup(){
        return Meetup.builder()
                .id(1L)
                .event("World Domination")
                .registration(RegistrationCreator.createNewRegistrationValid())
                .meetupDate(LocalDateTime.now())
                .registered(true)
                .build();
    }
}
