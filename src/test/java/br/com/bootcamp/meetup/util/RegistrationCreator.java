package br.com.bootcamp.meetup.util;

import br.com.bootcamp.meetup.model.Registration;

public class RegistrationCreator {

    public static Registration createNewRegistrationSaved() {

        return Registration.builder()
                .name("Lola")
                .cpf(1234L)
                .group("gatinho")
                .build();
    }

    public static Registration createNewRegistrationValid(){

        return Registration.builder()
                .id(1L)
                .name("Lola")
                .cpf(1234L)
                .group("gatinho")
                .build();
    }

    public static Registration createUpdateRegistration(){

        return Registration.builder()
                .id(1L)
                .name("Preta")
                .cpf(1234L)
                .group("gatinho")
                .build();
    }
}


