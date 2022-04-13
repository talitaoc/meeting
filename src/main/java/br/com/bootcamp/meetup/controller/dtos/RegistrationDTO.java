package br.com.bootcamp.meetup.dtos;

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
    private String groupName;
    @NotNull
    private Long cpf;

}
