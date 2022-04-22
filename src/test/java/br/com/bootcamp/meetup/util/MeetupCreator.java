package br.com.bootcamp.meetup.util;

import br.com.bootcamp.meetup.model.Meetup;
import br.com.bootcamp.meetup.model.Registration;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;


public class MeetupCreator {

    public static Meetup createValidMeetup(){
        return Meetup.builder()
                .event("World Domination")
                .registration(RegistrationCreator.createNewRegistrationValid())
                .meetupDate(LocalDateTime.now())
                .registered(true)
                .build();
    }
    public static Meetup createMeetup(Registration registration){
        return Meetup.builder()
                .event("World Domination")
                .registration(registration)
                .meetupDate(LocalDateTime.now())
                .registered(true)
                .build();
    }

    public static Meetup createNewMeetup(String event) {
        return Meetup.builder()
                .event(event)
                .meetupDate(LocalDateTime.now())
                .registered(true)
                .build();
    }

    public static List<Meetup> ListMeetup(){

        List<Meetup> lista = new ArrayList<>(List.of(createValidMeetup()));

        return lista;
    }
}
