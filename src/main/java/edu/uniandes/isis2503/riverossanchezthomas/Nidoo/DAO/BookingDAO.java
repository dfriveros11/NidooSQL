package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Booking;

@Transactional
public interface BookingDAO extends CrudRepository<Booking, Long> {
	
	@Query("select u from  #{#entityName} u where u.id = ?1")
	public Booking findBookingByID(long id);
	
	@Query("select u from #{#entityName} u")
	public List<Booking> getAllBookings();

}
