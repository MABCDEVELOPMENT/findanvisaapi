package com.anvisa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.anvisa.model.persistence.ScheduledEmail;
import com.anvisa.model.persistence.User;
import com.anvisa.repository.generic.RepositoryScheduledEmail;
import com.anvisa.repository.generic.UserRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@CrossOrigin
@RequestMapping("/user")
@SwaggerDefinition(tags = { @Tag(name = "User", description = "Operations about user") })
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RepositoryScheduledEmail scheduledEmail;

	@Autowired
	public void setService(UserRepository userRepository, RepositoryScheduledEmail scheduledEmail) {
		this.userRepository = userRepository;
		this.scheduledEmail = scheduledEmail;
	}

	@ApiOperation(value = "View a list of available user", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	public List<User> list() {
		List<User> list = userRepository.findAll(sort(true, "id"));
		return list;
	}

	@ApiOperation(value = "Add or update a user")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<String> saveUser(@RequestBody User user) {

		boolean isSchedule = (user.getId() == null);
		if (!isSchedule) {
			user.setActive(true);
		}
		String emailUser = user.getUserName() + "@gmail.com";
		user.setEmail(emailUser);
		user = userRepository.saveAndFlush(user);

		if (isSchedule) {

			ScheduledEmail email = new ScheduledEmail();

			email.setEmail(user.getEmail());
			email.setName(user.getFullName());
			email.setInsertUser(user);
			email.setInsertDate(user.getInsertDate());
			email.setSubject("Ativação de conta");
			email.setBody("http://localhost:4200/activate/" + user.getId());

			this.scheduledEmail.saveAndFlush(email);

		}

		return new ResponseEntity<String>("User saved successfully", HttpStatus.OK);
	}

	@ApiOperation(value = "Update a user")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
		userRepository.save(user);
		return new ResponseEntity<String>("User updated successfully", HttpStatus.OK);
	}

	@ApiOperation(value = "Delete a user")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, consumes = "application/json")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		userRepository.deleteById(id);
		return new ResponseEntity<String>("User deleted successfully", HttpStatus.OK);
	}

	private Sort sort(boolean asc, String field) {
		return new Sort(asc ? Sort.Direction.ASC : Sort.Direction.DESC, field);
	}

}
