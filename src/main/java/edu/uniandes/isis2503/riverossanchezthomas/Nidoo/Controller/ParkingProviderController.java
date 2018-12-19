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
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO.ParkingDAO;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO.ParkingProviderDAO;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO.ParkingSpotDAO;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Booking;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Parking;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.ParkingProvider;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.ParkingSpot;

/**
 * @author TDJ
 */
@Controller
@RequestMapping("/parkingproviders")
public class ParkingProviderController extends UserController{

	/**
	 * Connection with the DAO of parking provider
	 */
	@Autowired
	private ParkingProviderDAO parkingProviderDAO;
	
	/**
	 * Connection with the DAO of parking 
	 */
	@Autowired
	private ParkingDAO parkingDao;
	
	
	/**
	 * Connection with the DAO of parking spot
	 */
	@Autowired
	private ParkingSpotDAO parkingSpotDAO;
	
	/**
	 * Connection with the DAO of booking
	 */
	@Autowired
	private BookingDAO bookingDAO;
	
	/**
	 * /parkingproviders/create ->  create a new parkingprovider user and save it in the database. 
	 * @return a string describing if the parking provider is succesfully created or not.
	 */
	@PostMapping("/create")
	@ResponseBody
	public String createParkingProvider(@RequestBody @Valid ParkingProvider parkingProvider) {
		try {
			parkingProviderDAO.save(parkingProvider);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Error creating the Parking Provider with id: " + parkingProvider.getId();
		}
		return "Parking Provider with id: " + parkingProvider.getId() +" succesfully created!";
	}
	
	
	/**
	 * /parkingproviders/update -> updated the parking provider with passed id 
	 * @return A string describing if the user is succesfully updated or not.
	 */
	@PutMapping("/update")
	@ResponseBody
	public ParkingProvider updateParkingProvider(@RequestBody @Valid ParkingProvider parkingProvider) {
		try {
			
			long idParkingProvider = parkingProvider.getId();
			ParkingProvider oldParkingProvider = parkingProviderDAO.findParkingProviderByID(idParkingProvider);
			if(oldParkingProvider == null) {
				throw new Exception("The Parking provider with id:  " + idParkingProvider + " doesn't exist");
			}
			oldParkingProvider.setName(parkingProvider.getName());
			oldParkingProvider.setNickName(parkingProvider.getNickName());
			oldParkingProvider.setPassword(parkingProvider.getPassword());
			oldParkingProvider.setEmail(parkingProvider.getEmail());
			oldParkingProvider.setAccountNumber(parkingProvider.getAccountNumber());
			parkingProviderDAO.save(oldParkingProvider);
			return oldParkingProvider;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * /ParkingProviders/allParkingProviders -> return all the ParkingProviders..
	 * @return a empty list.
	 */
	@ResponseBody
	@GetMapping("/allparkingproviders")
	public List<ParkingProvider> getParkingProvider() {
		try {
			List<ParkingProvider> parkingProviders = parkingProviderDAO.getAllParkingProvider();
			return parkingProviders;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * /parkingproviders/delete/{id} -> delete the parking provider user having thepassed id.
	 * @param id - The id for the parkingprovider to delete
	 * @return A string describing if the person is succesfully deleted or not.
	 */
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public String deleteParkingProvider(@PathVariable("id") long id) {
		try {
			if (parkingProviderDAO.findParkingProviderByID(id)==null) {
				throw new Exception("The Parking provider with id:  " + id + " doesn't exist");
			}
			parkingProviderDAO.delete(new ParkingProvider(id));
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Error deleting the Parking Provider with id: " + id;
		}
		return "Parking Provider with id: "+ id +" succesfully deleted!";
	}
	
	//PARKING 
	@PostMapping("/create/{id}/parkings")
	@ResponseBody
	public String createParkingProviderParking(@PathVariable("id") long id, @RequestBody @Valid Parking parking) 
	{
		try {
			ParkingProvider parkingProvider = parkingProviderDAO.findParkingProviderByID(id);
			if (parkingProvider==null) {
				throw new Exception("The Parking provider with id:  " + id + " doesn't exist");
			}
			parking.setParkingProvider(parkingProvider);
			parkingDao.save(parking);
		}catch(Exception ex) {
			return "Error creating parking provdier  related with the parking  with id: " + id;
		}
		return "Parking  with id "+parking.getId() + " succesfully created linked to Parking Provider with id: " + id;
	}
	
	
	@GetMapping("/getbyid/{idPV}/parking/{idP}")
	@ResponseBody
	public Parking findByIDParkingParkingProvider(@PathVariable("idPV") long idPV, @PathVariable("idP") long idP) {
		try {
			ParkingProvider parkingProvider = parkingProviderDAO.findParkingProviderByID(idPV);
			if (parkingProvider==null) {
				throw new Exception("The Parking provider with id:  " + idPV + " doesn't exist");
			}
			Parking parking = parkingDao.findParkingsById(idP);
			if (parking==null) {
				throw new Exception("The Parking with id:  " + idP + " doesn't exist");
			}
			if(parking.getParkingProvider().getId() != parkingProvider.getId()) {
				throw new Exception("The Parking provider and parking associated  doesn't exist");
			}
			return parking;
		} catch (Exception e) {
			return null;
		}
	}
	

	@GetMapping("/{idP}/parkings")
	@ResponseBody
	public List<Parking> findParkingParkingprovider(@PathVariable("idP") long idP){
		try {
			ParkingProvider parkingProvider = parkingProviderDAO.findParkingProviderByID(idP);
			if (parkingProvider==null) {
				throw new Exception("The Parking Consumer with id:  " + idP + " doesn't exist");
			}
			return parkingProvider.getParkings();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	@PutMapping("/update/{idPV}/parkings/{idP}")
	@ResponseBody
	public Parking updateParkingParkingProvider(@PathVariable("idPV") long idPV, @RequestBody @Valid Parking parking) {
		try {
			ParkingProvider parkingProvider = parkingProviderDAO.findParkingProviderByID(idPV);
			if (parkingProvider==null) {
				throw new Exception("The Parking provider with id:  " + idPV + " doesn't exist");
			}
			Parking oldParking = parkingDao.findParkingsById(parking.getId());
			if (oldParking==null) {
				throw new Exception("The Parking  with id:  " + parking.getId() + " doesn't exist");
			}
			oldParking.setAddress(parking.getAddress());
			oldParking.setName(parking.getName());
			oldParking.setParkingProvider(parkingProvider);
			parking.setParkingProvider(parkingProvider);
			parkingDao.save(oldParking);
			return oldParking;
		} catch (Exception e) {
			return null;
		}
	}
	
	@DeleteMapping("/delete/{idPV}/parkings/{idP}")
	@ResponseBody
	public String deleteParkingParkingProvider(@PathVariable("idPV") long idPV, @PathVariable("idP") long idP) {
		try {
			ParkingProvider parkingProvider= parkingProviderDAO.findParkingProviderByID(idPV);
			if (parkingProvider==null) {
				throw new Exception("The Parking provider with id:  " + idPV + " doesn't exist");
			}
			Parking parking= parkingDao.findParkingsById(idP);
			if (parking==null) {
				throw new Exception("The Parking  with id:  " + idP + " doesn't exist");
			}
			parking.setParkingProvider(parkingProvider);
			parkingDao.delete(parking);
		} catch (Exception e) {
			return "Error deleting the parking :" + e.toString();
		}
		return "parking  with id "+ idP +"succesfully deleted!";
	}
	
	//PARKING SPOT//
	
	/**
	 * Get all the parking spots of a parking provider
	 * @param id of the parking provider
	 * @param parkingSpot parking spot that  want to be created
	 * @return the information if it was created or not
	 */
	@PostMapping("/create/{id}/parkingspots")
	@ResponseBody
	public String createParkingSpotParkingProvider(@PathVariable("id") long id, @RequestBody @Valid ParkingSpot parkingSpot) 
	{
		try {
			ParkingProvider parkingProvider = parkingProviderDAO.findParkingProviderByID(id);
			if (parkingProvider==null) {
				throw new Exception("The Parking provider with id:  " + id + " doesn't exist");
			}
			parkingSpot.setParkingProvider(parkingProvider);
			parkingSpotDAO.save(parkingSpot);
		}catch(Exception ex) {
			return "Error creating parking spot  related with the parking provider  with id: " + id;
		}
		return "Parking spot with id "+parkingSpot.getId() + " succesfully created linked to Parking provider  with id: " + id;
	}
	
	
	/**
	 * Get a specific parking spot from a specific parking provider
	 * @param id id of the parking provider
	 * @param idP id of the parking spot
	 * @return
	 */
	@GetMapping("/getbyid/{id}/parkingspots/{idP}")
	@ResponseBody
	public ParkingSpot findByIDParkingSpotParkingProvider(@PathVariable("id") long id, @PathVariable("idP") long idP) {
		try {
			ParkingProvider parkingProvider = parkingProviderDAO.findParkingProviderByID(id);
			if (parkingProvider==null) {
				throw new Exception("The Parking provider with id:  " + id + " doesn't exist");
			}
			ParkingSpot parkingSpot = parkingSpotDAO.findParkingSpotById(idP);
			if (parkingSpot==null) {
				throw new Exception("The Parking spot with id:  " + idP + " doesn't exist");
			}
			if(parkingSpot.getParkingProvider().getId() != parkingProvider.getId()) {
				throw new Exception("The Parking provider and the paring spot doesn't exist");
			}
			return parkingSpot;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Get the parkings spots of a specific parking provider
	 * @param id id of the parking provider
	 * @return the lsit of parking spots of the parking provider
	 */
	@GetMapping("/{id}/parkingspots")
	@ResponseBody
	public List<ParkingSpot> findParkingSpotParkingprovider(@PathVariable("id") long id){
		try {
			ParkingProvider parkingProvider = parkingProviderDAO.findParkingProviderByID(id);
			if (parkingProvider==null) {
				throw new Exception("The Parking provider with id:  " + id + " doesn't exist");
			}
			return parkingProvider.getParkingspots();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	/**
	 * Update the parking spot of a certain parking provider
	 * @param id of the parking provider
	 * @param parkingSpot the parking spot you want to update
	 * @return the parking spot updated
	 */
	@PutMapping("/update/{id}/parkingspots/{idP}")
	@ResponseBody
	public ParkingSpot updateParkingSpotParkingProvider(@PathVariable("id") long id, @RequestBody @Valid ParkingSpot parkingSpot) {
		try {
			ParkingProvider parkingProvider = parkingProviderDAO.findParkingProviderByID(id);
			if (parkingProvider==null) {
				throw new Exception("The Parking provider with id:  " + id + " doesn't exist");
			}
			ParkingSpot oldParkingSpot = parkingSpotDAO.findParkingSpotById(parkingSpot.getId());
			if (oldParkingSpot==null) {
				throw new Exception("The Parking spot with id:  " + parkingSpot.getId() + " doesn't exist");
			}
			oldParkingSpot.setNumber(parkingSpot.getNumber());
			oldParkingSpot.setParkingProvider(parkingProvider);
			parkingSpotDAO.save(oldParkingSpot);
			return oldParkingSpot;
		} catch (Exception e) {
			return null;
		}
	}
	

	/**
	 * Delete a specific parking spot of a specific parking provider
	 * @param id of the parking provider
	 * @param idP of the parking spot
	 * @return the information if it was  succesfully deleted or not
	 */
	@DeleteMapping("/delete/{id}/parkingspots/{idP}")
	@ResponseBody
	public String deleteParkingSpotParkingProvider(@PathVariable("id") long id, @PathVariable("idP") long idP) {
		try {
			ParkingProvider parkingProvider = parkingProviderDAO.findParkingProviderByID(id);
			if (parkingProvider==null) {
				throw new Exception("The Parking provider with id:  " + id + " doesn't exist");
			}
			ParkingSpot parkingSpot = parkingSpotDAO.findParkingSpotById(idP);
			if (parkingSpot==null) {
				throw new Exception("The Parking spot with id:  " + idP + " doesn't exist");
			}
			parkingSpot.setParkingProvider(parkingProvider);
			parkingSpotDAO.delete(parkingSpot);
		} catch (Exception e) {
			return "Error deleting the parking spot:" + e.toString();
		}
		return "parking spot with id "+ idP +"succesfully deleted!";
	}
	
								//BOOKING
	/**
	 * Create a booking associated to a parking provider
	 * @param id
	 * @param parkingProvider
	 * @return
	 */
	@PostMapping("/create/{id}/bookings")
	@ResponseBody
	public String createBookingParkingProvider(@PathVariable("id") long id, @RequestBody @Valid Booking booking ) {
		try {
			ParkingProvider parkingProvider = parkingProviderDAO.findParkingProviderByID(id);
			if (parkingProvider==null) {
				throw new Exception("The Parking provider with id:  " + id + " doesn't exist");
			}
			booking.setParkingProvider(parkingProvider);
			bookingDAO.save(booking);
			
		}catch(Exception ex) {
			return "Error creating booking related with the parking provider with id: " + id;
		}
		return "booking succesfully created with id: "+ booking.getId()+" linked to parking provider with id: " + id;
	}
	
	
	/**
	 * Get a specific booking from a specific parking provider
	 * @param id id of the parking provider
	 * @param idB id of the booking
	 * @return
	 */
	@GetMapping("/getById/{id}/bookings/{idB}")
	@ResponseBody
	public Booking findByIDParkingProviderBooking(@PathVariable("id") long id, @PathVariable("idB") long idB) {
		try {
			ParkingProvider parkingProvider = parkingProviderDAO.findParkingProviderByID(id);
			if (parkingProvider==null) {
				throw new Exception("The Parking provider with id:  " + id + " doesn't exist");
			}
			Booking booking = bookingDAO.findBookingByID(idB);
			if (booking==null) {
				throw new Exception("The booking with id:  " + idB + " doesn't exist");
			}
			if(booking.getParkingProvider().getId() != parkingProvider.getId()) {
				throw new Exception("The Parking provider and the booking doesn't exist");
			}
			return booking;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Get the parkings spots of a specific parking provider
	 * @param id id of the parking provider
	 * @return the list of bookings of the parking provider
	 */
	@GetMapping("/{id}/allbookings")
	@ResponseBody
	public List<Booking> findParkingProviderBooking(@PathVariable("id") long id){
		try {
			ParkingProvider parkingProvider = parkingProviderDAO.findParkingProviderByID(id);
			if (parkingProvider==null) {
				throw new Exception("The Parking provider with id:  " + id + " doesn't exist");
			}
			return parkingProvider.getBookings();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	
	/**
	 * Update the booking of a certain parking provider
	 * @param id of the parking provider
	 * @param booking the booking
	 * @return the booking updated
	 */
	@PutMapping("/update/{id}/bookings/{idB}")
	@ResponseBody
	public Booking updateParkingProviderBooking(@PathVariable("id") long id, @RequestBody @Valid Booking booking) {
		try {
			ParkingProvider parkingProvider = parkingProviderDAO.findParkingProviderByID(id);
			if (parkingProvider==null) {
				throw new Exception("The Parking provider with id:  " + id + " doesn't exist");
			}
			Booking oldBooking = bookingDAO.findBookingByID(booking.getId());
			if (oldBooking==null) {
				throw new Exception("The booking with id:  " + booking.getId() + " doesn't exist");
			}
			oldBooking.setBeginDate(booking.getBeginDate());
			oldBooking.setEndDate(booking.getEndDate());
			oldBooking.setParkingProvider(parkingProvider);
			bookingDAO.save(oldBooking);
			return oldBooking;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Delete a specific booking  of a specific parking provider
	 * @param id of the parking provider
	 * @param idB id of the booking
	 * @return the information if it was  succesfully deleted or not
	 */
	@DeleteMapping("/delete/{id}/bookings/{idB}")
	@ResponseBody
	public String deleteParkingProviderBooking(@PathVariable("id") long id, @PathVariable("idB") long idB) {
		try {
			ParkingProvider parkingProvider = parkingProviderDAO.findParkingProviderByID(id);
			if (parkingProvider==null) {
				throw new Exception("The Parking provider with id:  " + id + " doesn't exist");
			}
			Booking booking = bookingDAO.findBookingByID(idB);
			if (booking==null) {
				throw new Exception("The booking with id:  " + idB + " doesn't exist");
			}
			booking.setParkingProvider(parkingProvider);
			bookingDAO.delete(booking);
		} catch (Exception e) {
			return "Error deleting the booking:" + e.toString();
		}
		return "Booking with id "+ idB +"succesfully deleted!";
	}
}
