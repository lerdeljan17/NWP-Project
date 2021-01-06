package com.edu.raf.NWP_Projekat.services.impl;

import com.edu.raf.NWP_Projekat.model.Reservation;
import com.edu.raf.NWP_Projekat.model.User;
import com.edu.raf.NWP_Projekat.model.modelDTO.ReservationDto;
import com.edu.raf.NWP_Projekat.model.modelDTO.UserDto;
import com.edu.raf.NWP_Projekat.repositories.UserRepository;
import com.edu.raf.NWP_Projekat.services.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean deleteUser(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if(optionalUser.isPresent()){
            this.userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public UserDto updateUser(Long id, User newUser) {
        User user = this.userRepository.findById(id).orElseThrow(()->new RuntimeException("User sa ID-jem " + id + " ne postoji!"));
        if(newUser.getUsername() != null && !newUser.getUsername().isEmpty()){
            user.setUsername(newUser.getUsername());
        }
        if(newUser.getPassword() != null && !newUser.getPassword().isEmpty()){
            user.setPassword(newUser.getPassword());
        }
        if(newUser.getUserType() != null && !newUser.getUserType().isEmpty()){
            user.setUserType(newUser.getUserType());
        }

        if(user != null){
            this.userRepository.save(user);
            return User.userToDto(user);
        }
        return null;
    }

    @Override
    public UserDto addUser(User user) {
        this.userRepository.save(user);
        return User.userToResponseDto(user);
    }

    @Override
    public List<User> searchUser(String name) {
        return null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(User.userToDto(user));
        }
        return userDtos;
    }

    @Override
    public UserDto getById(Long id) {
        User user = this.userRepository.findById(id).orElseThrow(()->new RuntimeException("User with id: " + id +" not found"));
        return User.userToDto(user);
    }

    @Override
    public UserDto getByUsernameAndPassword(String username, String password) {
        User user = this.userRepository.findByUsernameAndPassword(username, password).orElseThrow(()->new RuntimeException("Ne postoji username (" + username + ") ili password (" + password + ")!"));
        return User.userToResponseDto(user);
    }

    @Override
    public boolean addReservationToUser(Long userID, Long reservationID) {
        return false;
    }

    @Override
    public boolean removeReservationFromUser(Long userID, Long reservationID) {
        return false;
    }

    @Override
    public int getBookingCountByUsername(String username){
        return userRepository.getUserByUsernameEquals(username).get().getBookings().size();
    };
}
