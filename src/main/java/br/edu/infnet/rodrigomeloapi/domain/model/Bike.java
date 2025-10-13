package br.edu.infnet.rodrigomeloapi.domain.model;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Bike {
    private Long id;            // generated in the in-memory adapter
    private String model;
    private String brand;
    private int year;
    private String serialNumber;
    private String type;        // e.g., "mountain", "road", "urban"
}
