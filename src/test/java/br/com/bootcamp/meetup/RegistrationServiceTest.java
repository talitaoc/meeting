package br.com.bootcamp.meetup;

import br.com.bootcamp.meetup.exception.BusinessException;
import br.com.bootcamp.meetup.model.Registration;
import br.com.bootcamp.meetup.repository.RegistrationRepository;
import br.com.bootcamp.meetup.service.RegistrationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class RegistrationServiceTest {

    @SpyBean
    private RegistrationService registrationService;

    @MockBean
    private RegistrationRepository registrationRepository;

    @Test
    @DisplayName("Save a Registration.")
    void saveRegistration(){

        Registration registration = createValidRegistration();

        when(registrationRepository.existsByCpf(1234L)).thenReturn(false);
        when(registrationRepository.save(registration)).thenReturn(createValidRegistration());

        Registration saveRegistration = registrationService.save(registration);

        assertNotNull(saveRegistration);

        verify(registrationRepository,times(1)).save(saveRegistration);

    }

    @Test
    @DisplayName("Registration already exists.")
    void whenRegistrationAlreadyExists(){

        Registration registration = createValidRegistration();

        when(registrationRepository.existsByCpf(1234L)).thenReturn(true);

        assertThrows(BusinessException.class, () -> registrationService.save(registration));

    }


    @Test
    @DisplayName("Should get an registration by cpf.")
    void whenFindByCpfFoundReturnRegistration(){

        Registration registration = createValidRegistration();

        when(registrationRepository.findByCpf(1L)).thenReturn(registration);

        Registration foundRegistration = registrationService.getRegistrationByCpf(1L);

        assertNotNull(foundRegistration);

        verify(registrationRepository, times(1)).findByCpf(1L);

    }

    @Test
    @DisplayName("Should return error when cpf doesn't exists.")
    void whenRegistrationNotFoundThrowError(){

        assertThrows(BusinessException.class,() -> registrationService.getRegistrationByCpf(null));

    }

    public Registration createValidRegistration(){

        return Registration.builder()
                .id(1L)
                .name("Lola")
                .cpf(1234L)
                .date(LocalDate.now())
                .group("001")
                .build();
    }
}
