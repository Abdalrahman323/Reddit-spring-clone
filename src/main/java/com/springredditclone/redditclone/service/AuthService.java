package com.springredditclone.redditclone.service;

import com.springredditclone.redditclone.dao.entities.NotificationEmail;
import com.springredditclone.redditclone.dao.entities.User;
import com.springredditclone.redditclone.dao.entities.VerificationToken;
import com.springredditclone.redditclone.dao.repository.UserRepository;
import com.springredditclone.redditclone.dao.repository.VerificationRepository;
import com.springredditclone.redditclone.dto.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    // using constructor injection instead of field injection
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationRepository verificationRepository;
    private final MailService mailService;

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


    private  String generateVerficationToken(User user){
        String generatedToken = UUID.randomUUID().toString();
        VerificationToken verficationToken = new VerificationToken();
        verficationToken.setToken(generatedToken);
        verficationToken.setUser(user);

        verificationRepository.save(verficationToken);

        return generatedToken;

    }

}
