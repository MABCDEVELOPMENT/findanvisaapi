package com.anvisa.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anvisa.core.json.UrlToJson;
import com.anvisa.core.type.TypeArea;
import com.anvisa.core.type.TypeCategory;
import com.anvisa.core.type.TypeProduct;
import com.anvisa.core.type.TypeSearchProductCosmetic;
import com.anvisa.rest.RootObjectProcesso;
import com.anvisa.rest.RootObjectProduto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/anvisa")
public class FindAnvisaController {

	@ApiOperation(value = "View a list of process", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@RequestMapping(value = "/process/{cnpj}", method = RequestMethod.GET, produces = "application/json")
	public RootObjectProcesso findProcess(@PathVariable String cnpj,
			@RequestParam(name = "area", required = false) TypeArea area) {

		return UrlToJson.findProcess(cnpj, area);
	}

	@ApiOperation(value = "View a list of produtcts", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@RequestMapping(value = "/product/cosmetic/{cnpj}", method = RequestMethod.GET, produces = "application/json")
	public RootObjectProduto findCosmetic(@PathVariable String cnpj,
			@RequestParam(name = "Processo", required = false) String numeroProcesso,
			@RequestParam(name = "Produto", required = true) String nomeProduto,
			@RequestParam(name = "Marca", required = false) String marca,
			@RequestParam(name = "Categoria", required = false) TypeCategory categoria,
			@RequestParam(name = "Numero de Registro", required = false) String numeroRegistro,
			@RequestParam(name = "Tipo de Produto", required = true) TypeProduct typeProdutc,
			@RequestParam(name = "Tipo de Pesquisa", required = false) TypeSearchProductCosmetic typeSearchProductCosmetic) {

		return UrlToJson.find(cnpj, numeroProcesso, numeroRegistro, nomeProduto, categoria, marca,
				TypeProduct.COSMETIC.name(), typeSearchProductCosmetic.name());
	}

}
