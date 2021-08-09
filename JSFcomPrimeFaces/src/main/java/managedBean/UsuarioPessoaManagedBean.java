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

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.google.gson.Gson;

import DAO.DaoEmail;
import DAO.DaoUsuario;
import model.EmailUser;
import model.UsuarioPessoa;

@ManagedBean(name = "usuarioPessoaManagedBean") 
@ViewScoped
public class UsuarioPessoaManagedBean {
	
	private UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
	private List<UsuarioPessoa> list = new ArrayList<UsuarioPessoa>();
	private DaoUsuario<UsuarioPessoa> daoGeneric = new DaoUsuario<UsuarioPessoa>();
	private BarChartModel barCharModel;
	private EmailUser emailuser = new EmailUser();
	private DaoEmail<EmailUser> daoEmail = new DaoEmail<EmailUser>();
	private String campoPesquisa;
	
	@PostConstruct
	public void init() {
	
		list = daoGeneric.listar(UsuarioPessoa.class);
		
		montarGráfico();
	
		
		
	}

	private void montarGráfico() {
		barCharModel = new BarChartModel();
		ChartSeries  userSalario = new ChartSeries();/* Grupo de Funcionários */
		userSalario.setLabel("Users"); 
		
		for (UsuarioPessoa usuarioPessoa : list) { /* Add salario para o grupo */
			userSalario.set(usuarioPessoa.getNome(), usuarioPessoa.getSalario()); /* add salários */
			
		}
		barCharModel.addSeries(userSalario); /* Adiciona o grupo */
		barCharModel.setTitle("Gráfico de Salários");
	}
	
	public BarChartModel getBarCharModel() {
		return barCharModel;
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
		usuarioPessoa = new UsuarioPessoa();
		init();
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
		init();
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
	
	public void setEmailuser(EmailUser emailuser) {
		this.emailuser = emailuser;
	}
	public EmailUser getEmailuser() {
		return emailuser;
	}
	
	public void addEmail() {
		emailuser.setUsuarioPessoa(usuarioPessoa);
		emailuser = daoEmail.updateMerge(emailuser);
		usuarioPessoa.getEmails().add(emailuser);
		emailuser = new EmailUser();
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Resultado:", "Salvo com sucesso!"));
		
	}
	
	public void removerEmail() throws Exception {
	String codigoemail = FacesContext.getCurrentInstance().getExternalContext()
			.getRequestParameterMap().get("codigoemail");
	
	EmailUser remover = new EmailUser();
	remover.setId(Long.parseLong(codigoemail));
	daoEmail.deletarPorId(remover);
	usuarioPessoa.getEmails().remove(remover);
	
	FacesContext.getCurrentInstance().addMessage(null, 
			new FacesMessage(FacesMessage.SEVERITY_INFO, "Resultado:", "Removido com sucesso!"));
	}

	public String getCampoPesquisa() {
		return campoPesquisa;
	}

	public void setCampoPesquisa(String campoPesquisa) {
		this.campoPesquisa = campoPesquisa;
	}
	
	public void pesquisar() {
		list = daoGeneric.pesquisar(campoPesquisa);
		montarGráfico();
	}
	
}
