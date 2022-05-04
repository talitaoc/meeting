package br.com.bootcamp.meetup.controller.resource;

import br.com.bootcamp.meetup.controller.dtos.RegistrationDTO;
import br.com.bootcamp.meetup.model.Registration;
import br.com.bootcamp.meetup.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/registration")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RegistrationDTO> save(@RequestBody @Valid RegistrationDTO registrationDTO){

        Registration registration = modelMapper.map(registrationDTO,Registration.class);
        registration = registrationService.save(registration);


        return new ResponseEntity<>(modelMapper.map(registration,RegistrationDTO.class), HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<RegistrationDTO>> list(){

        List<Registration> registrationList = registrationService.findAllRegistration();

        List<RegistrationDTO> registrationDTO = registrationList
                .stream()
                .map(registration -> modelMapper.map(registration, RegistrationDTO.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(registrationDTO, HttpStatus.OK);
    }

    @GetMapping(path = "{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RegistrationDTO> findByCpf(@PathVariable(value = "cpf", required = false) Long cpf){

        Registration registration = registrationService.findByCpf(cpf);
        
        return new ResponseEntity<>(modelMapper.map(registration,RegistrationDTO.class), HttpStatus.OK);

    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RegistrationDTO> replace(@RequestBody RegistrationDTO registrationDTO){

        Registration registration = registrationService.findByCpf(registrationDTO.getCpf());

        registration.setName(registrationDTO.getName());
        registration.setCpf(registrationDTO.getCpf());
        registration.setRegistration(registrationDTO.getRegistration());

        registrationService.update(registration);

        log.info("Update was a success {} ",registration);

        return new ResponseEntity<>(modelMapper.map(registration, RegistrationDTO.class), HttpStatus.OK);

    }

    @DeleteMapping(path = "{cpf}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable(value = "cpf", required = false) Long cpf){
        registrationService.delete(cpf);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
