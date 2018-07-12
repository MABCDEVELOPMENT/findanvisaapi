package com.anvisa.controller;

import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.anvisa.controller.exception.LoginException;
import com.anvisa.controller.exception.bean.ErrorResponse;
import com.anvisa.controller.util.CustomErrorType;
import com.anvisa.interceptor.ScheduledTasks;
import com.anvisa.model.persistence.ScheduledEmail;
import com.anvisa.model.persistence.User;
import com.anvisa.repository.generic.RepositoryScheduledEmail;
import com.anvisa.repository.generic.UserRepository;
import com.anvisa.rest.model.Login;

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
	public ResponseEntity<?> login(@RequestBody Login login) throws LoginException {

		String pass = login.getPassword();

		User user = userRepository.findEmail(login.getEmail());

		if (user == null) {

			CustomErrorType erro = new CustomErrorType("Usuário não encontrado!");
			return new ResponseEntity<CustomErrorType>(erro,HttpStatus.BAD_REQUEST);
		} else {
			
			if (pass.equals(user.getPassword())) {
			
				return new ResponseEntity<User>(user, HttpStatus.OK);
			
			} else {
				
				CustomErrorType erro = new CustomErrorType("Login inválido!");
				return new ResponseEntity<CustomErrorType>(erro,HttpStatus.BAD_REQUEST);
			}

		}

	}

	@ApiOperation(value = "Login of user")
	@RequestMapping(value = "/loaduser/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> loadUser(@PathVariable Long id) {

		User user = userRepository.findId(id);

		if (user == null) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("User invalid!"), HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}

	}

	@ApiOperation(value = "Get e-mail of user")
	@RequestMapping(value = "/getemail/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getEmail(@PathVariable Long id) {

		User user = userRepository.findId(id);

		if (user == null) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Usuário inválido!"), HttpStatus.CONFLICT);
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
			scheduledEmail.setBody("http://localhost:4200/redefine?id=" + user.getId());

			this.scheduledEmail.saveAndFlush(scheduledEmail);
			
			ScheduledTasks.scheduledEmail();
			
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}

	}

	@ExceptionHandler(LoginException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
