package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.ParkingConsumer;

@Transactional
public interface ParkingConsumerDAO extends UserBaseDAO<ParkingConsumer>{

	@Query(value ="select u from #{#entityName} u where u.id = ?1")
	public ParkingConsumer findParkingConsumerByID(long id);
	
	@Query("select u from #{#entityName} u")
	public List<ParkingConsumer> getAllParkingConsumer();
}
