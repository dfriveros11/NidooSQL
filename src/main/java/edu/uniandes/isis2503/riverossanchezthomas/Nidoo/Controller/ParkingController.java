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
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO.GuardDAO;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO.ParkingDAO;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO.ParkingSpotDAO;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Guard;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Parking;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.ParkingProvider;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.ParkingSpot;

/**
 * @author TDJ
 */
@Controller
@RequestMapping("/parkings")
public class ParkingController {

	/**
	 * Connection with the DAO of parking 
	 */
	@Autowired
	private ParkingDAO parkingDao;
	
	/**
	 * Connection with the DAO of parking spot
	 */
	@Autowired
	private ParkingSpotDAO parkingSpotDao;
	
	/**
	 * Connection with the DAO of guard
	 */
	@Autowired
	private GuardDAO guardDao;
	
	/**
	 * /parkings/create ->  create a new parking  and save it in the database. 
	 * @return a string describing if the parking is succesfully created or not.
	 */
	@PostMapping("/create")
	@ResponseBody
	public String createParking(@RequestBody @Valid Parking parking)
	{
		try {
			parkingDao.save(parking);
		} catch (Exception ex) {
			return "Error creating the Parking  : " + ex.toString();
		}
		return "Parking  identified with id "+ parking.getId()+ " was succesfully created" ;
	}



	/**
	 * /update/parkings -> updated the parking with passed
	 * @return the parking  if the parking  is succesfully updated 
	 */
	@PutMapping("/update")
	@ResponseBody
	public Parking updateParking(@RequestBody @Valid Parking parking) {
		try {
			Parking oldParking = parkingDao.findParkingsById(parking.getId());
			if (oldParking==null) {
				throw new Exception("The parking with id: " + parking.getId()+ " doesn´t exist in our database" );
			}
			oldParking.setName(parking.getName());
			oldParking.setAddress(parking.getAddress());
			parkingDao.save(oldParking);
			return oldParking;
		} catch (Exception ex) {
			ex.toString();
			return null;
		}
	}

