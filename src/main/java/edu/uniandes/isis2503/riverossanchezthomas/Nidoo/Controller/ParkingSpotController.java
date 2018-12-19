package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Controller;

import java.sql.Date;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO.BookingDAO;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO.ParkingSpotDAO;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Booking;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Parking;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.ParkingProvider;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.ParkingSpot;

/**
 * @author TDJ
 *
 */
@Controller
@RequestMapping("/parkingspots")
public class ParkingSpotController {

	/**
	 * Connection with the DAO of parking Spot
	 */
	@Autowired
	private ParkingSpotDAO parkingspotDAO;
	
	/**
	 * Connection with the DAO of the booking
	 */
	@Autowired
	private BookingDAO bookingDAO;
	
	/**
	 * /parkingspots/create ->  create a new parking spot and save it in the database.
	 * @return a string describing if the parking spot is succesfully created or not.
	 */
	@PostMapping("/create")
	@ResponseBody
	public String createParkingSpot(@RequestBody @Valid ParkingSpot parkingSpot)
	{
		try {
			parkingspotDAO.save(parkingSpot);
		} catch (Exception ex) {
			return "Error creating the Parking  Spot: " + ex.toString();
		}
		return "Parking Spot  identified with id "+ parkingSpot.getId()+ " was succesfully created" ;
	}
	
	
	/**
	 * /update/parkingspots -> updated the parking spot with passed
	 * @return the information that is updated  describing if the parking spot is succesfully updated or not.
	 */
	@PutMapping("/update")
	@ResponseBody
	public ParkingSpot updateParkingSpot(@RequestBody @Valid ParkingSpot parkingspot) {
		try {
			ParkingSpot oldParkingSpot = parkingspotDAO.findParkingSpotById(parkingspot.getId());
			if (oldParkingSpot==null) {
				throw new Exception("The Parking Spot with id: " + parkingspot.getId()+ " doesn´t exist in our database" );
			}
			oldParkingSpot.setNumber(parkingspot.getNumber());
			parkingspotDAO.save(oldParkingSpot);
			return oldParkingSpot;
		} catch (Exception ex) {
			ex.toString();
			return null;
		}
	}
	
