package br.com.bootcamp.meetup.repository;

import br.com.bootcamp.meetup.model.Meetup;

import br.com.bootcamp.meetup.model.Registration;
import br.com.bootcamp.meetup.util.MeetupCreator;


import br.com.bootcamp.meetup.util.RegistrationCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
@DataJpaTest
public class MeetupRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    MeetupRepository meetupRepository;

    @Test
    @DisplayName("Should return true when meetup is created")
    void returnTrueWhenMeetupIsCreated(){

        String event = "World Domination";

        Meetup meetupEvent = MeetupCreator.createNewMeetup(event);
        entityManager.persist(meetupEvent);

        boolean exists = meetupRepository.existsByEvent(event);

        assertTrue(exists);

    }

    @Test
    @DisplayName("Should return false when meetup does not exist")
    void returnFalseWhenMeetupDoesNotExist(){

        String event = "World Domination";

        boolean exists = meetupRepository.existsByEvent(event);

        assertFalse(exists);

    }

    @Test
    @DisplayName("Should get a page of meetup by registration")
    void findPageOfMeetupsByRegistrationTest(){

        PageRequest pageRequest = PageRequest.of(0,10);

        Registration registration = RegistrationCreator.createRegistration();

        entityManager.persist(registration);

        Meetup meetup = MeetupCreator.createMeetup(registration);

        entityManager.persist(meetup);

        Page<Meetup> foundMeetups = meetupRepository.findByRegistration(registration,pageRequest);

        assertNotNull(foundMeetups);
        assertFalse(foundMeetups.isEmpty());

    }

    @Test
    @DisplayName("Should get a meetup by event successful")
    void getMeetupByEventTest(){

        Meetup meetup = MeetupCreator.createNewMeetup("World Domination");
        entityManager.persist(meetup);

        Meetup getMeetup = meetupRepository.getMeetupByEvent(meetup.getEvent());

        assertNotNull(getMeetup);
    }

    @Test
    @DisplayName("Should save a meetup in registration")
    void saveMeetupTest(){

        Meetup meetup = MeetupCreator.createMeetup(RegistrationCreator.createRegistration());
        Meetup savedMeetup = meetupRepository.save(meetup);

        assertNotNull(savedMeetup.getId());

    }

    @Test
    @DisplayName("Should delete Meetup from the base")
    void deleteMeetupTest(){

        Meetup meetup = MeetupCreator.createMeetup(RegistrationCreator.createRegistration());
        entityManager.persist(meetup);

        Meetup foundMeetup = entityManager.find(Meetup.class, meetup.getId());
        meetupRepository.delete(foundMeetup);

        Meetup deleteMeetup = entityManager.find(Meetup.class, meetup.getId());

        assertNull(deleteMeetup);

    }

}