	/**
	 * /parkings/getbyid/{id} -> return the parking with id.
	 * @param id The id to search in the database.
	 * @return The parking  or a message error if the parking  is not found.
	 */
	@GetMapping("/getbyid/{id}")
	@ResponseBody
	public Parking getParking(@PathVariable("id") long id) {
		try {
			Parking parking = parkingDao.findParkingsById(id);
			if (parking==null) {
				throw new Exception("The Parking with id: " + id+ " doesn´t exist in our database" );
			}
			return parking;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * /parking/allparkings -> return all the parkings..
	 * @return a empty list.
	 */
	@ResponseBody
	@GetMapping("/allparkings")
	public List<Parking> getAllParkings() {
		try {
			List<Parking> parkings = parkingDao.getAllParkings();
			return parkings;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * /parkings/delete/{id} -> delete the parking with id.
	 * @param id The id for the parking  to delete
	 * @return A string describing if the parking  is succesfully deleted or not.
	 */
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public String deleteParking(@PathVariable("id") long id) {
		try {
			if (parkingDao.findParkingsById(id)==null) {
			throw new Exception("The Parking with id: " + id+ " doesn´t exist in our database" );	
			}
			parkingDao.delete(new Parking(id));
		} catch (Exception ex) {
			return "Error deleting the parking :" + ex.toString();
		}
		return "the parking  identified with id :" +id +" was succesfully deleted!";
	}

	
	
										// PARKING SPOT//
	/**
	 * Create a new parking spot associated to the parking with id
	 * @param id of the parking 
	 * @param parkingspot the new parking spot that  want´s to be created and related
	 * @return a string with the information if it was created or not
	 */
	@PostMapping("/create/{id}/parkingspots")
	@ResponseBody
	public String createParkingParkingSpot(@PathVariable("id") long id, @RequestBody @Valid ParkingSpot parkingspot) 
	{
		try {
			Parking parking = parkingDao.findParkingsById(id);
			if (parkingDao.findParkingsById(id)==null) {
				throw new Exception("The Parking with id: " + id+ " doesn´t exist in our database" );
				
			}
			parkingspot.setParking(parking);
			parkingSpotDao.save(parkingspot);
		}catch(Exception ex) {
			return "Error creating parking spot  related with the parking  with id: " + id;
		}
		return "Parking spot with id "+parkingspot.getId() + " succesfully created linked to Parking  with id: " + id;
	}
	
	/**
	 * Get the parking spot with idPS associated to the parking with id idP
	 * @param idP id of the parking 
	 * @param idPS id of the parking spot
	 * @return the  parking spot with that
	 */
	@GetMapping("/getbyid/{idP}/parkingspots/{idPS}")
	@ResponseBody
	public ParkingSpot findByIDParkingParkingSpot(@PathVariable("idP") long idP, @PathVariable("idPS") long idPS) {
		try {
			Parking parking = parkingDao.findParkingsById(idP);
			if (parkingDao.findParkingsById(idP)==null) {
				throw new Exception("The Parking with id: " + idP+ " doesn´t exist in our database" );
				
			}
			ParkingSpot parkingSpot = parkingSpotDao.findParkingSpotById(idPS);
			if (parkingSpot==null) {
				throw new Exception("The Parking spot with id: " + idPS+ " doesn´t exist in our database" );
			}
			if(parkingSpot.getParking().getId() != parking.getId()) {
				throw new Exception("it doesnt exist a parking with the same id  that parkingSpot.getParking" );
				
			}
			return parkingSpot;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Get all the  parking spots of the parking with id 
	 * @param idP id of the parking
	 * @return list of parking spots associated to the parking
	 */
	@GetMapping("/all/{idP}/parkingspots")
	@ResponseBody
	public List<ParkingSpot> findParkingParkingSpots(@PathVariable("idP") long idP){
		try {
			Parking parking = parkingDao.findParkingsById(idP);
			if (parking==null) {
				throw new Exception("The Parking with id: " + idP+ " doesn´t exist in our database" );
			}
			return parking.getParkingspots();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Update the information of the parking spot with idPS of the parking with idP
	 * @param idP id of the parking
	 * @param parkingSpot the new information of the parking spot
	 * @return the parking spot updated
	 */
	@PutMapping("/update/{idP}/parkingspots/{idPS}")
	@ResponseBody
	public ParkingSpot updateParkingParkingSpot(@PathVariable("idP") long idP, @RequestBody @Valid ParkingSpot parkingSpot) {
		try {
			Parking parking = parkingDao.findParkingsById(idP);
			if (parking==null) {
				throw new Exception("The Parking with id: " + idP+ " doesn´t exist in our database" );
			}
			ParkingSpot oldparkingSpot = parkingSpotDao.findParkingSpotById(parkingSpot.getId());
			if (oldparkingSpot==null) {
				throw new Exception("The Parking spot with id: " + parkingSpot.getId()+ " doesn´t exist in our database" );
			}
			oldparkingSpot.setNumber(parkingSpot.getNumber());
			oldparkingSpot.setParking(parking);
			parkingSpotDao.save(oldparkingSpot);
			return oldparkingSpot;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Delete a parking spot with idPS of a parking with idP
	 * @param idP of the parking
	 * @param idPS of the parking spot
	 * @return a string with the information if it was succesfully deleted or not
	 */
	@DeleteMapping("/delete/{idP}/parkingspots/{idPS}")
	@ResponseBody
	public String deleteParkingParkingSpot(@PathVariable("idP") long idP, @PathVariable("idPS") long idPS) {
		try {
			Parking parking = parkingDao.findParkingsById(idP);
			if (parking==null) {
				throw new Exception("The Parking with id: " + idP+ " doesn´t exist in our database" );
			}
			ParkingSpot parkingSpot = parkingSpotDao.findParkingSpotById(idPS);
			if (parkingSpot==null) {
				throw new Exception("The Parking Spot with id: " + idPS+ " doesn´t exist in our database" );
				
			}
			parkingSpot.setParking(parking);
			parkingSpotDao.delete(parkingSpot);
		} catch (Exception e) {
			return "Error deleting the parking spot:" + e.toString();
		}
		return "parking spot with id "+ idPS +"succesfully deleted!";
	}
	
										//PARKING PROVIDER//
	
	/**
	 * Find the parking provider  of a parking with idP
	 * @param idP of the parking
	 * @return the parking provider  associated to the parking with idP
	 */
	@GetMapping("/{idP}/parkingprovider")
	@ResponseBody
	public ParkingProvider findParkingParkingprovider(@PathVariable("idP") long idP){
		try {
			Parking parking = parkingDao.findParkingsById(idP);
			if (parking==null) {
				throw new Exception("The Parking with id: " + idP+ " doesn´t exist in our database" );
			}
			return parking.getParkingProvider();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	
									//PARKING GUARD//
	
	/**
	 * create a new guard associated to a parking
	 * @param id of the parking
	 * @param guard the new guard  to be related
	 * @return the information if it was created or not
	 */
	@PostMapping("/create/{id}/guards")
	@ResponseBody
	public String createParkingGuard(@PathVariable("id") long id, @RequestBody @Valid Guard guard) 
	{
		try {
			Parking parking = parkingDao.findParkingsById(id);
			if (parking==null) {
				throw new Exception("The Parking with id: " + id+ " doesn´t exist in our database" );	
			}
			guard.setParking(parking);
			guardDao.save(guard);
		}catch(Exception ex) {
			return "Error creating guard  related with the parking  with id: " + id;
		}
		return "Parking guard with id "+guard.getId() + " succesfully created linked to Parking  with id: " + id;
	}
	
	/**
	 * Get one guard in specific from a parking
	 * @param idP id of the parking
	 * @param idG id of the guard
	 * @return the guard  with the id requested
	 */
	@GetMapping("/getbyid/{idP}/guards/{idG}")
	@ResponseBody
	public Guard findByIDParkingGuard(@PathVariable("idP") long idP, @PathVariable("idG") long idG) {
		try {
			Parking parking = parkingDao.findParkingsById(idP);
			if (parking==null) {
				throw new Exception("The Parking with id: " + idP+ " doesn´t exist in our database" );
			}
			Guard guard = guardDao.findByIdGuard(idG);
			if (guard==null) {
				throw new Exception("The Guard with id: " + idG+ " doesn´t exist in our database" );
				
			}
			if(guard.getParking().getId() != parking.getId()) {
				throw new Exception("The Parking  has not guard  with the same id" );
			}
			return guard;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Get all the guards of the parking
	 * @param idP id of the parking
	 * @return the list of guards
	 */
	@GetMapping("/all/{idP}/guards")
	@ResponseBody
	public List<Guard> findParkingGuards(@PathVariable("idP") long idP){
		try {
			Parking parking = parkingDao.findParkingsById(idP);
			if (parking==null) {
			throw new Exception("The Parking with id: " + idP+ " doesn´t exist in our database" );

			}
			return parking.getGuards();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	/**
	 * Update the specific guard of  ona parking
	 * @param idP id of the parking
	 * @param guard the new  values for the guard
	 * @return the guard  updated
	 */
	@PutMapping("/update/{idP}/guards/{idG}")
	@ResponseBody
	public Guard updateParkingGuard(@PathVariable("idP") long idP, @RequestBody @Valid Guard guard) {
		try {
			Parking parking = parkingDao.findParkingsById(idP);
			if (parking==null) {
				throw new Exception("The Parking with id: " + idP+ " doesn´t exist in our database" );
			}
			Guard oldGuard = guardDao.findByIdGuard(guard.getId());
			if (oldGuard==null) {
				throw new Exception("The Guard with id: " + guard.getId()+ " doesn´t exist in our database" );
				
			}
			oldGuard.setEmail(guard.getEmail());
			oldGuard.setPhone(guard.getPhone());
			oldGuard.setParking(parking);
			guardDao.save(oldGuard);
			return oldGuard;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Delete a specific guard of a parking
	 * @param idP id of the parking
	 * @param idG id of the guard
	 * @return a string with the information if it was  deleted or not
	 */
	@DeleteMapping("/delete/{idP}/guards/{idG}")
	@ResponseBody
	public String deleteParkingGuard(@PathVariable("idP") long idP, @PathVariable("idG") long idG) {
		try {
			Parking parking = parkingDao.findParkingsById(idP);
			if (parking==null) {
				throw new Exception("The Parking with id: " + idP+ " doesn´t exist in our database" );
				
			}
			Guard guard= guardDao.findByIdGuard(idG);
			if (guard==null) {
				throw new Exception("The Guard with id: " + idG+ " doesn´t exist in our database" );
			}
			guard.setParking(parking);
			guardDao.delete(guard);
		} catch (Exception e) {
			return "Error deleting the parking guard:" + e.toString();
		}
		return "parking guard with id "+ idG +"succesfully deleted!";
	} 
}
