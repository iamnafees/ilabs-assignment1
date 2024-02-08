package com.ilabs.AuthService.service.impl;

import com.ilabs.AuthService.dto.TokenResponseDto;
import com.ilabs.AuthService.dto.UserDto;
import com.ilabs.AuthService.dto.UserLoginDto;
import com.ilabs.AuthService.entity.Role;
import com.ilabs.AuthService.entity.User;
import com.ilabs.AuthService.jwt.JwtUtil;
import com.ilabs.AuthService.jwt.UserDetail;
import com.ilabs.AuthService.repository.RoleRepository;
import com.ilabs.AuthService.repository.UserRepository;
import com.ilabs.AuthService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public ResponseEntity<?> signUp(UserDto userDto) {
        try {
            User user = userRepository.findByEmailId(userDto.getEmailId());
            if (user!=null) {
                return new ResponseEntity<>("Email already exists!", HttpStatus.BAD_REQUEST);

            }
// Validate role
        Role role = roleRepository.findByCustomId(userDto.getRoleId());
        if (Objects.isNull(role))
            return new ResponseEntity<>("Selected role not found!", HttpStatus.NOT_FOUND);

        // Restrict admin role
        if (role.getName().equals("ROLE_ADMIN"))
            return new ResponseEntity<>("You cannot select admin role!", HttpStatus.UNAUTHORIZED);

        userRepository.save(getUserFromDto(userDto));

        return new ResponseEntity<>("You have registered successfully!", HttpStatus.OK);
    }catch (Exception ex){
        ex.printStackTrace();

    }

    return new ResponseEntity<>("User signup failed!", HttpStatus.BAD_REQUEST);
}

    @Override
    public ResponseEntity<?> login(UserLoginDto userLoginDto) {try{

        System.out.println(userLoginDto.getEmailId() +" "+ userLoginDto.getPassword());
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLoginDto.getEmailId(),
                userLoginDto.getPassword()
        ));

        if (auth.isAuthenticated()) {// Avoid casting error on above line
            Object principal = auth.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetail userDetail = (UserDetail) principal;
                User authUser = userRepository.findByEmailId(userDetail.getUsername());
                return new ResponseEntity<TokenResponseDto>(new TokenResponseDto(jwtUtil.generateToken(authUser)), HttpStatus.OK);

            } else {
                System.out.println("Unexpected principal type: "+ principal.getClass());
            }
        }

    }
    catch (Exception e){
        e.printStackTrace();
    }//        throw new AuthenticationCredentialsNotFoundException("fhtfjy");
        return new ResponseEntity<>("Bad Credentials!",HttpStatus.BAD_REQUEST);
    }
    private User getUserFromDto(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmailId(userDto.getEmailId());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        //Role role = roleDao.findByName("ROLE_TRAINEE");
        user.addRole(new Role(userDto.getRoleId()));
        return user;
    }
}