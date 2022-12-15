package com.blog.blogappapis.controller;

import com.blog.blogappapis.payloads.UserDTO;
import com.blog.blogappapis.security.CustomeUserDetailsService;
import com.blog.blogappapis.security.JwtAuthResponse;
import com.blog.blogappapis.security.JwtRequest;
import com.blog.blogappapis.security.JwtTokenHelper;
import com.blog.blogappapis.service.UserService;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class JWTController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomeUserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenHelper jwtHelper;
    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser( @RequestBody @Valid UserDTO userDTO){
        UserDTO registerUserDTO=this.userService.registerNewUser(userDTO);
        return new ResponseEntity<>(registerUserDTO, HttpStatus.CREATED);
    }
    @PostMapping("/generateToken")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest request) throws Exception {
        System.out.println(request);
        try{
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken( request.getUsername(),request.getPassword()));

        }
        catch (UsernameNotFoundException ex){
            ex.printStackTrace();
            throw new Exception("User not found");

        }
        catch (BadCredentialsException ex){
            ex.printStackTrace();
            throw new BadCredentialsException("Bad Credential");

        }
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtHelper.generateToken(userDetails);
        System.out.println("Token:"+token);
        return ResponseEntity.ok(new JwtAuthResponse(token));
    }

}
