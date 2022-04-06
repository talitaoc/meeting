package br.com.bootcamp.meetup.controller;



import br.com.bootcamp.meetup.model.Registration;
import br.com.bootcamp.meetup.service.RegistrationService;
import br.com.bootcamp.meetup.util.RegistrationCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;


import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class RegistrationControllerTest {

    @InjectMocks
    private RegistrationController registrationController;

    @Mock
    RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        BDDMockito.when(registrationService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(RegistrationCreator.createNewRegistrationValid());
    }

    @Test
    @DisplayName("Find registration by id return successful")
    void findByIdReturnRegistrationSuccessful() {

        Long expectedId = RegistrationCreator.createNewRegistrationValid().getCpf();

        Registration registration = registrationController.findById(expectedId).getBody();

        Assertions.assertThat(registration.getId()).isNotNull().isEqualTo(expectedId);


    }
}

//    @Test
//    @DisplayName("Should create a registration with success")
//    void whenCreateRegistrationSuccess() throws Exception{
//
//        Registration registrationBuilder = RegistrationCreator.createNewRegistrationSaved();
//
//        Registration savedRegistration = Registration.builder().id(1L).name("Lola").date(LocalDate.now()).cpf(1234L).group("gatinho").build();
//
//        BDDMockito.given(registrationService.save(any(Registration.class))).willReturn(savedRegistration);
//
//        String json = new ObjectMapper().writeValueAsString(registrationBuilder);
//
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .post(REGISTRATION_API)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(json);
//
//        mockMvc
//                .perform(request)
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("name").value(registrationBuilder.getName()))
//                .andExpect(jsonPath("cpf").value(registrationBuilder.getCpf()))
//                .andExpect(jsonPath("group").value(registrationBuilder.getGroup()));
//
//    }
//
//    @Test
//    @DisplayName("Should throw an exception when not have date enough for the test")
//    void whenCreateInvalidRegistrationTest() throws Exception{
//
//        String json = new ObjectMapper().writeValueAsString(new RegistrationDTO());
//
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .post(REGISTRATION_API)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(json);
//
//        mockMvc.perform(request)
//                .andExpect(status().isBadRequest());
//
//    }
//    @Test
//    @DisplayName("Should get registration information")
//    void getRegistrationTest() throws Exception {
//        Long cpf = 1234L;
//
//        Registration registration = Registration.builder()
//                .name(createNewRegistration().getName())
//                .cpf(createNewRegistration().getCpf())
//                .group(createNewRegistration().getGroup())
//                .build();
//
//        BDDMockito.given(registrationService.getRegistrationByCpf(cpf)).willReturn(registration);
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
//                .get(REGISTRATION_API)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(json);
//    }
//}
