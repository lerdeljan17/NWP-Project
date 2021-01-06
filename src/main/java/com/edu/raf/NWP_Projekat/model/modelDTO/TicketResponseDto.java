package com.edu.raf.NWP_Projekat.model.modelDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDto {

    private Long id;
    private Boolean oneWay;
    private String departDate;
    private String returnDate;
    private Long count;
    private String company;
    private Long flight;
    private String origin;
    private String destination;
}
