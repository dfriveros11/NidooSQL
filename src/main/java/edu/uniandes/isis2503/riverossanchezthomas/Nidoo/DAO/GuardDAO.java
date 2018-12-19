package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Guard;

@Transactional
public interface GuardDAO extends CrudRepository<Guard, Long>
{
	@Query(value ="select u from #{#entityName} u where u.id = ?1")
	public Guard findByIdGuard(long id);
	
	@Query("select u from #{#entityName} u")
	public List<Guard> getAllGuards();
}

