package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.User;

@NoRepositoryBean
public interface UserBaseDAO<T extends User> extends CrudRepository<T, Long>{

	public User findByEmail(String email);
	
	@Query("select u from  #{#entityName} u where u.id = ?1")
	public User findUserByID(long id);
}
