package com.nathy.app.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nathy.app.entity.AuthRequest;
import com.nathy.app.entity.AuthResponse;
import com.nathy.app.entity.LoginDto;
import com.nathy.app.entity.value_objects.UserVO;
import com.nathy.app.repository.UserRepository;
import com.nathy.app.service.AuthService;
import com.nathy.app.service.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
	Logger logger = LoggerFactory.getLogger(AuthController.class);

//    private final AuthService authService;
//
//    @Autowired
//    public AuthController(final AuthService authService) {
//        this.authService = authService;
//    }
	@Autowired
	private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtUtil jwtUtil;
  
    
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
    	
    	if(userRepository.existsByEmail(authRequest.getEmail())) {
    		return new ResponseEntity<>("Email is already taken", HttpStatus.BAD_REQUEST);
    	}
    	if(userRepository.existsByUsername(authRequest.getUsername())) {
    		return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
    	}  
      
    	return ResponseEntity.ok(authService.register(authRequest));
     
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserVO userVO){    	 
    	userVO.setPassword(BCrypt.hashpw(userVO.getPassword(), BCrypt.gensalt()));
    	logger.info("Kefat:   "+BCrypt.hashpw(userVO.getPassword(), BCrypt.gensalt()));
    	if(userRepository.existsByEmail(userVO.getEmail())&&
    			userRepository.existsByUsername(userVO.getUsername())) {
    		String accessToken =jwtUtil.generate(userVO, "ACCESS");
        	String refreshToken =jwtUtil.generate(userVO, "REFERESH");
        	return new ResponseEntity<>(new AuthResponse(accessToken, refreshToken), HttpStatus.OK);
    		
    	}  
//    	&&
//		userRepository.existsByPassword(BCrypt.hashpw(userVO.getPassword(), BCrypt.gensalt()))
    	if(!userRepository.existsByEmail(userVO.getEmail())){
    		return new ResponseEntity<>("No user by this email", HttpStatus.BAD_REQUEST);
    	}
    	if(!userRepository.existsByUsername(userVO.getUsername())){
    		return new ResponseEntity<>("No user by this user name", HttpStatus.BAD_REQUEST);
    	}
//    	if(!userRepository.existsByPassword(BCrypt.hashpw(userVO.getPassword(), BCrypt.gensalt()))){
//    		return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
//    	}
    	else {
    	
    		return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
    	}
    }

}
