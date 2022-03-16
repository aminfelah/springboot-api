package com.example.controllers;



import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
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


import com.example.models.User;
import com.example.payloads.LoginRequest;
import com.example.payloads.LoginResponse;
import com.example.payloads.MessageResponse;
import com.example.payloads.SignupRequest;
import com.example.payloads.UpdateRequest;
import com.example.repositories.UserRepository;
import com.example.services.SendMailService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {


	@Autowired
	UserRepository userRepository;

	@Autowired
	SendMailService sendMailService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		if (!userRepository.existsByUsername(loginRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username not found !"));
		} else {
			User thetargetUser = userRepository.findByUsername(loginRequest.getUsername());
			if (!thetargetUser.isActivated()) {
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error: Account Not Activated!"));
			}
			if (thetargetUser.getPassword().equals(loginRequest.getPassword())) {
				LoginResponse loginResponse = new LoginResponse( thetargetUser.getUsername(), thetargetUser.getEmail(),thetargetUser.getAge() ,thetargetUser.getPhoneNumber());
				return ResponseEntity.ok(loginResponse);
			} else {
				return ResponseEntity
				.badRequest()
				.body(new MessageResponse("Error: wrong password !"));
			}
			
			
		}

		
	
		
	}
	  
	  @PostMapping("/signup")
		public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) throws UnsupportedEncodingException, MessagingException {
			if (userRepository.existsByUsername(signUpRequest.getUsername())) {
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error: Username is already taken!"));
			}

			if (userRepository.existsByEmail(signUpRequest.getEmail())) {
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error: Email is already in use!"));
			}

			// Create new user's account
			User user = new User(signUpRequest.getUsername(), 
								 signUpRequest.getEmail(),
								 signUpRequest.getPassword(),
								 signUpRequest.getAge(),
								 signUpRequest.getPhoneNumber());

			user.setActivated(false);
			user.setActivationToken((int)((Math.random() * (9999 - 1000)) + 9999));
			userRepository.save(user);
			String email = signUpRequest.getEmail();
			email = email.replace("%40","@");
	    	
	    	String activateAccount = "http://localhost:8080/api/auth"+ "/activate?token="+user.getActivationToken();
			
	    	sendMailService.sendEmail(email, activateAccount,"activate");
			return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
		}
	
	  
	  @GetMapping("/activate")
	     public String activateAccount(@RequestParam long token) {
	    
	    	User theTargetUser = userRepository.findFirstByActivationToken(token).orElse(null);
	    	if (theTargetUser==null) {
	    	return "User not found !";
	    	}else {
	    		theTargetUser.setActivated(true);
	    		userRepository.save(theTargetUser);
	    		return "User activated successfully!";
	    	}
	 }
	
		@GetMapping("/deactivate/{username}")
	     public String requestDeactivation(@PathVariable String username) throws UnsupportedEncodingException, MessagingException {
	    
			User thetargetUser = userRepository.findByUsername(username);
			if (thetargetUser==null) {
				return "user not found" ;
			}else {
				
				String deactivateAccount = "http://localhost:8080/api/auth"+ "/deactivate?token="+thetargetUser.getActivationToken();
				sendMailService.sendEmail(thetargetUser.getEmail(), deactivateAccount,"deactivate");
				return "check your mail for your account  to be deactivated";
			}
			
		}	
		@GetMapping("/deactivate")
	     public String deactivateAccount(@RequestParam long token)  {
			User theTargetUser = userRepository.findFirstByActivationToken(token).orElse(null);
	    	if (theTargetUser==null) {
	    	return "User not found !";
	    	}else {
	    		theTargetUser.setActivated(false);
	    		userRepository.save(theTargetUser);
	    		return "User deactivated successfully!";
	    	}
		}
		@GetMapping("/findall")
	     public List<User> findallUsers()  {
			return userRepository.findAll();
		}
		@GetMapping("/findone/{username}")
	     public User findOnebyUsername(@PathVariable String username)  {
			
			return userRepository.findByUsername(username);
		}
		@DeleteMapping("/deleteone/{username}")
	     public String deleteOnebyUsername(@PathVariable String username)  {
			if (userRepository.findByUsername(username)==null) {
				return "user not found";
			}else {
				userRepository.delete(userRepository.findByUsername(username));
				return "usesr deleted successfully";
			}
			
		}
		@PutMapping("/updateone/{username}")
	     public String updateOnebyUsername(@PathVariable String username,@RequestBody UpdateRequest updateRequest)  {
			if (userRepository.findByUsername(username)==null) {
				return "user not found";
			}else {
				User theTargetUser = userRepository.findByUsername(username);
				theTargetUser.setEmail(updateRequest.getEmail());
				theTargetUser.setAge(updateRequest.getAge());
				theTargetUser.setPhoneNumber(updateRequest.getPhoneNumber());

				userRepository.save(theTargetUser);
				return "user updated";
			}
			
		}
		
		
}