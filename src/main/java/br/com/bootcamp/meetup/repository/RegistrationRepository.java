package br.com.bootcamp.meetup.repository;

import br.com.bootcamp.meetup.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    boolean existsById(Long id);

    Registration getById(Long id);

    boolean existsByCpf(Long cpf);

    Registration getByCpf(Long cpf);

    Registration findByRegistration(String registrationAttribute);
}
