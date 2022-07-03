package com.bkap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bkap.dto.UserDto;
import com.bkap.entities.LoginRequest;
import com.bkap.entities.Token;
import com.bkap.filter.UserFilter;
import com.bkap.services.impl.UserServiceImpl;
import com.bkap.until.JWTUntil;
import com.bkap.until.MyUser;
import com.google.gson.Gson;

@RestController
@RequestMapping(value ="/api/user")
@CrossOrigin(origins="*",allowedHeaders="*") 
public class UserController {
	
	@Autowired
    AuthenticationManager authenticationManager;
	
	@Autowired
	JWTUntil jwtUntil;
	
	@Autowired
	UserServiceImpl service;
	
	@GetMapping(value = "/{id}")
	public UserDto getById(@PathVariable("id") int userId) {
		return service.findOne(userId);
	}
	
	@GetMapping
	public List<UserDto> getAll() {
		return service.getAll();
	}
	
	@GetMapping(value= "/paginations")
	public Page<UserDto> filter(@RequestParam("filter") String filter){
		UserFilter uf = new Gson().fromJson(filter,UserFilter.class);
		return service.where(uf);
	}
	
	@GetMapping(value = "/firstName")
	public ResponseEntity<UserDto> getByFirstName(@RequestParam("firstName") String firstName ) {		
		UserDto userDto = service.findByFirstName(firstName);
		if(userDto.getUserId() != 0) {
			return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
		}
		return new ResponseEntity<UserDto>(new UserDto(), HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/email")
	public UserDto getByEmail(@RequestParam("email") String email ) {
		return service.findByEmail(email);
	}
	
	@PostMapping
	public UserDto save(@RequestBody UserDto UserDto) {
		return service.save(UserDto);
	}
	
	@PutMapping(value = "/{id}")
	private ResponseEntity<UserDto> edit(@RequestBody UserDto userDto,@PathVariable("id") int userId) {
		userDto.setUserId(userId);
		if(service.findOne(userId).getUserId() != 0) {
			return new ResponseEntity<UserDto>(service.save(userDto), HttpStatus.OK);
		} else {
			return new ResponseEntity<UserDto>(new UserDto(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	private ResponseEntity<UserDto> detele(@PathVariable("id") int userId) {
		UserDto userDto = service.findOne(userId);
		if(userDto.getUserId() != 0) {
			service.remove(userId);
			return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
		}
		return new ResponseEntity<UserDto>(new UserDto(), HttpStatus.NOT_FOUND);
	}

	@PostMapping("/token")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(),
                        loginRequest.getPassword()
                )
        );

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        MyUser detail = (MyUser) authentication.getPrincipal();
        System.out.println(detail);
        // Trả về jwt cho người dùng.
        String jwt = jwtUntil.generateToken(detail);
        Token token = new Token();
        token.setCode(jwt);  
        return ResponseEntity.ok(token);
    }
}
