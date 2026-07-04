package com.Gurpal.Ecodhan.Service;

import com.Gurpal.Ecodhan.Dto.UserRequestDto;
import com.Gurpal.Ecodhan.Dto.UserResponseDto;
import com.Gurpal.Ecodhan.Entity.Company;
import com.Gurpal.Ecodhan.Entity.User;
import com.Gurpal.Ecodhan.Enum.Role;
import com.Gurpal.Ecodhan.Exception.NotExistException;
import com.Gurpal.Ecodhan.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String saveUser(UserRequestDto userRequestDto)
    {
        String userName = userRequestDto.getUserName();
        Optional<User> u = userRepository.findByUserName(userName);
        if(u.isPresent())
        {
            return "User Already Exists";
        }
        else
        {
            User user = new User();
            user.setUserName(userRequestDto.getUserName());
            user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
            user.setRole(Role.USER);
            user.setEmail(userRequestDto.getEmail());
            userRepository.save(user);
            return "Singup Successfully";
        }
    }

    public String deleteUser(String userName)
    {
        userRepository.delete(userRepository.findByUserName(userName).get());
        return "User Get Deleted Successfully";
    }


    public UserResponseDto getUser(String username)
    {

        User user = userRepository.findByUserName(username).orElseThrow(()-> new NotExistException("User Not Exists"));
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUserName(user.getUsername());
        userResponseDto.setUserId(user.getUserId());
        userResponseDto.setEmail(user.getEmail());
        if(user.getCompany()!=null)
        {
            userResponseDto.setCompanyName(user.getCompany().getCompanyName());
        }
        return userResponseDto;

    }
}
