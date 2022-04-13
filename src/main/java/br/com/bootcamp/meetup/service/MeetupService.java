//package br.com.bootcamp.meetup.service;
//
//import br.com.bootcamp.meetup.controller.dtos.MeetupDTO;
//import br.com.bootcamp.meetup.controller.dtos.MeetupFilterDTO;
//import br.com.bootcamp.meetup.model.Meetup;
//import br.com.bootcamp.meetup.model.Registration;
//import br.com.bootcamp.meetup.repository.MeetupRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class MeetupService {
//
//    private final MeetupRepository meetupRepository;
//
//    @Transactional
//    public Meetup save(Meetup meetup){
//        return meetupRepository.save(meetup);
//    }
//
//    public Optional<Meetup> getById(Long id){
//        return meetupRepository.findById(id);
//    }
//
//    public Page<Meetup> getRegistrationByMeetup(Registration registration, Pageable pageable){
//        return meetupRepository.findByRegistration(registration, pageable);
//    }
//
//    public Page<Meetup> find(MeetupFilterDTO meetupFilterDTO, Pageable pageable){
//        return meetupRepository.findByRegistrationOnMeetup(meetupFilterDTO.getRegistration(), meetupFilterDTO.getEvent(), pageable);
//    }
//
//    public Meetup update(Meetup update){
//        return meetupRepository.save(update);
//    }
//
//    public Meetup findByEvent(String event) {
//        return meetupRepository.getMeetupByEvent(event);
//    }
//
//    public void delete(Meetup meetup){
//        meetupRepository.delete(meetup);
//    }
//}
