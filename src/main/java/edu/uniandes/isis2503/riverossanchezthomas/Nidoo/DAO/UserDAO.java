package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.User;

@Transactional
public interface UserDAO extends UserBaseDAO<User>{

	@Query("select u from #{#entityName} u")
	public List<User> getAllUsers();
	
}
