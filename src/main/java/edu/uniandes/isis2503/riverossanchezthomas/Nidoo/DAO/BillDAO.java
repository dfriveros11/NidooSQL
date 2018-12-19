package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Bill;

@Transactional
public interface BillDAO extends CrudRepository<Bill, Long>{
	
	@Query("select u from  #{#entityName} u where u.id = ?1")
	public Bill findBillByID(long id);
	
	@Query("select u from #{#entityName} u")
	public List<Bill> getAllBill();

}
