package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


@Entity
@NamedQueries({
	
	@NamedQuery(name = "UsuarioPessoas.todos", query = " select u from UsuarioPessoa u"),
	@NamedQuery(name = "UsuarioPessoas.buscaPorNome", query = " select u from UsuarioPessoa u where u.nome = :nome")
	
})
public class UsuarioPessoa {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nome;
	private String sobrenome;
	private String login;
	private String senha;
	private String sexo;
	private int idade;
	private Double salario;
	
	@Column(columnDefinition = "text")
	private String imagem;
	
	
	// mapeado com usuarioPessoa do telefoneUser que está fazendo o ManyToOne
	// ele que vai trazer os telefones dos usuarios
		@OneToMany(mappedBy = "usuarioPessoa", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
		private List<TelefoneUser> telefoneUsers = new ArrayList<TelefoneUser>();
		
		@OneToMany(mappedBy = "usuarioPessoa", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
		private List<EmailUser> emails =  new ArrayList<EmailUser>();
		
		public List<EmailUser> getEmails() {
			return emails;
		}
		
		public void setEmails(List<EmailUser> emails) {
			this.emails = emails;
		}
		
		private String cep;
		private String logradouro;
		private String complemento;
		private String localidade;
		private String bairro;
		private String uf;
		private String ibge;
		
		public String getImagem() {
			return imagem;
		}

		public void setImagem(String imagem) {
			this.imagem = imagem;
		}

		public Double getSalario() {
			return salario;
		}

		public void setSalario(Double salario) {
			this.salario = salario;
		}

		public String getCep() {
			return cep;
		}

		public void setCep(String cep) {
			this.cep = cep;
		}

		public String getLogradouro() {
			return logradouro;
		}

		public void setLogradouro(String logradouro) {
			this.logradouro = logradouro;
		}

		public String getComplemento() {
			return complemento;
		}

		public void setComplemento(String complemento) {
			this.complemento = complemento;
		}

		public String getLocalidade() {
			return localidade;
		}

		public void setLocalidade(String localidade) {
			this.localidade = localidade;
		}

		public String getBairro() {
			return bairro;
		}

		public void setBairro(String bairro) {
			this.bairro = bairro;
		}

		public String getUf() {
			return uf;
		}

		public void setUf(String uf) {
			this.uf = uf;
		}

		public String getIbge() {
			return ibge;
		}

		public void setIbge(String ibge) {
			this.ibge = ibge;
		}

		public void setTelefoneUsers(List<TelefoneUser> telefoneUsers) {
			this.telefoneUsers = telefoneUsers;
		}

		public List<TelefoneUser> getTelefoneUsers() {
			return telefoneUsers;
		}
	
		
	
	public String getSexo() {
			return sexo;
		}

		public void setSexo(String sexo) {
			this.sexo = sexo;
		}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}


	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "UsuarioPessoa [id=" + id + ", nome=" + nome + ", sobrenome=" + sobrenome + ", email=" 
				+ ", login=" + login + ", senha=" + senha + ", idade=" + idade + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioPessoa other = (UsuarioPessoa) obj;
		return Objects.equals(id, other.id);
	}
	
	

}
