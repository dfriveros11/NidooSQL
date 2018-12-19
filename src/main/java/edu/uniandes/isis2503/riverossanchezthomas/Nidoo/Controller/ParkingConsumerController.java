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

import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO.ParkingConsumerDAO;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO.VehicleDAO;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.CorporationConsumer;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.ParkingConsumer;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Vehicle;

/**
 * @author TDJ
 */

@Controller
@RequestMapping("/parkingconsumers")
public class ParkingConsumerController extends UserController {

	/**
	 * Connectio to the DAO of parking consumer
	 */
	@Autowired
	private ParkingConsumerDAO parkingConsumerDAO;
	
	/**
	 * Connection to the DAO of vehicle
	 */
	@Autowired
	private VehicleDAO vehicleDAO;
	
	/**
	 * /parkingconsumers/create -> create a new parking consumer user and save it in the database.
	 * @return a string describing if the person is succesfully created or not.
	 */
	@PostMapping("/create")
	@ResponseBody
	public String createParkingConsumer(@RequestBody @Valid ParkingConsumer parkingConsumer) {
		try {
			parkingConsumerDAO.save(parkingConsumer);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Error creating the ParkingConsumer with id: " + parkingConsumer.getId();
		}
		return "Parking Consumer with id: "+ parkingConsumer.getId() + " succesfully created!";
	}

	
	/**
	 * /user/update/parkingconsumer -> updated the parkingconsumer with passed
	 * @return A parkingconsumer describing if the user is succesfully updated or not.
	 */
	@PutMapping("/update")
	@ResponseBody
	public ParkingConsumer updateParkingConsumer(@RequestBody @Valid ParkingConsumer parkingConsumer) {
		try {
			
			long idParkingConsumer = parkingConsumer.getId();
			ParkingConsumer oldParkingConsumer = parkingConsumerDAO.findParkingConsumerByID(idParkingConsumer);
			
			if(oldParkingConsumer == null) throw new Exception("The Parking Consumer with id:  " + idParkingConsumer + " doesn't exist");		
			
			oldParkingConsumer.setName(parkingConsumer.getName());
			oldParkingConsumer.setNickName(parkingConsumer.getNickName());
			oldParkingConsumer.setPassword(parkingConsumer.getPassword());
			oldParkingConsumer.setEmail(parkingConsumer.getEmail());
			oldParkingConsumer.setCreditCard(parkingConsumer.getCreditCard());
			
			parkingConsumerDAO.save(oldParkingConsumer);
			return oldParkingConsumer;
		} catch (Exception ex) {
			ex.printStackTrace();;
			return null;
		}
	}
	
