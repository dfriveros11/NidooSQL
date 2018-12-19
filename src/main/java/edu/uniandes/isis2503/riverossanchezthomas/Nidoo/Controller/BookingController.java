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
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO.BillDAO;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO.BookingDAO;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Bill;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Booking;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Guard;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.ParkingProvider;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.ParkingSpot;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Vehicle;

@Controller
@RequestMapping("/bookings")
public class BookingController {
	
	

	/**
	 * Connection to the DAO of booking
	 */
	@Autowired
	private BookingDAO bookingDAO;
	

	/**
	 * Connection to the DAO of bill
	 */
	@Autowired
	private BillDAO billDAO;
	
	
	/**
	 * /bookings/create ->  create a new booking and save it in the database.
	 * @return a string describing if the booking is succesfully created or not.
	 */
	@PostMapping("/create")
	@ResponseBody
	public String createBooking(@RequestBody @Valid Booking booking ) {
		try {
			bookingDAO.save(booking);
		} catch (Exception ex) {
			return "Error creating the booking: " + ex.toString();
		}
		return "Booking succesfully created with id :" + booking.getId();
	}

	
	/**
	 * /bookings/update -> updated the booking with passed
	 * @return A string describing if the booking is succesfully updated or not.
	 */
	@PutMapping("/update")
	@ResponseBody
	public Booking updateBooking(@RequestBody @Valid Booking booking) {
		try {
			Booking oldBooking = bookingDAO.findBookingByID(booking.getId());
			if (oldBooking==null) {
				throw new Exception("The booking with id: " + booking.getId()+ " doesn´t exist in our database" );
			}
			oldBooking.setBeginDate(booking.getBeginDate());
			oldBooking.setEndDate(booking.getEndDate());
			bookingDAO.save(oldBooking);
			return oldBooking;
		} catch (Exception ex) {
			ex.toString();
			return null;
		}
	}
	
