package com.HealthJobsNow.Job.Application.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HealthJobsNow.Job.Application.Dto.LoginRequest;
import com.HealthJobsNow.Job.Application.Dto.LoginResponse;
import com.HealthJobsNow.Job.Application.Dto.MessageResponse;
import com.HealthJobsNow.Job.Application.Dto.SignUpRequest;
import com.HealthJobsNow.Job.Application.Model.User;
import com.HealthJobsNow.Job.Application.Model.UserRole;
import com.HealthJobsNow.Job.Application.Repository.UserRepository;
import com.HealthJobsNow.Job.Application.Security.Jwt.JwtUtils;
import com.HealthJobsNow.Job.Application.Security.Services.UserDetailsImpl;
import com.HealthJobsNow.Job.Application.Service.UserService;
import com.HealthJobsNow.Job.Application.Utils.AuthUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/public/auth")
public class AuthController {
	   @Autowired
	    JwtUtils jwtUtils;

	    @Autowired
	    AuthenticationManager authenticationManager;

	    @Autowired
	    UserRepository userRepository;

	    @Autowired
	    PasswordEncoder encoder;

	    @Autowired
	    UserService userService;

	    @Autowired
	    AuthUtil authUtil;
	    
	    
	    @PostMapping("/signin")
	    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest,HttpServletRequest request){
	        Authentication authentication;
	        try{
	            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));

	        }catch(AuthenticationException exception){
	            Map<String,Object> map = new HashMap<>();
	            map.put("message","Bad Credentials");
	            map.put("status",false);
	            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);


	        }
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

	        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

	        List<String> roles = userDetails.getAuthorities().stream().map(item->item.getAuthority()).collect(Collectors.toList());

	        LoginResponse response = new LoginResponse(userDetails.getUsername(),roles,jwtToken);
	        
	        return ResponseEntity.ok(response);
	    }
	    
	    @PostMapping("/register/user")
	    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest){
	    	
	    	if(userRepository.existsByEmail(signUpRequest.getEmail())) {
	    		return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
	    	}
	    	 User user = User.builder()
	    	            .name(signUpRequest.getName())
	    	            .email(signUpRequest.getEmail())
	    	            .phone(signUpRequest.getPhone())
	    	            .password(encoder.encode(signUpRequest.getPassword()))
	    	            .role(UserRole.JOB_SEEKER)
	    	            .build();
	    	userService.createUser(user);
	    	 return ResponseEntity
	    	            .status(HttpStatus.CREATED)
	    	            .body(new MessageResponse("Job seeker registered successfully!"));
	    	
	    }
	    
	    @PostMapping("/register/employer")
	    public ResponseEntity<?> registerEmployer(@Valid @RequestBody SignUpRequest signUpRequest){
	    	
	    	if(userRepository.existsByEmail(signUpRequest.getEmail())) {
	    		return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
	    	}
	    	User user = User.builder()
    	            .name(signUpRequest.getName())
    	            .email(signUpRequest.getEmail())
    	            .phone(signUpRequest.getPhone())
    	            .password(signUpRequest.getPassword())
    	            .role(UserRole.EMPLOYER)
    	            .build();
	    	userService.createUser(user);
	    	 return ResponseEntity
	    	            .status(HttpStatus.CREATED)
	    	            .body(new MessageResponse("Employer registered successfully!"));
	   }
}
