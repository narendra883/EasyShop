//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.backend.controller;

import com.backend.Config.JWTUtils;
import com.backend.dto.UserDto;
import com.backend.services.CartService;
import com.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/users"})
@CrossOrigin(
        origins = {"http://localhost:5173"}
)
public class UserController {
    @Autowired
    private UserService usersManagementService;
    @Autowired
    private JWTUtils jwtUtils;



    public UserController() {
    }

    @PostMapping({"/register"})
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto registrationRequest) {
        UserDto response = this.usersManagementService.register(registrationRequest);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @PostMapping({"/login"})
    public ResponseEntity<UserDto> login(@RequestBody UserDto loginRequest) {
        UserDto response = this.usersManagementService.login(loginRequest);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {


        // Extract the email or username from the principal (UserDetails)
        String email = userDetails.getUsername();

        // Fetch user information based on the extracted email
        UserDto userDto = usersManagementService.getMyInfo(email);


        return ResponseEntity.ok(userDto); // Return the user info if everything is fine
    }



}
