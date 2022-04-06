package br.com.bootcamp.meetup.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "REGISTRATION_PERSON", schema = "registration_crud")
@Getter
@Setter
@ToString
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
    @CreatedDate
    private LocalDateTime createdDate;
    @Column
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    @Column(nullable = false, unique = false, length = 50)
    private String groupName;
}
