package com.edu.raf.NWP_Projekat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

//    private Long id;
    private String username;
    private String userType;
    private String jwt;
}
