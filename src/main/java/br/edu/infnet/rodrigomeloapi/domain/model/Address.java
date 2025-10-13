package br.edu.infnet.rodrigomeloapi.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter @Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Address {

    @Column(length = 20)
    private String zipCode;

    @Column(length = 120)
    private String street;

    @Column(length = 20)
    private String number;

    @Column(length = 60)
    private String complement;

    @Column(length = 80)
    private String neighborhood;

    @Column(length = 80)
    private String city;

    @Column(length = 2)
    private String state;
}
