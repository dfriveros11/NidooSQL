package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Parking;


@Transactional
public interface ParkingDAO extends CrudRepository<Parking, Long>
{

	@Query(value ="select u from #{#entityName} u where u.id = ?1")
	public Parking findParkingsById(long id);
	
	@Query("select u from #{#entityName} u")
	public List<Parking> getAllParkings();
}
