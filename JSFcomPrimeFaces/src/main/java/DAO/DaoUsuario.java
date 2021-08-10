package DAO;

import java.util.List;

import javax.persistence.Query;

import model.UsuarioPessoa;

public class DaoUsuario<E> extends DaoGenerico<UsuarioPessoa> {
	
	

	public void removerUsuario(UsuarioPessoa pessoa) throws Exception {
getEntityManager().getTransaction().begin();
		
		String sqlDeleteFone = "delete from telefoneuser where usuariopessoa_id = " + pessoa.getId();
        //método do DaoGeneric da conexão com o banco de dados  
		getEntityManager().createNativeQuery(sqlDeleteFone).executeUpdate();//faz atualização ou delete
    	
    	String sqlDeleteEmail = "delete from emailuser where usuariopessoa_id = " + pessoa.getId();
    	getEntityManager().createNativeQuery(sqlDeleteEmail).executeUpdate();
    	
    	getEntityManager().getTransaction().commit();
		
		super.deletarPorId(pessoa);
		}

	public List<UsuarioPessoa> pesquisar(String campoPesquisa) {
		
		Query query = super.getEntityManager().createQuery("from UsuarioPessoa where nome like '%"+campoPesquisa+"%'");
		
		
		return query.getResultList();
	}
	
	
}
