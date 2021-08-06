package managedBean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import com.google.gson.Gson;

import DAO.DaoUsuario;
import model.UsuarioPessoa;

@ManagedBean(name = "usuarioPessoaManagedBean") 
@ViewScoped
public class UsuarioPessoaManagedBean {
	
	private UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
	private List<UsuarioPessoa> list = new ArrayList<UsuarioPessoa>();
	private DaoUsuario<UsuarioPessoa> daoGeneric = new DaoUsuario<UsuarioPessoa>();
	
	@PostConstruct
	public void init() {
		list = daoGeneric.listar(UsuarioPessoa.class);
	}
	
	public UsuarioPessoa getUsuarioPessoa() {
		return usuarioPessoa;
	}
	public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
		this.usuarioPessoa = usuarioPessoa;
	}
	
	public String salvar() {
		
		
		daoGeneric.salvar(usuarioPessoa);
		list.add(usuarioPessoa);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
				(FacesMessage.SEVERITY_INFO,  "Informação: ", "Salvo com Sucesso!"));
		
		
		return"";
	}
	


	public String novo() {
		usuarioPessoa = new UsuarioPessoa();
		return"";
	}
	
	public List<UsuarioPessoa> getList() {
		return list;
	}
	
	public String remover() {
		try {
		daoGeneric.removerUsuario(usuarioPessoa);
		list.remove(usuarioPessoa);
		usuarioPessoa = new UsuarioPessoa();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
				(FacesMessage.SEVERITY_INFO,  "Informação: ", "Removido com Sucesso!"));
		}catch(Exception e) {
			
			if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,"Informação: ", "Existem telefones parao usuário!"));
		}else
			e.printStackTrace();
		}
		
		return"";
	}
	
	public void pesquisaCep(AjaxBehaviorEvent event) {
		
		try {
			
			URL url = new URL("https://viacep.com.br/ws/"+ usuarioPessoa.getCep() +"/json/");
			
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			String cep = "";
			StringBuilder JsonCep = new StringBuilder(); 
			
			while((cep = br.readLine()) != null){
				JsonCep.append(cep);
			}
			
			UsuarioPessoa gsonAux = new Gson().fromJson(JsonCep.toString(), UsuarioPessoa.class);
			
			usuarioPessoa.setCep(gsonAux.getCep());
			usuarioPessoa.setLogradouro(gsonAux.getLogradouro());
			usuarioPessoa.setComplemento(gsonAux.getComplemento());
			usuarioPessoa.setBairro(gsonAux.getBairro());
			usuarioPessoa.setLocalidade(gsonAux.getLocalidade());
			usuarioPessoa.setUf(gsonAux.getUf());
			usuarioPessoa.setIbge(gsonAux.getIbge());
		    
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
}
