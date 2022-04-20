package br.com.bootcamp.meetup.controller.dtos;

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
    private String registration;
    @NotNull
    private Long cpf;

}
