package br.edu.infnet.rodrigomeloapi.domain.model;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Mechanic extends Person {
    private Long id;              
    private String employeeNumber;
    private String specialty;     
    private double salary;
    private boolean active;

    public Mechanic(String name, String email, String cpf, String phone) {
        super(name, email, cpf, phone);
    }
}
