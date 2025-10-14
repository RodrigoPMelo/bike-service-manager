package br.edu.infnet.rodrigomeloapi.domain.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class Person {

	@NotBlank
	@Size(max = 120)
	private String name;

	@Email
	@Size(max = 180)
	private String email;

	@Pattern(regexp = "\\d{11}", message = "cpf must have 11 digits")
	private String cpf;

	@Size(max = 20)
	private String phone;
}
