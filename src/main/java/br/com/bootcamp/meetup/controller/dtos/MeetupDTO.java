package br.com.bootcamp.meetup.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetupDTO {

    private String registrationAttribute;

    private String event;

    private RegistrationDTO registration;

}
