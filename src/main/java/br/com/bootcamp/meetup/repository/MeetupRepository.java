package br.com.bootcamp.meetup.repository;

import br.com.bootcamp.meetup.model.Meetup;
import br.com.bootcamp.meetup.model.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MeetupRepository extends JpaRepository<Meetup, Long>{

    Page<Meetup> findByRegistration(Registration registration, Pageable pageable);
    boolean findRegistration(Registration registration,Pageable pageable);

    @Query( value = " select l from Meetup as l join l.registration as b where b.registration = :registration or l.event =:event ")
    Page<Meetup> findByRegistrationOnMeetup(@Param("registration") String registration,
                                            @Param("event") String event, Pageable pageable);


    Meetup getMeetupByEvent(String event);
    boolean existsByEvent(String event);
}
