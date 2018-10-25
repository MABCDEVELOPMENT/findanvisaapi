package com.anvisa.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.anvisa.controller.find.FindData;
import com.anvisa.core.json.UrlToJson;
import com.anvisa.rest.QueryRecordDetail;
import com.anvisa.rest.QueryRecordParameter;
import com.anvisa.rest.QueryRecordProcessParameter;
import com.anvisa.rest.RootObject;
import com.anvisa.rest.model.Area;

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
	public RootObject findProcess(@RequestBody QueryRecordProcessParameter queryRecordProcessParameter) {
		RootObject rootObject = FindData.findProcess(queryRecordProcessParameter);//UrlToJson.findProcess(queryRecordProcessParameter);
		return rootObject;
	}
	
	@RequestMapping(value = "/processDetail", method = RequestMethod.POST, produces = "application/json")
	public RootObject findProcess(@RequestBody QueryRecordDetail queryRecordDetail) {
		RootObject rootObject = UrlToJson.findProcessDetail(queryRecordDetail.getValue());
		return rootObject;
	}


	@ApiOperation(value = "View a list of produtcts", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@RequestMapping(value = "/product", method = RequestMethod.POST, produces = "application/json")
	public RootObject findProduct(@RequestBody QueryRecordParameter queryRecordParameter) {

//		if (queryRecordParameter.getCategory()!=0 && queryRecordParameter.getCategory()!=1 && ( queryRecordParameter.getOption()!=0 && queryRecordParameter.getCategory()==0)) {
//		    RootObject rootObject = UrlToJson.find(queryRecordParameter);
//		    return rootObject;
//		} else {
//		    return FindData.find(queryRecordParameter);
//		} 
		 return FindData.find(queryRecordParameter);
	}
	
	@ApiOperation(value = "View a detail of produtc")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/productDetial", method = RequestMethod.POST, produces = "application/json")
	public RootObject findProductDetail(@RequestBody QueryRecordDetail queryRecordDetail) {
		
		RootObject rootObject = UrlToJson.findDetail(queryRecordDetail.getCategory(), queryRecordDetail.getOption(),
				queryRecordDetail.getValue());
		
		return rootObject;
	}
	
	@ApiOperation(value = "Viewa of cosmetic detail")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/findProductCosmetcDetail", method = RequestMethod.POST, produces = "application/json")
	public RootObject findProductCosmetcDetail(@RequestBody QueryRecordDetail queryRecordDetail) {
		
		RootObject rootObject = UrlToJson.findDetailCosmetic(queryRecordDetail.getProcess(), queryRecordDetail.getValue(), queryRecordDetail.getOption());
		
		return rootObject;
	}
	
	private ResourceLoader resourceLoader = new DefaultResourceLoader();
	
	@ApiOperation(value = "View a label of produtc")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/productLabel/{id}", method = RequestMethod.GET, produces = "image/jpg")
	public ResponseEntity<byte[]> findProductLabel(@PathVariable String id) throws IOException {
		
		    ClassPathResource imgFile = new ClassPathResource("images/rotulo_" + id + ".jpg");
		    byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

	        return ResponseEntity
	                .ok()
	                .contentType(MediaType.IMAGE_JPEG)
	                .body(bytes);
	}
	

	@ApiOperation(value = "View a list of TypeArea", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@RequestMapping(value = "/areas", method = RequestMethod.GET, produces = "application/json")
	public List<Area> getAllArea() {
		List<Area> areas = new ArrayList<Area>();
		
		areas.add(new Area("Alimentos", 6)); 
		//areas.add(new Area("Autorizações", 7)); 
		areas.add(new Area("Cosmeticos", 2)); 
		//areas.add(new Area("Derivado de Tabaco", 10)); 
		//areas.add(new Area("Medicamentos", 1)); 
		//areas.add(new Area("Portos,Aeroportos e Fronteitas", 11)); 
		//areas.add(new Area("Produtos para saúde(Correlatos)",8)); 
		//areas.add(new Area("Produtos para diognóstico para uso in vitro",5)); 
		areas.add(new Area("Saneantes", 12)); 
		//areas.add(new Area("Toxicologia", 9));
		
		return areas;
	}

	

}
