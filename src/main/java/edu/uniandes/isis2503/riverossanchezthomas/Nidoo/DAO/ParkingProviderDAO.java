package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.ParkingProvider;

@Transactional
public interface ParkingProviderDAO extends UserBaseDAO<ParkingProvider>{

	@Query("select u from  #{#entityName} u where u.id = ?1")
	public ParkingProvider findParkingProviderByID(long id);
	
	@Query("select u from #{#entityName} u")
	public List<ParkingProvider> getAllParkingProvider();
}
