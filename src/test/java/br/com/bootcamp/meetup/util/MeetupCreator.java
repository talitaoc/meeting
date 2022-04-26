package br.com.bootcamp.meetup.util;

import br.com.bootcamp.meetup.controller.dtos.MeetupDTO;
import br.com.bootcamp.meetup.controller.dtos.MeetupFilterDTO;
import br.com.bootcamp.meetup.controller.dtos.RegistrationDTO;
import br.com.bootcamp.meetup.model.Meetup;
import br.com.bootcamp.meetup.model.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    public static List<Meetup> listMeetup(){

        List<Meetup> lista = new ArrayList<>(List.of(createValidMeetup()));

        return lista;
    }


    public static MeetupDTO createMeetupDTO(RegistrationDTO registrationDTO){
        return MeetupDTO.builder()
                .id(1L)
                .event("World Domination")
                .registrationAttribute("1234")
                .registration(registrationDTO)
                .build();
    }

    public static MeetupDTO createMeetupDTO() {
        return MeetupDTO.builder()
                .event("World Domination")
                .registrationAttribute("1234")
                .registration(RegistrationCreator.createRegistrationDTO())
                .build();
    }

    public static MeetupFilterDTO createMeetupFilterDTO() {
        return MeetupFilterDTO.builder()
                .event("World Domination")
                .registration("Cat")
                .build();
    }

    public static Meetup createUpdateMeetup(Registration registration) {
        return Meetup.builder()
                .event("friendly cat")
                .registration(registration)
                .meetupDate(LocalDateTime.now())
                .registered(true)
                .build();
    }
}

