package br.edu.infnet.rodrigomeloapi.domain.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@ToString(exclude = {"owner"})
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "bikes")
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    @NotBlank
    private String model;

    @Size(max = 120)
    private String brand;

    @Column(name = "manufacture_year", nullable = false)
    @Min(1900) @Max(2100)
    private int manufactureYear;

    @Size(max = 60)
    private String serialNumber;

    @Size(max = 40)
    private String type;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Client owner;
}
