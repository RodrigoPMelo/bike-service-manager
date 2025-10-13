package br.edu.infnet.rodrigomeloapi.domain.model;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class Person {
    private String name;
    private String email;
    private String cpf;      
    private String phone;
}