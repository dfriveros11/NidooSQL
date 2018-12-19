package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Controller;


import java.util.LinkedList;
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
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO.VehicleDAO;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Booking;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Vehicle;
/**
 * @author TDJ
 */
@Controller
@RequestMapping("/vehicles")
public class VehicleController {
	@Autowired
	private VehicleDAO vehicleDAO;
	
	@Autowired
	private BookingDAO bookingDAO;
	
	/**
	 * /vehicles/create ->  create a new vehicles user and save it in the database.
	 * 
	 * @return a string describing if the person is succesfully created or not.
	 */
	@PostMapping("/create")
	@ResponseBody
	public String createVehicle(@RequestBody @Valid Vehicle vehicle) {
		try {
			vehicleDAO.save(vehicle);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Error creating the Vehicle with id: " + vehicle.getId();
		}
		return "Vehicle with id: " + vehicle.getId() +" succesfully created!";
	}
	
	/**
	 * /vehicles/update -> updated the vehicles with passed
	 * 
	 * @return A string describing if the user is succesfully updated or not.
	 */
	@PutMapping("/update")
	@ResponseBody
	public Vehicle updateVehicle(@RequestBody @Valid Vehicle vehicle) {
		try {
			
			Vehicle oldVehicle = vehicleDAO.findVehicleByID(vehicle.getId());
			
			if(oldVehicle == null) throw new Exception("The Vehicle with id:  " + vehicle.getId() + " doesn't exist");
			
			oldVehicle.setLincensePlate(vehicle.getLincensePlate());
			oldVehicle.setCarModel(vehicle.getCarModel());
			oldVehicle.setCarBrand(vehicle.getCarBrand());
			
			vehicleDAO.save(oldVehicle);
			return oldVehicle;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * /vehicles/getbyid/{id} -> return the user having the passed id.
	 * 
	 * @param id
	 *            The id to search in the database.
	 * @return The user or a message error if the user is not found.
	 */
	@GetMapping("/getbyid/{id}")
	@ResponseBody
	public Vehicle getVehicleID(@PathVariable("id") long id) {
		try {
			Vehicle vehicle = vehicleDAO.findVehicleByID(id);
			
			if(vehicle == null) throw new Exception("The Vehicle with id:  " + id + " doesn't exist");

			return vehicle;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * /vehicles/allvehicless -> return all the vehicles..
	 * @return a empty list.
	 */
	@ResponseBody
	@GetMapping("/allvehicles")
	public List<Vehicle> getVehicles() {
		try {
			List<Vehicle> vehicles = vehicleDAO.getAllVehicle();
			return vehicles;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	
	/**
	 * /vehicles/delete/{id} -> delete the vehicles user having the
	 * passed id.
	 * 
	 * @param id
	 *            The id for the vehicles to delete
	 * @return A string describing if the person is succesfully deleted or not.
	 */
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public String deleteVehicle(@PathVariable("id") long id) {
		try {
			vehicleDAO.delete(new Vehicle(id));
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Error deleting the vehicle with id:" + id;
		}
		return "Vehicle  with id: " + id +" succesfully deleted!";
	}
	
	
	// BOOKING
	
		/**
		 * Create a booking associated to a vechicle
		 * @param id 
		 * @param booking
		 * @return
		 */
		@PostMapping("/create/{id}/bookings")
		@ResponseBody
		public String createBookingVehicle(@PathVariable("id") long id, @RequestBody @Valid Booking booking ) {
			try {
				Vehicle vehicle = vehicleDAO.findVehicleByID(id);
				if (vehicle==null) {
					throw new Exception("The vehicle with id:  " + id + " doesn't exist");
				}
				List<Vehicle> newList=new LinkedList<Vehicle>();
				newList.add(vehicle);
				booking.setVehicles(newList);
				bookingDAO.save(booking);
				vehicle.addOneBooking(booking);
				vehicleDAO.save(vehicle);
			}catch(Exception ex) {
				return "Error creating booking related with the vehicle with id: " + id + ex.toString();
			}
			return "booking succesfully created with id: "+ booking.getId()+" linked to vehicle  with id: " + id;
			
		}
		
		
		/**
		 * Get a specific booking from a specific Vehicle
		 * @param id id of the vehicle
		 * @param idB id of the booking
		 * @return
		 */
		@GetMapping("/getbyid/{id}/bookings/{idB}")
		@ResponseBody
		public Booking findByIDVehicleBooking(@PathVariable("id") long id, @PathVariable("idB") long idB) {
			try {
				Vehicle vehicle = vehicleDAO.findVehicleByID(id);
				if (vehicle==null) {
					throw new Exception("The vehicle with id:  " + id + " doesn't exist");
				}
				Booking booking = bookingDAO.findBookingByID(idB);
				if (booking==null) {
					throw new Exception("The booking with id:  " + idB + " doesn't exist");
				}
				for (int i = 0; i < vehicle.getBookings().size(); i++) {
					Booking actual= vehicle.getBookings().get(i);
					if (actual.getId()==booking.getId()) {
						return booking;
					}
				}
				throw new Exception("The Vehicle and the booking doesn't exist");
				
			} catch (Exception e) {
				return null;
			}
		}
		
		/**
		 * Get the bookings of the vehicle
		 * @param id id of the parking provider
		 * @return the list of bookings of vehicle
		 */
		@GetMapping("/{id}/allbookings")
		@ResponseBody
		public List<Booking> findVehicleBooking(@PathVariable("id") long id){
			try {
				Vehicle vehicle = vehicleDAO.findVehicleByID(id);
				if (vehicle==null) {
					throw new Exception("The vehicle with id:  " + id + " doesn't exist");
				}
				return vehicle.getBookings();
			} catch (Exception e) {
				return null;
			}
			
		}
		
		
		/**
		 * Update the booking of a certain vehicle
		 * @param id of the vehicle
		 * @param booking the booking
		 * @return the booking updated
		 */
		@PutMapping("/update/{id}/bookings/{idB}")
		@ResponseBody
		public Booking updateVehicleBooking(@PathVariable("id") long id, @RequestBody @Valid Booking booking) {
			try {
				Vehicle vehicle = vehicleDAO.findVehicleByID(id);
				if (vehicle==null) {
					throw new Exception("The vehicle with id:  " + id + " doesn't exist");
				}
				Booking oldBooking = bookingDAO.findBookingByID(booking.getId());
				if (oldBooking==null) {
					throw new Exception("The booking with id:  " + booking.getId() + " doesn't exist");
				}
				if (oldBooking.getVehicles().size()==2) {
					throw new Exception("The booking with id:  " + booking.getId() + " have the max capactity of vehicles");
				}
				oldBooking.setBeginDate(booking.getBeginDate());
				oldBooking.setEndDate(booking.getEndDate());
				vehicle.addOneBooking(oldBooking);
				oldBooking.addOneVehicle(vehicle);
				bookingDAO.save(oldBooking);
				return oldBooking;
			} catch (Exception e) {
				return null;
			}
		}
		
		/**
		 * Delete a specific booking  of a specific vehicle
		 * @param id of the vehicle
		 * @param idB id of the booking
		 * @return the information if it was  succesfully deleted or not
		 */
		@DeleteMapping("/delete/{id}/bookings/{idB}")
		@ResponseBody
		public String deleteVehicleBooking(@PathVariable("id") long id, @PathVariable("idB") long idB) {
			try {
				Vehicle vehicle = vehicleDAO.findVehicleByID(id);
				if (vehicle==null) {
					throw new Exception("The vehicle with id:  " + id + " doesn't exist");
				}
				Booking booking = bookingDAO.findBookingByID(idB);
				if (booking==null) {
					throw new Exception("The booking with id:  " + idB + " doesn't exist");
				}
				vehicle.addOneBooking(booking);
				bookingDAO.delete(booking);
			} catch (Exception e) {
				return "Error deleting the booking:" + e.toString();
			}
			return "Booking with id "+ idB +"succesfully deleted!";
		}
	
	
}
