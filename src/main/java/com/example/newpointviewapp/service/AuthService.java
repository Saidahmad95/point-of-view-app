package com.example.newpointviewapp.service;


import com.example.newpointviewapp.dto.AuthResponse;
import com.example.newpointviewapp.dto.LoginRequest;
import com.example.newpointviewapp.dto.RegisterRequest;
import com.example.newpointviewapp.exceptions.MyCustomException;
import com.example.newpointviewapp.model.NotificationEmail;
import com.example.newpointviewapp.model.User;
import com.example.newpointviewapp.model.VerificationToken;
import com.example.newpointviewapp.repository.UserRepository;
import com.example.newpointviewapp.repository.VerificationTokenRepository;
import com.example.newpointviewapp.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setCreated(Instant.now());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEnabled(false);
        User savedUser = userRepository.save(user);
        String token = generateVerificationToken(savedUser);
        mailService.sendEmail(new NotificationEmail(
                "Please activate your account",
                savedUser.getEmail(),
                "Thanks for joining to our platform, for activate your account, please click on the url below" +
                        "http://localhost:8080/api/auth/accountVerification/" + token
        ));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public ResponseEntity<String> verifyAccount(String token) {
        Optional<VerificationToken> byToken = verificationTokenRepository.findByToken(token);
        VerificationToken verificationToken =
                byToken.orElseThrow(() -> new MyCustomException(token + " is not exist in database !"));
        fetchUserAndEnable(verificationToken);
        return new ResponseEntity<>(
                "Dear " + verificationToken.getUser().getUsername() +
                        ", your account has been successfully activated !", HttpStatus.OK);
    }


    @Transactional
    void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).
                orElseThrow(() -> new MyCustomException("User with username: \"" + username + "\" not found !"));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtProvider.generateToken(authentication);
            return new AuthResponse(jwtToken, loginRequest.getUsername());
        }
        return new AuthResponse(null, loginRequest.getUsername());
    }
}
