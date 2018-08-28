package com.anvisa.controller;

import java.util.Iterator;
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

import com.anvisa.controller.util.CustomErrorType;
import com.anvisa.interceptor.ScheduledTasks;
import com.anvisa.model.persistence.RegisterCNPJ;
import com.anvisa.model.persistence.ScheduledEmail;
import com.anvisa.model.persistence.User;
import com.anvisa.model.persistence.UserRegisterCNPJ;
import com.anvisa.repository.generic.RegisterCNPJRepository;
import com.anvisa.repository.generic.RepositoryScheduledEmail;
import com.anvisa.repository.generic.UserRegisterCNPJRepository;
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
	UserRegisterCNPJRepository userRegisterCNPJRepository;
	
	@Autowired
	RegisterCNPJRepository registerCNPJRepository; 
	
	@Autowired
	public void setService(UserRepository userRepository, UserRegisterCNPJRepository userRegisterCNPJRepository, RepositoryScheduledEmail scheduledEmail, RegisterCNPJRepository registerCNPJRepository) {
		this.userRepository = userRepository;
		this.userRegisterCNPJRepository = userRegisterCNPJRepository;
		this.scheduledEmail = scheduledEmail;
		this.registerCNPJRepository = registerCNPJRepository;
	}

	@ApiOperation(value = "View a list of available user", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@RequestMapping(value = "/list/{checked}", method = RequestMethod.GET, produces = "application/json")
	public List<User> list(@PathVariable boolean checked) { 
		List<User> list = (checked?userRepository.findWaitingForApproval():userRepository.findAll(sort(true, "fullName")));
		return list;
	}

	@ApiOperation(value = "Add or update a user")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody User user) {
		
		boolean isSchedule = (user.getId() == null);
		user.setFullName(user.getFullName().toUpperCase());
		user.setUserName(user.getUserName().toUpperCase());
		String emailUserSend;
		
		User userFind = userRepository.findEmail(user.getEmail());
		
		if ((userFind!=null) && (user.getId() == null)) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Usuário já cadastrado."), HttpStatus.BAD_REQUEST);
		}
		
		if (isSchedule && (user.getProfile() == null || user.getProfile().intValue() == 2)) {
			emailUserSend = this.getUserSendAtivacion()+";fredalessandro@gmail.com";
		} else {
			emailUserSend = user.getUserName();
		}
		
		if (userFind!=null) {
			user.setRegisterCNPJs(userFind.getRegisterCNPJs()); 	
		}
		user = userRepository.saveAndFlush(user);

		if (isSchedule) {


			StringBuffer sb = new StringBuffer();
			
			sb.append("Prezado Administrado,\n");
			sb.append(" Foi efetuado um registro de usuário no sistema FINDANVISA, favor efetuar a ativação do mesmo confime dados abaixo:\n");
			sb.append("\n");
			sb.append("\n");
			sb.append(" Usuário : "+user.getFullName()+"\n");
			sb.append(" Login "+user.getEmail()+"\n");
			sb.append("\n");
			sb.append("\n");
			sb.append(" Acesse o link http://findinfo.kinghost.net/findanvisa/#/userList");
			
			
			List<User> users = this.userRepository.findSendActivation();
			
			for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
				
				User usermail = (User) iterator.next();
				
				ScheduledEmail email = new ScheduledEmail();

				email.setName(user.getFullName());
				email.setInsertUser(user);
				email.setInsertDate(user.getInsertDate());
				email.setSubject("Ativação de conta");
				email.setEmail(usermail.getEmail());
				email.setBody(sb.toString());
				
				this.scheduledEmail.saveAndFlush(email);

			}
			
			
			ScheduledTasks.scheduledEmail();

		}

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@ApiOperation(value = "Update a user")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
		userRepository.save(user);
		return new ResponseEntity<String>("User updated successfully", HttpStatus.OK);
	}

	@ApiOperation(value = "Delete a user")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable Long id) {
		userRepository.deleteById(id);
		return new ResponseEntity<String>("User deleted successfully", HttpStatus.OK);
	}

	@ApiOperation(value = "Delete a cnpj from user")
	@RequestMapping(value = "/deleteCnpjUser/", method = RequestMethod.POST)
	public ResponseEntity<User> deleteCnpjUser(@RequestBody Long[] object) {
		
		/*List<RegisterCNPJ> registerCNPJs = user.getRegisterCNPJs();
		List<RegisterCNPJ> registerCNPJsnew = new ArrayList<RegisterCNPJ>();
		for (Iterator<RegisterCNPJ> iterator = registerCNPJs.iterator(); iterator.hasNext();) {
			RegisterCNPJ registerCNPJ = (RegisterCNPJ) iterator.next();
			if (registerCNPJ.getId().longValue() != userRegisterCNPJ.getCnpj().getId().longValue()) {
				registerCNPJsnew.add(registerCNPJ);
			}
		}*/
		Long userId = (Long)object[0];
		User user = this.userRepository.findId(userId);
		
		Long registerId = (Long)object[1];
		RegisterCNPJ cnpj = this.registerCNPJRepository.findId(registerId);
		
		UserRegisterCNPJ userRegisterCNPJ = this.userRegisterCNPJRepository.findId(user, cnpj);
		if (userRegisterCNPJ!=null) {
			this.userRegisterCNPJRepository.delete(userRegisterCNPJ);
		}
		User userLocal = null; //saveCnpjUse(userRegisterCNPJ.getUser().getId(),registerCNPJsnew);
		

		return new ResponseEntity<User>(userLocal, HttpStatus.OK);
	}

	

	public User saveCnpjUse(Long id, List<RegisterCNPJ> registers) {
		
		
		User user = this.userRepository.findId(id);
		user.setRegisterCNPJs(null);
		userRepository.save(user);
		for (Iterator<RegisterCNPJ> iterator = registers.iterator(); iterator.hasNext();) {
			RegisterCNPJ registerCNPJ = (RegisterCNPJ) iterator.next();
			UserRegisterCNPJ userRegisterCNPJ = new UserRegisterCNPJ();
			userRegisterCNPJ.setUser(user);
			userRegisterCNPJ.setCnpj(registerCNPJ);
			userRegisterCNPJ.setSendNotification(registerCNPJ.isSendNotification());
			userRegisterCNPJ.setFoot(registerCNPJ.isFoot());
			userRegisterCNPJ.setCosmetic(registerCNPJ.isCosmetic());
			userRegisterCNPJ.setSaneante(registerCNPJ.isSaneante());
			userRegisterCNPJRepository.save(userRegisterCNPJ);	
		}

		user = this.userRepository.findId(id);
		
		return user;
		
	}
	private Sort sort(boolean asc, String field) {
		return new Sort(asc ? Sort.Direction.ASC : Sort.Direction.DESC, field);
	}
	
	private String getUserSendAtivacion() {
		String emails = "";
		List<User> users = this.userRepository.findSendActivation();
		int i = 0;
		for (Iterator iterator = users.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			emails = (i==0?"":";")+emails+user.getEmail();  
		}
		return emails;
	}

}