	/**
	 * /bookings/getbyid/{id} -> return the booking having the passed id.
	 * @param id The id to search in the database.
	 * @return The booking or a message error if the booking is not found.
	 */
	@GetMapping("/getbyid/{id}")
	@ResponseBody
	public Booking getBookingID(@PathVariable("id") long id) {
		try {
			Booking booking = bookingDAO.findBookingByID(id);
			if (booking==null) {
				throw new Exception("The booking with id: " + id + " doesn´t exist in our database" );
			}
			return booking;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * /bookings/allbookings -> return all the bookings..
	 * @return a empty list.
	 */
	@ResponseBody
	@GetMapping("/allbookings")
	public List<Booking> getBookings() {
		try {
			List<Booking> bookings = bookingDAO.getAllBookings();
			return bookings;
		} catch (Exception ex) {
			return null;
		}
	}

	
	/**
	 * /bookings/delete/{id} -> delete the booking having the
	 * passed id.
	 * 
	 * @param id
	 *            The id for the booking to delete
	 * @return A string describing if the booking is succesfully deleted or not.
	 */
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public String deleteBooking(@PathVariable("id") long id) {
		try {
			if (bookingDAO.findBookingByID(id)==null) {
				throw new Exception("The booking with id: " + id + " doesn´t exist in our database" );
			}
			bookingDAO.delete(new Booking(id));
		} catch (Exception ex) {
			return "Error deleting the booking:" + ex.toString();
		}
		return "booking succesfully deleted with id: "+ id;
	}
	
	/**
	 * Get the  parking provider of a booking
	 * @param id id of the booking
	 * @return the parking provider
	 */
	@GetMapping("/{id}/parkingproviders")
	@ResponseBody
	public ParkingProvider findParkingProviderBooking(@PathVariable("id") long id){
		try {
			Booking booking = bookingDAO.findBookingByID(id);
			if (booking==null) {
				throw new Exception("The booking with id:  " + id + " doesn't exist");
			}
			return booking.getParkingProvider();
		} catch (Exception e) {
			return null;
		}
		
	}

	/**
	 * Get the vehicles of the booking
	 * @param id id of the booking
	 * @return the list of the vehicles
	 */
	@GetMapping("/{id}/allvehicles")
	@ResponseBody
	public List<Vehicle> findVehiclesBooking(@PathVariable("id") long id){
		try {
			Booking booking = bookingDAO.findBookingByID(id);
			if (booking==null) {
				throw new Exception("The booking spot with id:  " + id + " doesn't exist");
			}
			return booking.getVehicles();
		} catch (Exception e) {
			return null;
		}
		
	}


	/**
	 * Get the  parking spot of a booking
	 * @param id id of the booking
	 * @return the parking spot
	 */
	@GetMapping("/{id}/parkingspots")
	@ResponseBody
	public ParkingSpot findParkingSpotBooking(@PathVariable("id") long id){
		try {
			Booking booking = bookingDAO.findBookingByID(id);
			if (booking==null) {
				throw new Exception("The booking with id:  " + id + " doesn't exist");
			}
			return booking.getParkingSpot();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	/**
	 * Get the  guard of a booking
	 * @param id id of the booking
	 * @return the parking spot
	 */
	@GetMapping("/{id}/guards")
	@ResponseBody
	public Guard findGuardBooking(@PathVariable("id") long id){
		try {
			Booking booking = bookingDAO.findBookingByID(id);
			if (booking==null) {
				throw new Exception("The booking with id:  " + id + " doesn't exist");
			}
			return booking.getGuard();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	//Bill
	// BOOKING
	
		/**
		 * Create a bill associated to a booking
		 * @param id 
		 * @param bill
		 * @return
		 */
		@PostMapping("/create/{id}/bills")
		@ResponseBody
		public String createBookingBill(@PathVariable("id") long id, @RequestBody @Valid Bill bill ) {
			try {
				Booking booking = bookingDAO.findBookingByID(id);
				if (booking==null) {
					throw new Exception("The booking spot with id:  " + id + " doesn't exist");
				}
				if (booking.getBills().size()==1) {
					throw new Exception("The booking spot with id:  " + id + " already have a bill");
				}
				bill.setBooking(booking);
				billDAO.save(bill);
			}catch(Exception ex) {
				return "Error creating bill related with the booking  with id: " + id;
			}
			return "bill succesfully created with id: "+ bill.getId()+" linked to booking with id: " + id;
		}
		
		
		/**
		 * Get a specific Bill of a booking
		 * @param id id of the booking
		 * @param idB id of the bill
		 * @return
		 */
		@GetMapping("/getbyid/{id}/bills/{idB}")
		@ResponseBody
		public Bill findByIDBillBooking(@PathVariable("id") long id, @PathVariable("idB") long idB) {
			try {
				Booking booking = bookingDAO.findBookingByID(id);
				if (booking==null) {
					throw new Exception("The booking spot with id:  " + id + " doesn't exist");
				}
				Bill bill = billDAO.findBillByID(idB);
				if (bill==null) {
					throw new Exception("The bill with id:  " + idB + " doesn't exist");
				}
				if(bill.getBooking().getId() != booking.getId()) {
					throw new Exception("The bill  and the booking doesn't exist");
				}
				return bill;
			} catch (Exception e) {
				return null;
			}
		}
		
		/**
		 * Get the bills of the booking
		 * @param id id of the bill
		 * @return the list of the bills
		 */
		@GetMapping("/{id}/allbills")
		@ResponseBody
		public List<Bill> findBillBooking(@PathVariable("id") long id){
			try {
				Booking booking = bookingDAO.findBookingByID(id);
				if (booking==null) {
					throw new Exception("The booking spot with id:  " + id + " doesn't exist");
				}
				return booking.getBills();
			} catch (Exception e) {
				return null;
			}
			
		}
		
		/**
		 * Update the bill of a booking
		 * @param id of the booking
		 * @param bill 
		 * @return the bill updated
		 */
		@PutMapping("/update/{id}/bills/{idB}")
		@ResponseBody
		public Bill updateBillBooking(@PathVariable("id") long id, @RequestBody @Valid Bill bill) {
			try {
				Booking booking = bookingDAO.findBookingByID(id);
				if (booking==null) {
					throw new Exception("The booking spot with id:  " + id + " doesn't exist");
				}
				Bill oldBill = billDAO.findBillByID(bill.getId());
				if (oldBill==null) {
					throw new Exception("The bill with id:  " + bill.getId() + " doesn't exist");
				}
				oldBill.setCreationDate(bill.getCreationDate());
				oldBill.setTotalPrice(bill.getTotalPrice());
				oldBill.setBooking(booking);
				billDAO.save(oldBill);
				return oldBill;
			} catch (Exception e) {
				return null;
			}
		}
		
		/**
		 * Delete a specific bill of a booking
		 * @param id of the booking
		 * @param idB id of bill
		 * @return the information if it was  succesfully deleted or not
		 */
		@DeleteMapping("/delete/{id}/bills/{idB}")
		@ResponseBody
		public String deleteBillBooking(@PathVariable("id") long id, @PathVariable("idB") long idB) {
			try {
				Booking booking = bookingDAO.findBookingByID(id);
				if (booking==null) {
					throw new Exception("The booking spot with id:  " + id + " doesn't exist");
				}
				Bill bill = billDAO.findBillByID(idB);
				if (bill==null) {
					throw new Exception("The bill with id:  " +id+ " doesn't exist");
				}
				bill.setBooking(booking);
				billDAO.delete(bill);
			} catch (Exception e) {
				return "Error deleting the bill:" + e.toString();
			}
			return "Bill with id "+ idB +"succesfully deleted!";
		}
		
		
}
