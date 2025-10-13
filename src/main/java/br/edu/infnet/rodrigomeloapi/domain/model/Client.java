package br.edu.infnet.rodrigomeloapi.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Client extends Person {
    private Long id;                
    private String loyaltyCode;
    private Address address;         // association 1-1
    private LocalDateTime signupAt; 

    public Client(String name, String email, String cpf, String phone) {
        super(name, email, cpf, phone);
    }
}
