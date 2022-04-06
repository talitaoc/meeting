package br.com.bootcamp.meetup.controller;

import br.com.bootcamp.meetup.dtos.RegistrationDTO;
import br.com.bootcamp.meetup.model.Registration;
import br.com.bootcamp.meetup.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/meetup")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final ModelMapper modelMapper;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RegistrationDTO> save(@RequestBody @Valid RegistrationDTO registrationDTO){

        Registration registration = modelMapper.map(registrationDTO,Registration.class);
        registration = registrationService.save(registration);

        return new ResponseEntity<>(modelMapper.map(registration,RegistrationDTO.class), HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<Registration>> list(){

        return ResponseEntity.ok(registrationService.findAllRegistration());
    }

    @GetMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Registration> findById(@PathVariable(value = "id", required = false) Long id){
        return ResponseEntity.ok(registrationService.findById(id));
    }

    @PutMapping
    public ResponseEntity<RegistrationDTO> replace(@RequestBody @Valid RegistrationDTO registrationDTO){

        Registration registration = modelMapper.map(registrationDTO,Registration.class);
        registration = registrationService.findById(registration.getId());

        registration.setName(registration.getName());
        registration.setCpf(registration.getCpf());
        registration.setGroup(registration.getGroup());
        registration.setDate(LocalDate.now());

        registrationService.save(registration);

        log.info("Update was a success {} ",registration);

        return new ResponseEntity<>(modelMapper.map(registration, RegistrationDTO.class), HttpStatus.CREATED);

    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable(value = "id", required = false) Long id){
        registrationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
