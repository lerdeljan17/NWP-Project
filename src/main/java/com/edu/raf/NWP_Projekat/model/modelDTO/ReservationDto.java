package com.edu.raf.NWP_Projekat.model.modelDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private Long id;
    private Boolean isAvailable;
    private Long flight;
    private Long user;
    private Long ticket;
}
