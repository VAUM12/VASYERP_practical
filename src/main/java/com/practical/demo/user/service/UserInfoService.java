package com.practical.demo.user.service;

import com.practical.demo.user.dto.UserRegistrationRequest;
import com.practical.demo.user.model.UserInfo;
import com.practical.demo.user.repository.UserRepository;
import com.practical.demo.exception.ExceptionResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Autowired
    @Lazy
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = repository.findByEmail(username); // Assuming 'email' is used as username

        // Converting UserInfo to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new ExceptionResponse("User not found: " + username,HttpStatus.BAD_REQUEST));
    }

    public String addUser(UserRegistrationRequest userRegistrationRequest) {
        UserInfo userInfo= new UserInfo();
        BeanUtils.copyProperties(userRegistrationRequest,userInfo);
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userInfo.setRoles("ROLE_" + userRegistrationRequest.getRoles().name());

        repository.save(userInfo);
        return "User Added Successfully";
    }

    public UserInfo getUserByEmail(String username) {
        Optional<UserInfo> userDetail = repository.findByEmail(username); // Assuming 'email' is used as username
        return userDetail.get();
    }

    public UserInfo getUserById(Long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new ExceptionResponse("User not found", HttpStatus.BAD_REQUEST));
    }
}
