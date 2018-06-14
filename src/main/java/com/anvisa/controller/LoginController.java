package com.anvisa.controller;

import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.anvisa.controller.util.CustomErrorType;
import com.anvisa.model.Login;
import com.anvisa.model.ScheduledEmail;
import com.anvisa.model.User;
import com.anvisa.repository.generic.RepositoryScheduledEmail;
import com.anvisa.repository.generic.UserRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@CrossOrigin
@RequestMapping("/login")
@SwaggerDefinition(tags = { @Tag(name = "Login", description = "Operations about login") })
public class LoginController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RepositoryScheduledEmail scheduledEmail;

	@Autowired
	public void setService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@ApiOperation(value = "Login of user")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody Login login) {

		User user = userRepository.findLogin(login.getUserName());

		if (user == null) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("User invalid!"), HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}

	}

	@ApiOperation(value = "Get e-mail of user")
	@RequestMapping(value = "/getemail/{userName}", method = RequestMethod.GET)
	public ResponseEntity<?> getEmail(@PathVariable String userName) {

		User user = userRepository.findLogin(userName);

		if (user == null) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("User invalid!"), HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}

	}

	@ApiOperation(value = "Get e-mail of user")
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public ResponseEntity<?> forGotPassword(@RequestBody String email) {

		User user = userRepository.findEmail(email);

		if (user == null) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("User invalid!"), HttpStatus.CONFLICT);
		} else {

			ScheduledEmail scheduledEmail = new ScheduledEmail();

			scheduledEmail.setEmail(user.getEmail());
			scheduledEmail.setName(user.getFullName());
			scheduledEmail.setInsertUser(user);
			scheduledEmail.setInsertDate(GregorianCalendar.getInstance().getTime());
			scheduledEmail.setSubject("Re-definição de senha.");
			scheduledEmail.setBody("http://localhost:4200/redefine/" + user.getId());

			this.scheduledEmail.saveAndFlush(scheduledEmail);

			return new ResponseEntity<User>(user, HttpStatus.OK);
		}

	}

}
