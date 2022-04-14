package br.com.bootcamp.meetup.service;


import br.com.bootcamp.meetup.controller.dtos.MeetupFilterDTO;
import br.com.bootcamp.meetup.model.Meetup;
import br.com.bootcamp.meetup.model.Registration;
import br.com.bootcamp.meetup.repository.MeetupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeetupService {

    private final MeetupRepository meetupRepository;

    @Transactional
    public Meetup save(Meetup meetup){

        if(meetupRepository.existsByEvent(meetup.getEvent())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Meetup already registered");
        }
        return meetupRepository.save(meetup);
    }

    public Optional<Meetup> findMeetupById(Long id){
        return meetupRepository.findById(id);
    }

    public Page<Meetup> getRegistrationByMeetup(Registration registration, Pageable pageable){
        return meetupRepository.findByRegistration(registration, pageable);
    }

    public Page<Meetup> findAllMeetup(MeetupFilterDTO meetupFilterDTO, Pageable pageable){
        return meetupRepository.findByRegistrationOnMeetup(meetupFilterDTO.getRegistration(), meetupFilterDTO.getEvent(), pageable);
    }

    public Meetup update(Meetup update){
        return meetupRepository.save(update);
    }

    public Meetup findByEvent(String event) {
        return meetupRepository.getMeetupByEvent(event);
    }

    public void delete(Meetup meetup){
        meetupRepository.delete(meetup);
    }
}
