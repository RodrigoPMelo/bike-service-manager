package br.edu.infnet.rodrigomeloapi.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@ToString(callSuper = true, exclude = {"bikes"})
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "clients")
public class Client extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 40)
    private String loyaltyCode;

    @Embedded
    private Address address;

    private LocalDateTime signupAt;
    
    @OneToMany(mappedBy = "owner")
    private List<Bike> bikes = new ArrayList<>();
}
