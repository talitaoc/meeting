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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/meetup")
@RequiredArgsConstructor
public class MeetupController {

    private final MeetupService meetupService;
    private final RegistrationService registrationService;
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Long create(@RequestBody MeetupDTO meetupDTO){

        Registration registration = registrationService.getRegistrationByRegistrationAttribute(meetupDTO.getRegistrationAttribute())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Meetup meetup = Meetup.builder()
                .registration(registration)
                .event(meetupDTO.getEvent())
                .meetupDate(LocalDateTime.now())
                .build();

        meetup = meetupService.save(meetup);
        return meetup.getId();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<MeetupDTO> find(MeetupFilterDTO filterDTO, Pageable pageable){

        Page<Meetup> result = meetupService.findAllMeetup(filterDTO, pageable);
        List<MeetupDTO> meetupDTOList = result
                .getContent() //volta um objeto Meetup que ta no result
                .stream() // transforma Meetup numa Stream Meetup
                .map(meetup -> { //mapeia cada elemento da Stream de acordo com a funcao passada para cada MeetupDTO. transforma Meetup em MeetupDTO

                    Registration registration = meetup.getRegistration();
                    RegistrationDTO registrationDTO =  modelMapper.map(registration, RegistrationDTO.class);

                    MeetupDTO meetupDTO = modelMapper.map(meetup, MeetupDTO.class);
                    meetupDTO.setRegistration(registrationDTO);
                    return  meetupDTO;
                        }).collect(Collectors.toList()); //justa todas as partes numa nova lista do tipo MeetupDTO
        return new PageImpl<MeetupDTO>(meetupDTOList,pageable, result.getTotalElements()); //retorna um PageImpl do tipo MeetupDTO
    }

   @PutMapping
   @ResponseStatus(HttpStatus.OK)
    public MeetupDTO update(@RequestBody MeetupFilterDTO filterDTO){

        Meetup meetup = modelMapper.map(filterDTO, Meetup.class);
        meetup = meetupService.findByEvent(meetup.getEvent());

        meetup.setEvent(meetup.getEvent());
        meetup.setRegistration(meetup.getRegistration());
        meetup.setRegistered(meetup.getRegistered());

        meetupService.save(meetup);

        return modelMapper.map(meetup, MeetupDTO.class);
   }

   @DeleteMapping
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void delete(MeetupFilterDTO filterDTO){
        meetupService.delete(modelMapper.map(filterDTO, Meetup.class));
   }

}
