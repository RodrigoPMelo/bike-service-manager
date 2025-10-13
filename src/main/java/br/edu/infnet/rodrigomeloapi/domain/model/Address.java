package br.edu.infnet.rodrigomeloapi.domain.model;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Address {
    private String zipCode;     
    private String street;
    private String complement;
    private String number;       
    private String neighborhood;
    private String city;
    private String state;        
}