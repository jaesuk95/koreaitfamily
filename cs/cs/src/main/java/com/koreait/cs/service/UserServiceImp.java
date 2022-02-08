package com.koreait.cs.service;

import com.koreait.cs.entities.User;
import com.koreait.cs.entities.userdetails.CustomUserDetails;
import com.koreait.cs.repository.UserRepositoryProfileSecure;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service("userDetailsService")
public class UserServiceImp implements UserDetailsService {

    @Autowired
    UserRepositoryProfileSecure userRepositoryProfileSecure;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

//        Optional<User> user = userRepositoryProfileSecure.findByEmail(email);
//        user.orElseThrow(() -> new UsernameNotFoundException("Can't find user " + email));
//        return user.map(CustomUserDetails::new).get();
        User user = userRepositoryProfileSecure.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Not Found account");
        }

        return new CustomUserDetails(user);
    }
}
