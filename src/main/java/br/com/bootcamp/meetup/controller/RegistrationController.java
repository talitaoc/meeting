package br.com.bootcamp.meetup.controller;

import br.com.bootcamp.meetup.dtos.RegistrationDTO;
import br.com.bootcamp.meetup.model.Registration;
import br.com.bootcamp.meetup.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/meetup")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/save")
    public Registration saveRegistration(@RequestBody RegistrationDTO registrationDTO){

        return registrationService.save(registrationDTO.convert());
    }

    @GetMapping("/get/{cpf}")
    public Registration getRegistration(@RequestParam(value = "cpf", required = false) Long cpf){
        return registrationService.getRegistrationByCpf(cpf);
    }

    @GetMapping("/get")
    public Page<Registration> getAllRegistration(){
        return registrationService.findAllRegistration();
    }

    //Update: se o usuário ja existe, atualiza, caso contrario faz cadastro.
    @PutMapping("/update")
    public Registration updateRegistration( @RequestBody RegistrationDTO registrationDTO){

      //TODO entender como setar os atributos por partes do registration

        return registrationService.update(registrationDTO.convert());


    }

    @DeleteMapping("/delete")
    public void deleteRegistration(@RequestParam(value = "cpf", required = false) Long cpf){
        registrationService.delete(registrationService.getRegistrationByCpf(cpf));
    }



}
