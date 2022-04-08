package br.com.bootcamp.meetup.util;

import br.com.bootcamp.meetup.dtos.RegistrationDTO;
import br.com.bootcamp.meetup.model.Registration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RegistrationCreator {

    public static Registration createRegistrationSaved() {

        return Registration.builder()
                .name("Lola")
                .cpf(1234L)
                .groupName("Meetup kitten")
                .build();
    }

    public static Registration createNewRegistrationValid(){

        return Registration.builder()
                .id(1L)
                .name("Lola")
                .cpf(1234L)
                .groupName("Meetup kitten")
                .build();
    }

    public static Registration createUpdateRegistration(){

        return Registration.builder()
                .id(1L)
                .name("Lince")
                .cpf(1234L)
                .groupName("Meetup kitten")
                .build();
    }

    public static List<Registration> ListRegistration(){

        List<Registration> lista = new ArrayList<>(List.of(createRegistrationSaved()));

        return lista;
    }
}


