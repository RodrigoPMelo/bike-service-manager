package br.edu.infnet.rodrigomeloapi.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @Size(max = 40)
    private String employeeNumber;

    @Size(max = 80)
    private String specialty;

    @PositiveOrZero
    private double salary;

    private boolean active;
}
