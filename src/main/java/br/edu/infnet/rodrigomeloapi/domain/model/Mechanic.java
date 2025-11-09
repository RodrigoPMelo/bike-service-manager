package br.edu.infnet.rodrigomeloapi.domain.model;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"serviceOrders"})
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "mechanics")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
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
    
    @OneToMany(mappedBy = "mechanic") 
    @JsonIgnore // Avoids heavy serialization
    private List<ServiceOrder> serviceOrders = new ArrayList<>();
}