	/**
	 * /parkingspots/getbyid/{id} -> return the parking spot having the passed id.
	 * @param id The id to search in the database
	 * @return The parking spot or a message error if the parking spot is not found.
	 */
	@GetMapping("/getbyid/{id}")
	@ResponseBody
	public ParkingSpot getParkingSpot(@PathVariable("id") long id) {
		try {
			ParkingSpot parkingSpot = parkingspotDAO.findParkingSpotById(id);
			if (parkingSpot==null) {
				throw new Exception("The Parking Spot with id: " + id+ " doesn´t exist in our database" );
			}
			return parkingSpot;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * /parkingspots/allparkingspots -> return all the parking spots..
	 * @return a empty list.
	 */
	@ResponseBody
	@GetMapping("/allparkingspots")
	public List<ParkingSpot> getAllParkingSpots() {
		try {
			List<ParkingSpot> spots = parkingspotDAO.getAllParkingSpots();
			return spots;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * /parkingspots/delete/{id} -> delete the parking spot user having thepassed id.
	 * @param id The id for the parking spot to delete
	 * @return A string describing if the parking spot is succesfully deleted or not.
	 */
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public String deleteParkingSpot(@PathVariable("id") long id) {
		try {
			if (parkingspotDAO.findParkingSpotById(id)==null) {
				throw new Exception("The Parking Spot with id: " + id+ " doesn´t exist in our database" );	
			}
			parkingspotDAO.delete(new ParkingSpot(id));
		} catch (Exception ex) {
			return "Error deleting the parking Spot:" + ex.toString();
		}
		return "the parking spot identified with id :" +id +" was succesfully deleted!";
	}

								//PARKING PROVIDER//

	/**
	 * Get the parking provider of a parking spot
	 * @param id id of the parking spot
	 * @return the parking provider of the parking spot
	 */
	@GetMapping("/{id}/parkingproviders")
	@ResponseBody
	public ParkingProvider findParkingSpotParkingprovider(@PathVariable("id") long id){
		try {
			ParkingSpot parkingSpot = parkingspotDAO.findParkingSpotById(id);
			if (parkingSpot==null) {
				throw new Exception("The Parking Spot with id: " + id+ " doesn´t exist in our database" );
			}
			return parkingSpot.getParkingProvider();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	
											//PARKING
	/**
	 * get te parking of a specific parking spot
	 * @param id of the parking spot
	 * @return the parking of the parking spot
	 */
	@GetMapping("/{id}/parkings")
	@ResponseBody
	public Parking findParkingSpotsParking(@PathVariable("id") long id){
		try {
			ParkingSpot parkingSpot = parkingspotDAO.findParkingSpotById(id);
			if (parkingSpot==null) {
				throw new Exception("The Parking Spot with id: " + id+ " doesn´t exist in our database" );
			}
			return parkingSpot.getParking();
		} catch (Exception e) {
			return null;
		}
		
	}	
	
	// BOOKING
	
	/**
	 * Create a booking associated to a parking spot
	 * @param id 
	 * @param booking
	 * @return
	 */
	@PostMapping("/create/{id}/bookings")
	@ResponseBody
	public String createBookingParkingSpot(@PathVariable("id") long id, @RequestBody @Valid Booking booking ) {
		try {
			ParkingSpot parkingSpot = parkingspotDAO.findParkingSpotById(id);
			if (parkingSpot==null) {
				throw new Exception("The Parking spot with id:  " + id + " doesn't exist");
			}
			java.util.Date d = new java.util.Date();
			Date date = new Date(d.getTime());
			Booking bookingTest = parkingspotDAO.getBookingInDate(date);
			if(bookingTest != null) {
				throw new Exception("The parking spot with id:" + id + "is currently bussy");
			}
			booking.setParkingSpot(parkingSpot);
			bookingDAO.save(booking);
		}catch(Exception ex) {
			return "Error creating booking related with the parking spot with id: " + id;
		}
		return "booking succesfully created with id: "+ booking.getId()+" linked to parking spot with id: " + id;
	}
	
	
	/**
	 * Get a specific booking from a specific parking spot
	 * @param id id of the parking spot
	 * @param idB id of the booking
	 * @return
	 */
	@GetMapping("/getbyid/{id}/bookings/{idB}")
	@ResponseBody
	public Booking findByIDParkingSpotBooking(@PathVariable("id") long id, @PathVariable("idB") long idB) {
		try {
			ParkingSpot parkingSpot = parkingspotDAO.findParkingSpotById(id);
			if (parkingSpot==null) {
				throw new Exception("The Parking spot with id:  " + id + " doesn't exist");
			}
			Booking booking = bookingDAO.findBookingByID(idB);
			if (booking==null) {
				throw new Exception("The booking with id:  " + idB + " doesn't exist");
			}
			if(booking.getParkingSpot().getId() != parkingSpot.getId()) {
				throw new Exception("The Parking spot and the booking doesn't exist");
			}
			return booking;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Get the bookings of the parkingSpot
	 * @param id id of the parking provider
	 * @return the list of bookings of the parking provider
	 */
	@GetMapping("/{id}/allbookings")
	@ResponseBody
	public List<Booking> findParkingSpotBooking(@PathVariable("id") long id){
		try {
			ParkingSpot parkingSpot = parkingspotDAO.findParkingSpotById(id);
			if (parkingSpot==null) {
				throw new Exception("The Parking spot with id:  " + id + " doesn't exist");
			}
			return parkingSpot.getBookings();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	
	/**
	 * Update the booking of a certain parking spot
	 * @param id of the parking spot
	 * @param booking the booking
	 * @return the booking updated
	 */
	@PutMapping("/update/{id}/bookings/{idB}")
	@ResponseBody
	public Booking updateParkingSpotBooking(@PathVariable("id") long id, @RequestBody @Valid Booking booking) {
		try {
			ParkingSpot parkingSpot = parkingspotDAO.findParkingSpotById(id);
			if (parkingSpot==null) {
				throw new Exception("The Parking spot with id:  " + id + " doesn't exist");
			}
			Booking oldBooking = bookingDAO.findBookingByID(booking.getId());
			if (oldBooking==null) {
				throw new Exception("The booking with id:  " + booking.getId() + " doesn't exist");
			}
			oldBooking.setBeginDate(booking.getBeginDate());
			oldBooking.setEndDate(booking.getEndDate());
			oldBooking.setParkingSpot(parkingSpot);
			bookingDAO.save(oldBooking);
			return oldBooking;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Delete a specific booking  of a specific parking spot
	 * @param id of the parking spot
	 * @param idB id of the booking
	 * @return the information if it was  succesfully deleted or not
	 */
	@DeleteMapping("/delete/{id}/bookings/{idB}")
	@ResponseBody
	public String deleteParkingSpotBooking(@PathVariable("id") long id, @PathVariable("idB") long idB) {
		try {
			ParkingSpot parkingSpot = parkingspotDAO.findParkingSpotById(id);
			if (parkingSpot==null) {
				throw new Exception("The Parking spot with id:  " + id + " doesn't exist");
			}
			Booking booking = bookingDAO.findBookingByID(idB);
			if (booking==null) {
				throw new Exception("The booking with id:  " + idB + " doesn't exist");
			}
			booking.setParkingSpot(parkingSpot);
			bookingDAO.delete(booking);
		} catch (Exception e) {
			return "Error deleting the booking:" + e.toString();
		}
		return "Booking with id "+ idB +"succesfully deleted!";
	}
}
