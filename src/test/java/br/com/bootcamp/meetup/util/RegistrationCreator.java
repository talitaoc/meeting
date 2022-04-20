package br.com.bootcamp.meetup.util;

import br.com.bootcamp.meetup.controller.dtos.RegistrationDTO;
import br.com.bootcamp.meetup.model.Registration;

import java.util.ArrayList;
import java.util.List;

public class RegistrationCreator {

    public static Registration createRegistration() {

        return Registration.builder()
                .name("Lola")
                .cpf(1234L)
                .registration("Cat")
                .build();
    }

    public static RegistrationDTO createRegistrationDTO() {

        return RegistrationDTO.builder()
                .name("Lola")
                .cpf(1234L)
                .registration("Cat")
                .build();
    }

    public static Registration createNewRegistrationValid(){

        return Registration.builder()
                .id(1L)
                .name("Lola")
                .cpf(1234L)
                .registration("Cat")
                .build();
    }

    public static Registration createUpdateRegistration(){

        return Registration.builder()
                .id(1L)
                .name("Lince")
                .cpf(1234L)
                .registration("Cat")
                .build();
    }


    public static List<Registration> ListRegistration(){

        List<Registration> lista = new ArrayList<>(List.of(createRegistration()));

        return lista;
    }

    public static Registration createNewRegistration(Long cpf ) {
        return Registration.builder()
                .name("Lola")
                .cpf(cpf)
                .registration("Meetup kitten")
                .build();
    }
}


