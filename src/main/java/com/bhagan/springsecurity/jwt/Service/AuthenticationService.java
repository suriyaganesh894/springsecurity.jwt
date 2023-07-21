package com.bhagan.springsecurity.jwt.Service;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.bhagan.springsecurity.jwt.Repository.UserRepository;
import com.bhagan.springsecurity.jwt.controller.AuthenticationResponse;
import com.bhagan.springsecurity.jwt.controller.LoginRequest;
import com.bhagan.springsecurity.jwt.controller.RegisterRequest;
import com.bhagan.springsecurity.jwt.entity.Role;
import com.bhagan.springsecurity.jwt.entity.User;
import com.bhagan.springsecurity.jwt.jwtauth.jwtService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepo;
    private final jwtService jwtServ;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                    .firstName(request.getFirstName())
                    .LastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(Role.User).build();
                    userRepo.save(user);
                String jwtToken = jwtServ.generateToken(new HashMap<>(),user);
        return AuthenticationResponse
        .builder()
        .token(jwtToken).build();
    }

    public AuthenticationResponse login(LoginRequest request) throws Exception {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        ));

        Optional<User> user = userRepo.findByEmail(request.getEmail());
        if(user.isEmpty()){
            throw new Exception("User not found");
        }
        String jwtToken = jwtServ.generateToken(new HashMap<>(),user.get());
        return AuthenticationResponse
        .builder()
        .token(jwtToken).build();
    }

}
