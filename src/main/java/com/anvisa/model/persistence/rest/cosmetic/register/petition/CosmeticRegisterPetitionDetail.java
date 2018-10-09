package com.anvisa.model.persistence.rest.cosmetic.register.petition;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.rest.detalhe.comestico.registrado.detalhe.peticao.RegistradoPeticaoApresentacao;
import com.anvisa.rest.detalhe.comestico.registrado.detalhe.peticao.RegistradoPeticaoFabricantesNacionais;

@Entity
@Table(name = "cosmetic_register_petition_detail")
public class CosmeticRegisterPetitionDetail extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String razaoSocial;
	private String autorizacao;
	private String cnpj;
	
	private String nomeProduto;
	private String categoria;
	
	private String registro;
	private String peticao;
	private LocalDate vencimento;
	

	private ArrayList<RegistradoPeticaoFabricantesNacionais> fabricantesNacionais;
	private ArrayList<RegistradoPeticaoApresentacao> apresentacoes;
	
}
