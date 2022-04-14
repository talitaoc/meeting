package br.com.bootcamp.meetup.service;

import br.com.bootcamp.meetup.model.Registration;
import br.com.bootcamp.meetup.repository.RegistrationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;


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
    @DisplayName("Save a registration successful.")
    void saveRegistrationWhenSuccessful(){

        Registration registrationToBeSaved = createValidRegistration();

        when(registrationRepository.existsById(createValidRegistration().getId())).thenReturn(false);
        when(registrationRepository.save(registrationToBeSaved)).thenReturn(createValidRegistration());

        Registration savedRegistration = registrationService.save(registrationToBeSaved);

        assertNotNull(savedRegistration);
        assertNotNull(savedRegistration.getId());
        assertEquals(savedRegistration.getName(),registrationToBeSaved.getName());
        assertNotNull(savedRegistration.getCpf());
        assertEquals(savedRegistration.getGroupName(),registrationToBeSaved.getGroupName());

        verify(registrationRepository,times(1)).save(registrationToBeSaved);

    }

    @Test
    @DisplayName("Registration already exists throw error")
    void whenRegistrationAlreadyExistsThrowError(){

        Registration registration = createValidRegistration();

        when(registrationRepository.existsById(createValidRegistration().getId())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> registrationService.save(registration));

    }

    @Test
    @DisplayName("Should get an registration by id.")
    void FoundByIdAndReturnRegistration(){

        Registration registration = createValidRegistration();

        when(registrationRepository.getById(createValidRegistration().getId())).thenReturn(registration);

        Registration foundRegistration = registrationService.findByCpf(createValidRegistration().getId());

        assertNotNull(foundRegistration);

        verify(registrationRepository, times(1)).getById(createValidRegistration().getId());

    }

    @Test
    @DisplayName("Should return error when id doesn't exists.")
    void whenRegistrationNotFoundByIdThrowError(){

        when(registrationRepository.existsById(any())).thenReturn(false);

        assertThrows(ResponseStatusException.class,() -> registrationService.findByCpf(any()));

        verify(registrationRepository,never()).getById(any());

    }

    @Test
    @DisplayName("When null should return error when id doesn't exists.")
    void whenNullShouldReturnError(){

        when(registrationRepository.existsById(null)).thenReturn(false);

        assertThrows(ResponseStatusException.class,() -> registrationService.findByCpf(null));

        verify(registrationRepository,never()).getById(null);

    }

    @Test
    @DisplayName("Update a Registration Successful")
    void UpdateRegistrationWhenSuccessful(){

        Registration registrationToBeUpdate = createValidRegistration();

        when(registrationRepository.existsById(createValidRegistration().getId())).thenReturn(true);
        when(registrationRepository.save(registrationToBeUpdate)). thenReturn(createValidRegistration());

        Registration replaceRegistration = registrationService.update(registrationToBeUpdate);

        assertNotNull(replaceRegistration);
        assertNotNull(replaceRegistration.getId());
        assertNotNull(replaceRegistration.getCpf());
        assertEquals(replaceRegistration.getName(),registrationToBeUpdate.getName());
        assertEquals(replaceRegistration.getGroupName(),registrationToBeUpdate.getGroupName());

        verify(registrationRepository,times(1)).save(registrationToBeUpdate);

    }
    @Test
    @DisplayName("Update a registration doesn't not exist.")
    void whenUpdateRegistrationDoesNotExistThrowError(){

        Registration registration = null;

        when(registrationRepository.existsById(null)).thenReturn(false);

        assertThrows(ResponseStatusException.class, ()->registrationService.update(registration));

       verify(registrationRepository, never()).save(registration);

    }

    @Test
    @DisplayName("Delete a registration successful")
    void whenDeleteRegistrationSuccessful(){

        Registration registrationToBeDelete = createValidRegistration();

        when(registrationRepository.getById(registrationToBeDelete.getId())).thenReturn(registrationToBeDelete);

        assertDoesNotThrow(()-> registrationService.delete(registrationToBeDelete.getId()));

       verify(registrationRepository, times(1)).delete(registrationToBeDelete);

    }

    @Test
    @DisplayName("Should throw error when trying to delete a registration that doesn't exist.")
    void whenDeleteRegistrationNotFoundThrowError(){

        Registration registration = null;

        when(registrationRepository.existsById(null)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () ->registrationService.delete(null));

        verify(registrationRepository,never()).delete(registration);
    }


    public Registration createValidRegistration(){

        return Registration.builder()
                .id(1L)
                .name("Lola")
                .cpf(1234L)
                .groupName("001")
                .build();
    }
}
