package com.anvisa.model.persistence.rest.cosmetic.register;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.rest.detalhe.comestico.registrado.detalhe.apresentacao.RegistradoApresentacaoFabricantesNacionais;

@Entity
@Table(name = "cosmetic_register_presente_dateil")
public class CosmeticRegisterPresentationDetail extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String nomeProduto;  
    private String processo;
    private String apresentacao;
    private String categoria;
    private ArrayList<PresentationCountryManufacturer> fabricantesNacionais;
    private String formaFisica;
    private String tonalidade;
    private String prazoValidade;
    private ArrayList<String> conservacao;
    private ArrayList<String> destinacao;
    private ArrayList<String> restricao;
	
}
