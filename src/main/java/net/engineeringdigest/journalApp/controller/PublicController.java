package net.engineeringdigest.journalApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.journalApp.Utils.JWTUtil;
import net.engineeringdigest.journalApp.dto.User;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.service.UserDetailsServiceImpl;
import net.engineeringdigest.journalApp.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/public")
@Tag(name = "Public APIs")
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UserService userService;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PublicController.class);


    @Operation(summary = "health-check")
    private String healthCheck() {
        return "OK";
    }

    @PostMapping("/signup")
    @Operation(summary = "Sign up first")
    public void signup(@RequestBody User user) {
        UserEntry newUser = new UserEntry();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setUserName(user.getUserName());
        newUser.setSentimentAnalysis(user.isSentimentAnalysis());
        userService.saveNewEntry(newUser);
    }

    @PostMapping("/login")
    @Operation(summary = "login using credentials")
    public ResponseEntity<String> login(@RequestBody UserEntry user) {
        try {
//            Authentication Manager internally checks the password from DB using methods like: loadUserByUserName and passwordEncoder
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch(Exception e) {
            logger.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}