package com.estore.drugstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.drugstore.ApiResponse;
import com.estore.drugstore.dto.ProductDto;
import com.estore.drugstore.dto.SignInDto;
import com.estore.drugstore.dto.SignInResponseDto;
import com.estore.drugstore.dto.SignUpResponseDto;
import com.estore.drugstore.dto.UserDto;
import com.estore.drugstore.exceptions.AuthenticationFailException;
import com.estore.drugstore.exceptions.CustomException;
import com.estore.drugstore.model.User;
import com.estore.drugstore.service.AuthenticationService;
import com.estore.drugstore.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	
	public UserController(UserService userServ) {
		this.userService=userServ;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = userService.readAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
	
	@PostMapping("/signUp")
	public SignUpResponseDto userSignUp(@RequestBody UserDto userDto) throws CustomException{
		return userService.signUp(userDto);
	}
	
    @PostMapping("/signIn")
    public SignInResponseDto userSignIn(@RequestBody SignInDto signInDto) throws AuthenticationFailException, CustomException {
        return userService.signIn(signInDto);
    }
}
