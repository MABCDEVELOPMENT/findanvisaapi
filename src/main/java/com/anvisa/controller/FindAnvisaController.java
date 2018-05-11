package com.anvisa.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.anvisa.core.json.RootObjectProcesso;
import com.anvisa.core.json.UrlToJson;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class FindAnvisaController {

	@ApiOperation(value = "View a list of available user", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@RequestMapping(value = "/process/{cnpj}", method = RequestMethod.GET, produces = "application/json")
	public RootObjectProcesso findProcess(@PathVariable String cnpj) {

		return UrlToJson.findProcess(cnpj);
	}

}
