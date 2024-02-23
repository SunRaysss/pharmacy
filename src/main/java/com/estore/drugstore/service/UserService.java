package com.estore.drugstore.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.drugstore.config.MessageStrings;
import com.estore.drugstore.dto.SignInDto;
import com.estore.drugstore.dto.SignInResponseDto;
import com.estore.drugstore.dto.SignUpResponseDto;
import com.estore.drugstore.dto.UserDto;
import com.estore.drugstore.exceptions.AuthenticationFailException;
import com.estore.drugstore.exceptions.CustomException;
import com.estore.drugstore.model.AuthenticationToken;
import com.estore.drugstore.model.User;
import com.estore.drugstore.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	public UserService(UserRepository userRepo) {
		this.userRepository= userRepo;
	}
	public List<UserDto> readAllUsers(){
		List<UserDto> usersDto = new ArrayList<UserDto>();
		List<User> users = userRepository.findAll();
		for (User u : users) 
			usersDto.add(new UserDto(u));
		return usersDto;
	}
	
	public SignUpResponseDto signUp(UserDto userDto) throws CustomException {
		if(Objects.nonNull(userRepository.findByEmail(userDto.getEmail())))
			 throw new CustomException("User already exists");
		
		
		String encryptedPassword = userDto.getPassword();
        try {
        	encryptedPassword = hashPassword(userDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        User user = new User(userDto.getfName(), userDto.getlName(), userDto.getEmail(), encryptedPassword);
        try {
             userRepository.save(user);
             final AuthenticationToken authenticationToken = new AuthenticationToken(user);
             authenticationService.saveConfirmationToken(authenticationToken);
             
            return new SignUpResponseDto("success", "user created successfully");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
	}
	
	 String hashPassword(String password) throws NoSuchAlgorithmException {
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        byte[] hash = md.digest(password.getBytes());
	        StringBuilder hexString = new StringBuilder();
	        for (byte b : hash) {
	          hexString.append(String.format("%02x", b));
	        }
	        return hexString.toString();
	    }
	 
	 public SignInResponseDto signIn(SignInDto signInDto) throws AuthenticationFailException, CustomException{
		 User user = userRepository.findByEmail(signInDto.getEmail());
		 if (!Objects.nonNull(user)) {
			 throw new AuthenticationFailException("user not present");
		 }
		 try {
	            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))){
	                throw  new AuthenticationFailException(MessageStrings.WRONG_PASSWORD);
	            }
	        } catch (NoSuchAlgorithmException e) {
	            throw new CustomException(e.getMessage());
	        }
		 
		 AuthenticationToken token = authenticationService.getToken(user);
		 if(!Objects.nonNull(token)) {
			 throw new CustomException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
		 }
	     return new SignInResponseDto ("success", token.getToken());
	 }
}
