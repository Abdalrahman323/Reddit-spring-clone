package com.springredditclone.redditclone.service;

import com.springredditclone.redditclone.dao.entities.NotificationEmail;
import com.springredditclone.redditclone.dao.entities.User;
import com.springredditclone.redditclone.dao.entities.VerificationToken;
import com.springredditclone.redditclone.dao.repository.UserRepository;
import com.springredditclone.redditclone.dao.repository.VerificationRepository;
import com.springredditclone.redditclone.dto.AuthenticationResponse;
import com.springredditclone.redditclone.dto.LoginRequest;
import com.springredditclone.redditclone.dto.RegisterRequest;
import com.springredditclone.redditclone.exceptions.SpringRedditException;
import com.springredditclone.redditclone.security.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    // using constructor injection instead of field injection
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationRepository verificationRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;

    @Transactional
    public void singUp (RegisterRequest registerRequest){
        User user = new User();
        // map the data
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword( passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreatedDate(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

       String token = generateVerficationToken(user);
       mailService.sendMail(new NotificationEmail(
               "Please Activate your account",
               user.getEmail(),
               "Thank you for signing up to spring reddit"+
               "please click on the below link to activate your account : "+
               "http://localhost:8080/api/auth/accountVerfication/" + token));
    }

    @Transactional
    public User getCurrentUser(){
        org.springframework.security.core.userdetails.User principle = (org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return  userRepository.findByUsername(principle.getUsername())
                .orElseThrow(()-> new UsernameNotFoundException("User name not found - "+ principle.getUsername()));
    }

    private  String generateVerficationToken(User user){
        String generatedToken = UUID.randomUUID().toString();
        VerificationToken verficationToken = new VerificationToken();
        verficationToken.setToken(generatedToken);
        verficationToken.setUser(user);

        verificationRepository.save(verficationToken);

        return generatedToken;

    }

    public void verifyAccount(String token) {

        Optional<VerificationToken> verficationToken = verificationRepository.findByToken(token);
        fetchUserAndEnable(verficationToken.orElseThrow(()-> new SpringRedditException("Invalid Token" )));
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        Long userId = verificationToken.getUser().getUserId();

        User user = userRepository.findById(userId).orElseThrow(() -> new SpringRedditException("User Not Found with name - "));
         user.setEnabled(true);
         userRepository.save(user);

    }

    public AuthenticationResponse login(LoginRequest loginRequest) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = tokenUtils.generateToken(authenticate);

        return new AuthenticationResponse(token,loginRequest.getUsername());

    }
}
