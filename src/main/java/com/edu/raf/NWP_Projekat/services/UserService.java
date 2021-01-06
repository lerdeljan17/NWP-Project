package com.edu.raf.NWP_Projekat.services;

import com.edu.raf.NWP_Projekat.model.User;
import com.edu.raf.NWP_Projekat.model.modelDTO.UserDto;

import java.util.List;

public interface UserService {

    public boolean deleteUser(Long id);

    public UserDto updateUser(Long id, User newUser);

    public UserDto addUser(User user);

    public List<User> searchUser(String name);

    public List<UserDto> getAllUsers();

    public UserDto getById(Long id);

    public UserDto getByUsernameAndPassword(String username, String password);

    public boolean addReservationToUser(Long userID,Long reservationID);

    public boolean removeReservationFromUser(Long userID,Long reservationID);

    int getBookingCountByUsername(String username);
}
