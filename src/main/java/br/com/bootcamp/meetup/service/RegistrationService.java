package br.com.bootcamp.meetup.service;


import br.com.bootcamp.meetup.model.Registration;
import br.com.bootcamp.meetup.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    @Transactional
    public Registration save(Registration registration){

        if(registrationRepository.existsByCpf(registration.getCpf())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Registration already exists.");
        }

        return registrationRepository.save(registration);
    }

    public Registration update(Registration registration){

        if(Objects.isNull(registration)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Registration not found.");
        }

        return registrationRepository.save(registration);
    }

    public Registration findByCpf(Long cpf){

        if(Objects.isNull(cpf)){
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Registration not found.");
        }
        return registrationRepository.getByCpf(cpf);
    }

    public List<Registration> findAllRegistration(){

        List<Registration> registrationList = registrationRepository.findAll();

        return registrationList;
    }

    @Transactional
    public void delete(Long cpf) {

        if(Objects.isNull(cpf)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Registration not found.");
        }
        registrationRepository.delete(findByCpf(cpf));

    }

    public Registration getRegistrationByRegistrationAttribute(String registrationAttribute){
        return registrationRepository.findByRegistration(registrationAttribute);
    }
}
