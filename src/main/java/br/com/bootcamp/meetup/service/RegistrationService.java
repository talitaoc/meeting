package br.com.bootcamp.meetup.service;

import br.com.bootcamp.meetup.exception.BusinessException;
import br.com.bootcamp.meetup.model.Registration;
import br.com.bootcamp.meetup.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    @Transactional
    public Registration save(Registration registration){

        if(registrationRepository.existsByCpf(registration.getCpf())){
            throw new BusinessException("Already exists.");
        }

        return registrationRepository.save(registration);
    }

    public boolean existsByCpf(Long cpf){
        return registrationRepository.existsByCpf(cpf);
    }

    public Registration update(Registration registration){

        if(Objects.isNull(registration)){
            throw new BusinessException("Registration not found.");
        }

        return registrationRepository.save(registration);
    }

    public Registration getRegistrationByCpf(Long cpf){

        if(Objects.isNull(cpf)){
          throw new BusinessException("Registration not found.");
        }

        return registrationRepository.findByCpf(cpf);
    }

    public Page<Registration> findAllRegistration(){
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "name");

        return new PageImpl<>(registrationRepository.findAll(),pageRequest,size);
    }

    @Transactional
    public void delete(Registration registration){

        if(Objects.isNull(registration)){
            throw new BusinessException("Registration can not be deleted, because does not exist.");
        }

        registrationRepository.delete(registration);
    }


}
