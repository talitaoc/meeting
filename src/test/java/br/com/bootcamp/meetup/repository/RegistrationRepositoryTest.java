package br.com.bootcamp.meetup.repository;

import br.com.bootcamp.meetup.model.Registration;
import br.com.bootcamp.meetup.util.RegistrationCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
class RegistrationRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    RegistrationRepository registrationRepository;

    @Test
    @DisplayName("Should return true when exists an registration already created")
    void returnTrueWhenRegistrationCreated(){

        Long cpf = 1234L;

        Registration registrationClassAttribute = RegistrationCreator.createNewRegistration(cpf);
        entityManager.persist(registrationClassAttribute);

        boolean exists = registrationRepository.existsByCpf(cpf);

        assertTrue(exists);

    }

    @Test
    @DisplayName("Should return false when doesn't exists an registration attribute with a registration already created")
    void returnFalseWhenRegistrationAttributeDoesNotExists(){

        Long cpf = 1234L;

        boolean exists = registrationRepository.existsByCpf(cpf);

        assertFalse(exists);
    }

    @Test
    @DisplayName("Should get an registration by id")
    void getByIdTest(){

        Registration registrationClassAttribute = RegistrationCreator.createNewRegistration(1234L);
        entityManager.persist(registrationClassAttribute);

        Registration foundRegistration = registrationRepository.getById(registrationClassAttribute.getId());

        assertNotNull(foundRegistration);
    }

    @Test
    @DisplayName("Should save an registration")
    void saveRegistrationTest(){

        Registration registrationClassAttribute =  RegistrationCreator.createNewRegistration(1234L);
        Registration savedRegistration = registrationRepository.save(registrationClassAttribute);

        assertNotNull(savedRegistration.getCpf());
    }

    @Test
    @DisplayName("Should delete an registration from the base")
    void deleteRegistrationTest(){

        Registration registrationClassAttribute = RegistrationCreator.createNewRegistration(1234L);
        entityManager.persist(registrationClassAttribute);

        Registration foundRegistration = entityManager
                .find(Registration.class, registrationClassAttribute.getId());
        registrationRepository.delete(foundRegistration);

        Registration deleteRegistration = entityManager
                .find(Registration.class, registrationClassAttribute.getId());

        assertNull(deleteRegistration);
    }

}
