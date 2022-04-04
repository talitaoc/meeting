package br.com.bootcamp.meetup.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "REGISTRATION_PERSON", schema = "registration_crud")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REGISTRATION_PERSON_SEQ")
    @SequenceGenerator(name = "REGISTRATION_PERSON_SEQ", sequenceName = "registration_crud.REGISTRATION_PERSON_SEQ")
    private Long id;
    @Column(nullable = false, unique = false, length = 50)
    private String name;
    @Column(nullable = false, unique = true, length = 11)
    private Long cpf;
    @Column
    private LocalDate date;
    @Column(nullable = false, unique = false, length = 50)
    private String group;
}
