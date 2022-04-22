package br.com.bootcamp.meetup.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Meetup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String event;

    @JoinColumn(name = "registration_id")
    @ManyToOne
    private Registration registration;

    @Column
    private LocalDateTime meetupDate;

    @Column
    private Boolean registered;
}
