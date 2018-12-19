package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Controller;

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
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO.GuardDAO;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Booking;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Guard;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Parking;

/**
 * @author TDJ
 */
@Controller
@RequestMapping("/guards")
public class GuardController 
{

	/**
	 * Connection to the  DAO of Guard
	 */
	@Autowired
	private GuardDAO guardDAO;
	
	/**
	 * Connection to the  DAO of Booking
	 */
	@Autowired
	private BookingDAO bookingDAO;
	
	/**
	 * /guards/create ->  create a new guard  and save it in the database. 
	 * @return a string describing if the guard is succesfully created or not.
	 */
	@PostMapping("/create")
	@ResponseBody
	public String createGuard(@RequestBody @Valid Guard guard)
	{
		try {
			guardDAO.save(guard);
		} catch (Exception ex) {
			return "Error creating the Guard : " + ex.toString();
		}
		return "Guard  identified with id "+ guard.getId()+ " was succesfully created" ;
	}
	
	/**
	 * /update/guards -> updated the guard with passed
	 * @return the guard updated 
	 */
	@PutMapping("/update")
	@ResponseBody
	public Guard updateGuard(@RequestBody @Valid Guard guard) {
		try {
			Guard oldGuard = guardDAO.findByIdGuard(guard.getId());
			if (oldGuard==null) {
				throw new Exception("The guard identified with id: "+ guard.getId() +" doesn´t exits");
			}
			oldGuard.setEmail(guard.getEmail());
			oldGuard.setPhone(guard.getPhone());
			guardDAO.save(oldGuard);
			return oldGuard;
		} catch (Exception ex) {
			ex.toString();
			return null;
		}
	}

	/**
	 * /guards/getbyid/{id} -> return the guard  the passed id.
	 * 
	 * @param id
	 *            The id to search in the database.
	 * @return The guard  or a message error if the guard  is not found.
	 */
	@GetMapping("/getbyid/{id}")
	@ResponseBody
	public Guard getGuard(@PathVariable("id") long id) {
		try {
			Guard guard = guardDAO.findByIdGuard(id);
			if (guard==null) {
				throw new Exception("The guard identified with id: "+id+" doesn´t exits");
			}
			return guard;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * /guards/allguards -> return all the guards..
	 * @return a empty list.
	 */
	@ResponseBody
	@GetMapping("/allguards")
	public List<Guard> getAllGuards() {
		try {
			List<Guard> guards = guardDAO.getAllGuards();
			return guards;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * /guards/delete/{id} -> delete the parking user having the  passed id.
	 * @param id The id for the guard  to delete
	 * @return A string describing if the guard  is succesfully deleted or not.
	 */
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public String deleteGuard(@PathVariable("id") long id) {
		try {
			Guard guard=guardDAO.findByIdGuard(id);
			if (guard==null) {
				throw new Exception("The guard identified with id: "+id+" doesn´t exits");
			}
			guardDAO.delete(new Guard(id));
		} catch (Exception ex) {
			return "Error deleting the guard :" + ex.toString();
		}
		return "the guard  identified with id :" +id +" was succesfully deleted!";
	}


	//PARKINGS
	/**
	 * Get the parking of the guard with id
	 * @param id of the parking
	 * @return the parking 
	 */
	@GetMapping("/{id}/parkings")
	@ResponseBody
	public Parking findParkingGuards(@PathVariable("id") long id){
		try {
			Guard guard=guardDAO.findByIdGuard(id);
			if (guard==null) {
				throw new Exception("The guard identified with id: "+id+" doesn´t exits");
			}
			return guard.getParking();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	//Booking
	
	/**
	 * Create a booking associated to a parking spot
	 * @param id 
	 * @param booking
	 * @return
	 */
	@PostMapping("/create/{id}/bookings")
	@ResponseBody
	public String createBookingGuard(@PathVariable("id") long id, @RequestBody @Valid Booking booking ) {
		try {
			Guard guard = guardDAO.findByIdGuard(id);
			if (guard==null) {
				throw new Exception("The guard with id:  " + id + " doesn't exist");
			}
			booking.setGuard(guard);
			bookingDAO.save(booking);
		}catch(Exception ex) {
			return "Error creating booking related with the guard with id: " + id;
		}
		return "booking succesfully created with id: "+ booking.getId()+" linked to gaurd with id: " + id;
	}
	
	
	/**
	 * Get a specific booking from a specific guard
	 * @param id id of the guard
	 * @param idB id of the booking
	 * @return
	 */
	@GetMapping("/getbyid/{id}/bookings/{idB}")
	@ResponseBody
	public Booking findByIDGuardBooking(@PathVariable("id") long id, @PathVariable("idB") long idB) {
		try {
			Guard guard = guardDAO.findByIdGuard(id);
			if (guard==null) {
				throw new Exception("The guard with id:  " + id + " doesn't exist");
			}
			Booking booking = bookingDAO.findBookingByID(idB);
			if (booking==null) {
				throw new Exception("The booking with id:  " + idB + " doesn't exist");
			}
			if(booking.getGuard().getId() != guard.getId()) {
				throw new Exception("The guard and the booking doesn't exist");
			}
			return booking;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Get the bookings of the guard
	 * @param id id of the guard
	 * @return the list of bookings of the parking provider
	 */
	@GetMapping("/{id}/allbookings")
	@ResponseBody
	public List<Booking> findGuardBooking(@PathVariable("id") long id){
		try {
			Guard guard = guardDAO.findByIdGuard(id);
			if (guard==null) {
				throw new Exception("The guard with id:  " + id + " doesn't exist");
			}
			return guard.getBookings();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	
	/**
	 * Update the booking of a certain guard
	 * @param id of the guard
	 * @param booking the booking
	 * @return the booking updated
	 */
	@PutMapping("/update/{id}/bookings/{idB}")
	@ResponseBody
	public Booking updateGuardBooking(@PathVariable("id") long id, @RequestBody @Valid Booking booking) {
		try {
			Guard guard = guardDAO.findByIdGuard(id);
			if (guard==null) {
				throw new Exception("The guard with id:  " + id + " doesn't exist");
			}
			Booking oldBooking = bookingDAO.findBookingByID(booking.getId());
			if (oldBooking==null) {
				throw new Exception("The booking with id:  " + booking.getId() + " doesn't exist");
			}
			oldBooking.setBeginDate(booking.getBeginDate());
			oldBooking.setEndDate(booking.getEndDate());
			oldBooking.setGuard(guard);
			bookingDAO.save(oldBooking);
			return oldBooking;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Delete a specific booking  of a specific guard
	 * @param id of the guard
	 * @param idB id of the booking
	 * @return the information if it was  succesfully deleted or not
	 */
	@DeleteMapping("/delete/{id}/bookings/{idB}")
	@ResponseBody
	public String deleteParkingSpotBooking(@PathVariable("id") long id, @PathVariable("idB") long idB) {
		try {
			Guard guard = guardDAO.findByIdGuard(id);
			if (guard==null) {
				throw new Exception("The guard with id:  " + id + " doesn't exist");
			}
			Booking booking = bookingDAO.findBookingByID(idB);
			if (booking==null) {
				throw new Exception("The booking with id:  " + idB + " doesn't exist");
			}
			booking.setGuard(guard);
			bookingDAO.delete(booking);
		} catch (Exception e) {
			return "Error deleting the booking:" + e.toString();
		}
		return "Booking with id "+ idB +"succesfully deleted!";
	}
	
}
