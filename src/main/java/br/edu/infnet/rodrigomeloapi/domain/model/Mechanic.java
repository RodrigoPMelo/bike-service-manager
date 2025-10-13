package br.edu.infnet.rodrigomeloapi.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "mechanics")
public class Mechanic extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40)
    private String employeeNumber;

    @Column(length = 80)
    private String specialty;

    private double salary;

    private boolean active;
}
