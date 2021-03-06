package com.nathy.app.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt; 
import org.springframework.stereotype.Service;
 


import com.nathy.app.entity.AuthRequest;
import com.nathy.app.entity.AuthResponse;
import com.nathy.app.entity.User;
import com.nathy.app.entity.value_objects.UserVO;
import com.nathy.app.repository.UserRepository;

@Service
public class AuthService {
	
	Logger logger = LoggerFactory.getLogger(AuthService.class);

   // private final RestTemplate restTemplate;
	@Autowired
    private JwtUtil jwt;
    
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;

//    @Autowired
//    public AuthService(RestTemplate restTemplate,
//                       final JwtUtil jwt) {
//        this.restTemplate = restTemplate;
//        this.jwt = jwt;
//    }

    public AuthResponse register(AuthRequest authRequest) {
        //do validation if user already exists
    	authRequest.setPassword(BCrypt.hashpw(authRequest.getPassword(), BCrypt.gensalt()));
        logger.info("Before");
        //UserVO userVO = restTemplate.postForObject("http://user-service/users", authRequest, UserVO.class);
        logger.info("After Rest templaate");
        //Assert.notNull(userVO, "Failed to register user. Please try again later");
        User user = new User();
    	user.setEmail(authRequest.getEmail());
    	user.setFirstName(authRequest.getFirstName());
    	user.setLastName(authRequest.getLastName());
    	//user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
    	user.setPassword(authRequest.getPassword());
    	user.setUsername(authRequest.getUsername());
    	user.setRole(authRequest.getRole());
    	user = userRepository.save(user);
    	UserVO userVO = new UserVO();
    	userVO.setEmail(user.getEmail());
    	userVO.setUsername(user.getUsername()); 
    	userVO.setPassword(user.getPassword()); 
    	userVO.setRole(user.getRole());
        
        String accessToken = jwt.generate(userVO, "ACCESS");
        String refreshToken = jwt.generate(userVO, "REFRESH");

        return new AuthResponse(accessToken, refreshToken);

    }
}
