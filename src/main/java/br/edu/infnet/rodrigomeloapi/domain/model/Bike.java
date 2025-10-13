package br.edu.infnet.rodrigomeloapi.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "bikes")
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String model;

    @Column(length = 120)
    private String brand;

    @Column(name = "manufacture_year", nullable = false)
    private int manufactureYear;

    @Column(length = 60, unique = false)
    private String serialNumber;

    @Column(length = 40)
    private String type; // ex: "mountain", "road", "urban", etc.
}
