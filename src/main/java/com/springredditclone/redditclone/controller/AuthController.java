package com.springredditclone.redditclone.controller;

import com.springredditclone.redditclone.dto.RegisterRequest;
import com.springredditclone.redditclone.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/signup")
    public  ResponseEntity<String> signUp( @RequestBody RegisterRequest registerRequest){

        authService.singUp(registerRequest);

        return new ResponseEntity<>("user Registration successfully", HttpStatus.OK);
    }

      @GetMapping("/accountVerfication/{token}")
        public ResponseEntity<String> verifyAccount(@PathVariable String token){
          authService.verifyAccount(token);

          return new ResponseEntity<>("Account Activated successfully",HttpStatus.OK);
        }

}
