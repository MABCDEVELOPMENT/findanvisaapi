package com.anvisa.model.persistence.rest.cosmetic.register;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;

@Entity
@Table(name = "cosmetic_country_manufacturer")
public class PresentationCountryManufacturer extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String cnpj;
	private String razaoSocial;
	private String cidade;
	private String uf;
	private String pais;
	private String tipo;


}
