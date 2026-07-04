package com.Gurpal.Ecodhan.Controller;

import com.Gurpal.Ecodhan.Dto.AuthRequestDto;
import com.Gurpal.Ecodhan.Jwt.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    AuthenticationManager authenticationManager;
    JwtUtility jwtUtility;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,JwtUtility jwtUtility)
    {
        this.authenticationManager = authenticationManager;
        this.jwtUtility = jwtUtility;
    }

    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody AuthRequestDto authRequestDto) //return jwt token after authentication for first time
    {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDto.getUserName(),authRequestDto.getPassword())
            );
            String token = jwtUtility.createToken(authRequestDto.getUserName());
            return ResponseEntity.ok(token);
        }
        catch(Exception e)
        {
            throw e;
        }
    }
}
