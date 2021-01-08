package com.edu.raf.NWP_Projekat.controllers;

import com.edu.raf.NWP_Projekat.model.User;
import com.edu.raf.NWP_Projekat.model.modelDTO.UserDto;
import com.edu.raf.NWP_Projekat.model.security.LoginRequest;
import com.edu.raf.NWP_Projekat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<UserDto> getAllUsers(){
        return this.userService.getAllUsers();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id){
        if(this.userService.deleteUser(id)){
            return  ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody User newUser){
        UserDto user = this.userService.updateUser(id, newUser);
        if(user != null){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    public UserDto addUser(@RequestBody User user){
        List<String> types = new ArrayList<>();
        types.add("admin");types.add("user");
        if (!types.contains(user.getUserType())) {
            //TODO: 4.1.2021. error not a valid user type
            return null;
        }

        return this.userService.addUser(user);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id){
        UserDto userDto = this.userService.getById(id);
        if(userDto != null){
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.notFound().build();
    }

//    @GetMapping("/login")
//        public ResponseEntity<UserDto> getByUsernameAndPassword(@RequestParam(value = "username", required = true) String username,
//                                                            @RequestParam(value = "password", required = true) String password){
//        UserDto userDto = this.userService.getByUsernameAndPassword(username, password);
//        if(userDto != null){
//            return ResponseEntity.ok(userDto);
//        }
//
//        return ResponseEntity.notFound().build();
//
//    }

    @GetMapping("/userCountBooking")
    public int userCountBookings(@RequestParam(value = "username", required = true) String username){
       return this.userService.getBookingCountByUsername(username);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try{
            return ResponseEntity.ok(userService.login(loginRequest));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
