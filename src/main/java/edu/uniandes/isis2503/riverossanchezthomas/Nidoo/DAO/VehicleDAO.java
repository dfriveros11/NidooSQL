package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Vehicle;


@Transactional
public interface VehicleDAO extends CrudRepository<Vehicle, Long>{

	@Query("select u from  #{#entityName} u where u.id = ?1")
	public Vehicle findVehicleByID(long id);
	
	@Query("select u from #{#entityName} u")
	public List<Vehicle> getAllVehicle();
}
