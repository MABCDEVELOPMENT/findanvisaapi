package com.anvisa.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.anvisa.core.json.UrlToJson;
import com.anvisa.rest.QueryRecordParameter;
import com.anvisa.rest.RootObject;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@CrossOrigin
@RequestMapping("/anvisa")
@SwaggerDefinition(tags = { @Tag(name = "Anvisa", description = "Operations about anvisa") })
public class FindAnvisaController {

	@ApiOperation(value = "View a list of process", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@RequestMapping(value = "/process", method = RequestMethod.POST, produces = "application/json")
	public RootObject findProcess(@RequestBody QueryRecordParameter queryRecordParameter) {
		RootObject rootObject = UrlToJson.findProcess(queryRecordParameter);
		return rootObject;
	}

	@ApiOperation(value = "View a list of produtcts", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@RequestMapping(value = "/product", method = RequestMethod.POST, produces = "application/json")
	public RootObject findProduct(@RequestBody QueryRecordParameter queryRecordParameter) {
		
		RootObject rootObject = UrlToJson.find(queryRecordParameter);
		
		return rootObject;
	}

}
