package br.com.bootcamp.meetup.dtos;

import br.com.bootcamp.meetup.model.Registration;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationDTO {

    @NotNull
    private String name;
    @NotNull
    private String group;
    @NotNull
    private Long cpf;

    public Registration convert(){

        Registration registration = new Registration();

        registration.setName(this.name);
        registration.setGroup(this.group);
        registration.setCpf(this.cpf);

        return registration;
    }

}
