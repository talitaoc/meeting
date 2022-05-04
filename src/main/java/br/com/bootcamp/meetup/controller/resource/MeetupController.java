package br.com.bootcamp.meetup.controller.resource;

import br.com.bootcamp.meetup.controller.dtos.MeetupDTO;
import br.com.bootcamp.meetup.controller.dtos.MeetupFilterDTO;
import br.com.bootcamp.meetup.controller.dtos.RegistrationDTO;
import br.com.bootcamp.meetup.model.Meetup;
import br.com.bootcamp.meetup.model.Registration;
import br.com.bootcamp.meetup.service.MeetupService;
import br.com.bootcamp.meetup.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/meetup")
@RequiredArgsConstructor
public class MeetupController {

    private final MeetupService meetupService;
    private final RegistrationService registrationService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private ResponseEntity<MeetupDTO> create(@RequestBody @Valid MeetupDTO meetupDTO){

        Registration registration = registrationService.getRegistrationByRegistrationAttribute(meetupDTO.getRegistrationAttribute());

        Meetup meetup = Meetup.builder()
                .registration(registration)
                .event(meetupDTO.getEvent())
                .meetupDate(LocalDateTime.now())
                .build();

        meetup = meetupService.save(meetup);

       return new ResponseEntity<>(fromMeetupToMeetupDTO(meetup),HttpStatus.CREATED);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<MeetupDTO> find(MeetupFilterDTO filterDTO, Pageable pageable){

        Page<Meetup> result = meetupService.findAllMeetup(filterDTO, pageable);
        List<MeetupDTO> meetupDTOList = result
                .getContent() //volta um objeto Meetup que ta no result
                .stream() // transforma Meetup numa Stream Meetup
                .map(MeetupController::fromMeetupToMeetupDTO).collect(Collectors.toList()); //justa todas as partes numa nova lista do tipo MeetupDTO
        return new PageImpl<MeetupDTO>(meetupDTOList,pageable, result.getTotalElements()); //retorna um PageImpl do tipo MeetupDTO
    }

   @PutMapping
   @ResponseStatus(HttpStatus.OK)
    public MeetupDTO update(@RequestBody MeetupDTO meetupDTO){


        Registration registration = registrationService.getRegistrationByRegistrationAttribute(meetupDTO.getRegistrationAttribute());

        Meetup meetup = meetupService.findByEvent(meetupDTO.getEvent());

        meetup.setEvent(meetupDTO.getEvent());
        meetup.setRegistration(registration);

        meetupService.update(meetup);

        return modelMapper.map(meetup, MeetupDTO.class);
   }

   @DeleteMapping(path = "{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void deleteMeetup(@PathVariable(value = "id", required = false) Long id){

       Optional<Meetup> meetup = meetupService.findMeetupById(id);
        meetupService.delete(meetup.get());


   }

    private static MeetupDTO fromMeetupToMeetupDTO(Meetup meetup){

        return MeetupDTO.builder()
                .id(meetup.getId())
                .event(meetup.getEvent())
                .registration(RegistrationDTO.builder()
                        .name(meetup.getRegistration().getName())
                        .cpf(meetup.getRegistration().getCpf())
                        .registration(meetup.getRegistration().getRegistration())
                        .build())
                .registrationAttribute(meetup.getRegistration().getRegistration())
                .build();


    }
    private static Meetup fromMeetupDTOToMeetup(MeetupDTO meetupDTO){

        return Meetup.builder()
                .event(meetupDTO.getEvent())
                .registration(Registration.builder()
                        .name(meetupDTO.getRegistration().getName())
                        .cpf(meetupDTO.getRegistration().getCpf())
                        .registration(meetupDTO.getRegistrationAttribute())
                        .build())
                .build();



    }

}
