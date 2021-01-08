package com.edu.raf.NWP_Projekat.services;

import com.edu.raf.NWP_Projekat.model.LoginResponse;
import com.edu.raf.NWP_Projekat.model.User;
import com.edu.raf.NWP_Projekat.model.modelDTO.ReservationDto;
import com.edu.raf.NWP_Projekat.model.modelDTO.ReservationsResponse;
import com.edu.raf.NWP_Projekat.model.modelDTO.UserDto;
import com.edu.raf.NWP_Projekat.model.security.LoginRequest;

import java.util.List;

public interface UserService {

    public boolean deleteUser(Long id);

    LoginResponse login(LoginRequest loginRequest) throws RuntimeException;

    public UserDto updateUser(Long id, User newUser);

    public UserDto addUser(User user) throws RuntimeException;

    public List<User> searchUser(String name);

    public List<UserDto> getAllUsers();

    public UserDto getById(Long id);

    public UserDto getByUsernameAndPassword(String username, String password);

    public boolean addReservationToUser(Long userID,Long reservationID);

    public boolean removeReservationFromUser(Long userID,Long reservationID);

    int getBookingCountByUsername(String username);

    List<ReservationsResponse> getUserBookings(String username);
}
