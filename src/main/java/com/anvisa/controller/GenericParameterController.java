package com.anvisa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.anvisa.controller.util.CustomErrorType;
import com.anvisa.model.persistence.GenericParameter;
import com.anvisa.repository.generic.GenericParameterRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@CrossOrigin
@RequestMapping("/genericparameter")
@SwaggerDefinition(tags = { @Tag(name = "GenericParameter", description = "Operations about generic parameter") })
public class GenericParameterController {

	@Autowired
	GenericParameterRepository genericParameterRepository;

	@Autowired
	public void setService(GenericParameterRepository genericParameterRepository) {
		this.genericParameterRepository = genericParameterRepository;
	}

	@ApiOperation(value = "Load Parameter")
	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public ResponseEntity<?> loadParameter() {

		List<GenericParameter> genericParameters = genericParameterRepository.findAll();

		if (genericParameters.size() == 0) {
			//return new ResponseEntity<GenericParameter>(HttpStatus.OK);
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Parametro inexistente!"), HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<GenericParameter>(genericParameters.get(0), HttpStatus.OK);
		}

	}

	@ApiOperation(value = "Add or update a parameter")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<GenericParameter> save(@RequestBody GenericParameter genericParameter) {
		
		genericParameter.setCnpj(genericParameter.getCnpj().replace(".", "").replace("/", "").replace("-", ""));

		GenericParameter parameter = genericParameterRepository.saveAndFlush(genericParameter);

		return new ResponseEntity<GenericParameter>(parameter, HttpStatus.OK);
	}

}
