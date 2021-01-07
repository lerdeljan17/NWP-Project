package com.edu.raf.NWP_Projekat.services.impl;

import com.edu.raf.NWP_Projekat.model.User;
import com.edu.raf.NWP_Projekat.model.security.MyUserDetail;
import com.edu.raf.NWP_Projekat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetails")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;





    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("user not found"));
        return new MyUserDetail(user);


    }
}
