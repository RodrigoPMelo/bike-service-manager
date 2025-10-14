package br.edu.infnet.rodrigomeloapi.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Address {

	@Size(max = 20) private String zipCode;
	@Size(max = 120) private String street;
	@Size(max = 20) private String number;
	@Size(max = 60) private String complement;
	@Size(max = 80) private String neighborhood;
	@Size(max = 80) private String city;
	@Size(max = 2)  private String state;
}
