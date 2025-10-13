package br.edu.infnet.rodrigomeloapi.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@Getter @Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class Person {

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 180)
    private String email;

    @Column(length = 14)
    private String cpf;

    @Column(length = 20)
    private String phone;
}
