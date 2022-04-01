package br.com.bootcamp.meetup.dtos;

import br.com.bootcamp.meetup.model.Registration;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationDTO {

    @NotNull
    private String name;
    @NotNull
    private String group;

    public Registration convert(){

        Registration registration = new Registration();

        registration.setName(this.name);
        registration.setGroup(this.group);

        return registration;
    }

}
