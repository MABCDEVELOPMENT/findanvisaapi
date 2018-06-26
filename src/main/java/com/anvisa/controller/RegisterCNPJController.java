package com.anvisa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.anvisa.model.RegisterCNPJ;
import com.anvisa.model.User;
import com.anvisa.repository.generic.RegisterCNPJRepository;
import com.anvisa.repository.generic.UserRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@CrossOrigin
@RequestMapping("/cnpj")
@SwaggerDefinition(tags = { @Tag(name = "Register CNPJ", description = "Operations about user") })
public class RegisterCNPJController {

	@Autowired
	RegisterCNPJRepository registerRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	public void setService(RegisterCNPJRepository registerRepository, UserRepository userRepository) {
		this.registerRepository = registerRepository;
		this.userRepository = userRepository;

	}

	@ApiOperation(value = "View a list of available Register CNPJ", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	public List<RegisterCNPJ> list() {
		List<RegisterCNPJ> list = registerRepository.findAll(sort(true, "id"));
		return list;
	}

	@RequestMapping(value = "/listnotuser/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<RegisterCNPJ> listNotUser(@PathVariable Long id) {
		List<RegisterCNPJ> list = registerRepository.findAll(sort(true, "id"));
		List<RegisterCNPJ> returnList = new ArrayList<RegisterCNPJ>();
		Optional<User> user = userRepository.findById(id);
		List<RegisterCNPJ> userList = user.get().getRegisterCNPJs();
		for (RegisterCNPJ registerCNPJ : list) {
			if (!userList.contains(registerCNPJ)) {
				returnList.add(registerCNPJ);
			}
		}
		return returnList;
	}

	@ApiOperation(value = "Add or update a Register CNPJ")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<String> save(@RequestBody RegisterCNPJ registerCNPJ) {

		registerCNPJ.setCnpj(registerCNPJ.getCnpj().replace(".", "").replace("/", "").replace("-", ""));
		registerCNPJ = registerRepository.saveAndFlush(registerCNPJ);

		return new ResponseEntity<String>("CNPJ saved successfully", HttpStatus.OK);
	}

	@ApiOperation(value = "Add or update a Register CNPJ for user")
	@RequestMapping(value = "/savecnpjuser/{id}", method = RequestMethod.POST)
	public ResponseEntity<String> save(@PathVariable Long id, @RequestBody List<RegisterCNPJ> registers) {

		User user = userRepository.findId(id);

		user.getRegisterCNPJs().addAll(registers);

		user = userRepository.saveAndFlush(user);

		return new ResponseEntity<String>("User saved successfully", HttpStatus.OK);
	}

	@ApiOperation(value = "Delete a Register CNPJ")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable Long id) {
		registerRepository.deleteById(id);
		return new ResponseEntity<String>("User deleted successfully", HttpStatus.OK);
	}

	private Sort sort(boolean asc, String field) {
		return new Sort(asc ? Sort.Direction.ASC : Sort.Direction.DESC, field);
	}

}
