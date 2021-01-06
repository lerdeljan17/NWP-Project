package com.edu.raf.NWP_Projekat.model;

import com.edu.raf.NWP_Projekat.model.modelDTO.ReservationDto;
import com.edu.raf.NWP_Projekat.model.modelDTO.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    //@Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private String userType;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<Reservation> bookings;


    public static UserDto userToDto(User user){
        List<ReservationDto> reservationDtos = new ArrayList<>();
        if(user.getBookings() != null){
        for(Reservation r : user.getBookings()){
            reservationDtos.add(Reservation.reservationToDto(r));
            }
        }

        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .userType(user.getUserType())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();

        if(!reservationDtos.isEmpty()) userDto.setBookings(reservationDtos);
        return userDto;
    }

    public static UserDto userToResponseDto(User user){
        List<ReservationDto> reservationDtos = new ArrayList<>();
        if(user.getBookings() != null){
            for(Reservation r : user.getBookings()){
                reservationDtos.add(Reservation.reservationToDto(r));
            }
        }

        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .userType(user.getUserType())
                .username(user.getUsername())
                .build();

        if(!reservationDtos.isEmpty()) userDto.setBookings(reservationDtos);
        return userDto;
    }
}
