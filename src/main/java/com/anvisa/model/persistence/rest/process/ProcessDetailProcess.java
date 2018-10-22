package com.anvisa.model.persistence.rest.process;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "process_detail_process")
public class ProcessDetailProcess extends BaseEntity {

	
		/**
		 * 
	 	 */
		private static final long serialVersionUID = 1L;

		@Column(name = "number", length = 20)
		@JsonAlias(value = "numero")
	    String numero;
	    
		@JsonAlias(value = "processPetition")
		@OneToOne(cascade = CascadeType.ALL, optional = true)
		@JoinColumn(name="ProcessPetitionDetailFK")
	    ProcessPetition peticao;
	    
		public String getNumero() {
			return numero;
		}
		public void setNumero(String numero) {
			this.numero = numero;
		}
		public ProcessPetition getPeticao() {
			return peticao;
		}
		public void setPeticao(ProcessPetition peticao) {
			this.peticao = peticao;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + ((numero == null) ? 0 : numero.hashCode());
			result = prime * result + ((peticao == null) ? 0 : peticao.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof ProcessDetailProcess)) {
				return false;
			}
			ProcessDetailProcess other = (ProcessDetailProcess) obj;
			if (numero == null) {
				if (other.numero != null)
					return false;
			} else if (!numero.equals(other.numero))
				return false;
			if (peticao == null) {
				if (other.peticao != null)
					return false;
			} else if (!peticao.equals(other.peticao))
				return false;
			return true;
		}
		
		

}
