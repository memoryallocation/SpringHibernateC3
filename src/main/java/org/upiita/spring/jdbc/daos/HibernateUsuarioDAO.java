package org.upiita.spring.jdbc.daos;
///ola k ase
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.upiita.spring.jdbc.entidades.Usuario;

@Component("usuarioDAO")
public class HibernateUsuarioDAO implements UsuarioDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public Usuario buscaUsuarioPorId(Integer usuarioId) {
		Session sesion = sessionFactory.openSession(); 
		sesion.beginTransaction();
		//INICIA TRANSACCION
		
		Usuario usuario = (Usuario) sesion.get(Usuario.class, usuarioId);		
		
		//obteniendo la transaccion actual y guardando todos los 
		//cambios en la base
		sesion.getTransaction().commit();
		sesion.close();

		return usuario;
	}

	public void creaUsuario(Usuario usuario) {
		Session sesion = sessionFactory.openSession(); 
		sesion.beginTransaction();
		//INICIA TRANSACCION
		
		sesion.saveOrUpdate(usuario);
		
		//obteniendo la transaccion actual y guardando todos los 
		//cambios en la base
		sesion.getTransaction().commit();
		sesion.close();
	}

	public Usuario buscaPorEmailYPassword(String email, String password) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		/**
		 * criterio de hibernate
		 * */
		Criteria criterio = session.createCriteria(Usuario.class);
		criterio.add(Restrictions.eq("email", email));
		criterio.add(Restrictions.eq("password",password ));
		criterio.uniqueResult();
		Usuario usuario = (Usuario) criterio.uniqueResult();
		session.getTransaction().commit();
		session.close();
		return usuario;
	}

	public List<Usuario> buscarPorNombre(String nombre) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Criteria criterio = session.createCriteria(Usuario.class);
		criterio.add(Restrictions.like("nombre","%" +nombre+"%"));
		List<Usuario>usuarios=criterio.list();
		session.getTransaction().commit();
		session.close();
		return usuarios;
	}
	

}




