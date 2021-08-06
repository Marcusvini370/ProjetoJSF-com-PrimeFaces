package DAO;

import model.UsuarioPessoa;

public class DaoUsuario<E> extends DaoGenerico<UsuarioPessoa> {
	
	

	public void removerUsuario(UsuarioPessoa pessoa) throws Exception {
		getEntityManager().getTransaction().begin();
		String sqlDeleteFone  = "delete from telefoneUser where usuariopessoa_id = " + pessoa.getId();	
		getEntityManager().createNativeQuery(sqlDeleteFone).executeUpdate();
		getEntityManager().getTransaction().commit();
		
		super.deletarPorId(pessoa);
		}
	
	
}