	/**
	 * /parkingconsumers/allParkingConsumers -> return all the ParkingConsumers..
	 * @return a empty list.
	 */
	@ResponseBody
	@GetMapping("/allparkingconsumers")
	public List<ParkingConsumer> getParkingConsumer() {
		try {
			List<ParkingConsumer> parkingConsumers = parkingConsumerDAO.getAllParkingConsumer();
			return parkingConsumers;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * /parkingconsumers/delete/{id} -> delete the parkingconsumer user having thepassed id.
	 * @param id The id for the person to delete
	 * @return A string describing if the parkingconsumer is succesfully deleted or not.
	 */
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public String deleteParkingConsumer(@PathVariable("id") long id) {
		try {
			ParkingConsumer parkingConsumer = parkingConsumerDAO.findParkingConsumerByID(id);
			if (parkingConsumer==null) {
				throw new Exception("The Parking Consumer with id:  " + id + " doesn't exist");		
			}
			parkingConsumerDAO.delete(new ParkingConsumer(id));
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Error deleting the Parking Consumer with id:" + id;
		}
		return "Parking Consumer with id: " + id + " succesfully deleted!";
	}
	
	/* Start the methods for relationships*/
	
	@PostMapping("/create/{id}/vehicles")
	@ResponseBody
	public String createParkingConsumerVehicle(@PathVariable("id") long id, @RequestBody @Valid Vehicle vehicle) {
		try {
			ParkingConsumer parkingConsumer = parkingConsumerDAO.findParkingConsumerByID(id);
			
			if(parkingConsumer == null) throw new Exception("The Parking Consumer with id:  " + id + " doesn't exist");
			
			vehicle.setParkingConsumer(parkingConsumer);
			vehicleDAO.save(vehicle);
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return ex.toString();
		}
		return "Vehicle with id: " + vehicle.getId() + " succesfully created! linked to Parking Consumer with id: " + id;
	}
	
	
	@GetMapping("/getbyid/{idP}/vehicles/{idV}")
	@ResponseBody
	public Vehicle findParkingConsumerVehicle(@PathVariable("idP") long idP, @PathVariable("idV") long idV) {
		try {
			ParkingConsumer parkingConsumer = parkingConsumerDAO.findParkingConsumerByID(idP);
			
			if(parkingConsumer == null) throw new Exception("The Parking Consumer with id:  " + idP + " doesn't exist");
			
			Vehicle vehicle = vehicleDAO.findVehicleByID(idV);
			
			if(vehicle == null) throw new Exception("The Vehicle with id:  " + idV + " doesn't exist");
			
			long idVehicleParkingConsumer = vehicle.getParkingConsumer().getId();
			long idParkingConsumer = parkingConsumer.getId();
			if(Long.compare(idVehicleParkingConsumer, idParkingConsumer) != 0) {
				throw new Exception("The Vehicle with id:  " + idV + " doesn't have a connection with Parking Consumer with id: " + idP);
			}
			return vehicle;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping("/all/{idP}/vehicles")
	@ResponseBody
	public List<Vehicle> findParkingConsumerVehicles(@PathVariable("idP") long idP){
		try {
			ParkingConsumer parkingConsumer = parkingConsumerDAO.findParkingConsumerByID(idP);
			
			if(parkingConsumer == null) throw new Exception("The Parking Consumer with id:  " + idP + "doesn´t exist");
			
			return parkingConsumer.getVehicles();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@PutMapping("/update/{idP}/vehicles/{idV}")
	@ResponseBody
	public Vehicle updateParkingConsumerVehicle(@PathVariable("idP") long idP, @RequestBody @Valid Vehicle vehicle) {
		try {
			ParkingConsumer parkingConsumer = parkingConsumerDAO.findParkingConsumerByID(idP);
			
			if(parkingConsumer == null) throw new Exception("The Parking Consumer with id:  " + idP + "doesn´t exist");
			
			Vehicle oldVehicle = vehicleDAO.findVehicleByID(vehicle.getId());
			
			if(oldVehicle == null) throw new Exception("The Vehicle with id:  " + vehicle.getId() + " doesn't exist");

			oldVehicle.setLincensePlate(vehicle.getLincensePlate());
			oldVehicle.setCarModel(vehicle.getCarModel());
			oldVehicle.setCarBrand(vehicle.getCarBrand());
			oldVehicle.setParkingConsumer(parkingConsumer);
			vehicleDAO.save(oldVehicle);
			return oldVehicle;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@DeleteMapping("/delete/{idP}/vehicles/{idV}")
	@ResponseBody
	public String deleteParkingConsumerVehicle(@PathVariable("idP") long idP, @PathVariable("idV") long idV) {
		try {
			ParkingConsumer parkingConsumer = parkingConsumerDAO.findParkingConsumerByID(idP);
			
			if(parkingConsumer == null) throw new Exception("The Parking Consumer with id:  " + idP + "doesn´t exist");
			
			Vehicle vehicle = vehicleDAO.findVehicleByID(idV);
			
			if(vehicle == null) throw new Exception("The Vehicle with id:  " + idV + " doesn't exist");
			
			
			long idVehicleParkingConsumer = vehicle.getParkingConsumer().getId();
			long idParkingConsumer = parkingConsumer.getId();
			if(Long.compare(idVehicleParkingConsumer, idParkingConsumer) != 0) {
				throw new Exception("The Vehicle with id:  " + vehicle.getId() + " doesn't have a connection with Parking Consumer with id: " + idP);
			}
			
			vehicle.setParkingConsumer(parkingConsumer);
			vehicleDAO.delete(vehicle);
		} catch (Exception e) {
			e.printStackTrace();
			return "Error deleting the Vehicle with id:" + idV + " with connection with Parking Consumer with id: " + idP;
		}
		return "Vehicle with id: " + idV + " with connection with Parking Consumer with id: " + idP + " succesfully deleted!";
	}
	
	/**
	 * Get  the corporative consumer associated to the  parking consumer with id
	 * @param id id of the parking consumer 
	 * @exception the parking consumer with id doesn´t exist in our  data base
	 * @return the corporative consumer that are related to the parking consumer
	 */
	@GetMapping("/{id}/corporativeconsumers")
	@ResponseBody
	public CorporationConsumer findCorporativeConsumersParkingConsumers(@PathVariable("id") long id){
		try {
			ParkingConsumer parkingConsumer = parkingConsumerDAO.findParkingConsumerByID(id);
			if (parkingConsumer==null) {
				throw new Exception("The parking Consumer with id:  " + id + "doesn´t exist");
			}
			return parkingConsumer.getCorporativeConsumer();
		} catch (Exception e) {
			return null;
		}
		
	}
	
}
