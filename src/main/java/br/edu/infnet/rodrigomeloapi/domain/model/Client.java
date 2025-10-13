package br.edu.infnet.rodrigomeloapi.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "clients")
public class Client extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40)
    private String loyaltyCode;

    @Embedded
    private Address address;

    private LocalDateTime signupAt;
}
