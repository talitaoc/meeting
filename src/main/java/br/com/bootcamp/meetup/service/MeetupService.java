package br.com.bootcamp.meetup.service;


import br.com.bootcamp.meetup.controller.dtos.MeetupFilterDTO;
import br.com.bootcamp.meetup.model.Meetup;
import br.com.bootcamp.meetup.model.Registration;
import br.com.bootcamp.meetup.repository.MeetupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Slf4j
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

        if(Objects.isNull(id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Meetup not found.");
        }
        return meetupRepository.findById(id);
    }

    public Page<Meetup> getRegistrationByMeetup(Registration registration, Pageable pageable){

        if(!meetupRepository.existsByRegistration(registration)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Registrations not found");
        }

        return meetupRepository.findByRegistration(registration, pageable);
    }

    public Page<Meetup> findAllMeetup(MeetupFilterDTO meetupFilterDTO, Pageable pageable){

        if(Objects.isNull(meetupFilterDTO)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Meetup not found.");
        }

        return meetupRepository.findByRegistrationOnMeetup(meetupFilterDTO.getRegistration(), meetupFilterDTO.getEvent(), pageable);
    }

    public Meetup update(Meetup update){

        if(Objects.isNull(update)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Meetup not found.");
        }

        return meetupRepository.save(update);
    }

    public Meetup findByEvent(String event) {

        if(Objects.isNull(event)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Meetup not found.");
        }

        return meetupRepository.getMeetupByEvent(event);
    }

    @Transactional
    public void delete(Meetup meetup){

        if(Objects.isNull(meetup)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Meetup not found.");
        }

        meetupRepository.delete(meetup);
    }
}
