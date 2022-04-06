package br.com.bootcamp.meetup.repository;

import br.com.bootcamp.meetup.model.Registration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    boolean existsById(Long id);
    Registration getById(Long id);
}
