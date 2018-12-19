package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Refund;

@Transactional
public interface RefundDAO extends CrudRepository<Refund, Long>{

	@Query("select u from  #{#entityName} u where u.id = ?1")
	public Refund findRefundByID(long id);
	
	@Query("select u from #{#entityName} u")
	public List<Refund> getAllRefunds();
	
}
