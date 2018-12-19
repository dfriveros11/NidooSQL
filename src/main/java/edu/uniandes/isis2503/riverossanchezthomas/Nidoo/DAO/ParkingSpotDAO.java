package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Booking;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.ParkingSpot;

@Transactional
public interface ParkingSpotDAO extends CrudRepository<ParkingSpot, Long>
{

	@Query(value ="select u from #{#entityName} u where u.id = ?1")
	public ParkingSpot findParkingSpotById(long id);
	
	@Query("select u from #{#entityName} u")
	public List<ParkingSpot> getAllParkingSpots();
	
	@Query(value="SELECT booking.* FROM parking_spot INNER JOIN booking ON booking.parking_spot_id=parking_spot.id WHERE booking.begin_date >= :date  AND booking.end_date <= :date", nativeQuery=true)
	public Booking getBookingInDate(@Param("date") Date date);
}
