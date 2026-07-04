package com.Gurpal.Ecodhan.Controller;

import com.Gurpal.Ecodhan.Dto.UserRequestDto;
import com.Gurpal.Ecodhan.Dto.UserResponseDto;
import com.Gurpal.Ecodhan.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    UserService userService;
    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponseDto> getUser(Authentication authentication)
    {
        String userName = authentication.getName();
        UserResponseDto userResponseDto = userService.getUser(userName);
        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> saveUser(@RequestBody UserRequestDto userRequestDto)
    {
        String result = userService.saveUser(userRequestDto);
        return ResponseEntity.ok(result);

    }

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(Authentication authentication)
    {
        String userName = authentication.getName();
        String result = userService.deleteUser(userName);
        return ResponseEntity.ok(result);
    }


}
