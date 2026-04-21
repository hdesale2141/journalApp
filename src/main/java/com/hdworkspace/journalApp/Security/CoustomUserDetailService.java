package com.hdworkspace.journalApp.Security;

import com.hdworkspace.journalApp.Controlller.AdminController;
import com.hdworkspace.journalApp.Entity.User;
import com.hdworkspace.journalApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminController adminController;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        adminController.setAuthUsername(username);
        if (user != null){
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_"+ user.getRole()))
            );
        }
        throw new UsernameNotFoundException("User not found with given: "+username);
    }
}
