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
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO.CorporationConsumerDAO;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO.ParkingConsumerDAO;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.CorporationConsumer;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.ParkingConsumer;

/**
 * Controlller of the services  of Corporation Consumer
 * @author TDJ
 */
@Controller
@RequestMapping("/corporationconsumer")
public class CorporationConsumerController extends UserController {

	/**
	 * Connection with the DAO of corporation consumer
	 */
	@Autowired
	private CorporationConsumerDAO corporationConsumerDAO;
	
	/**
	 * Connection with the DAO of parking consumer
	 */
	@Autowired
	private ParkingConsumerDAO parkingConsumerDAO;
	
	/**
	 * /corporationconsumer/create ->  create a new corporationconsumer user and save it in the database.
	 * @return a string describing if the person is succesfully created or not.
	 */
	@PostMapping("/create")
	@ResponseBody
	public String createCorporationConsumer(@RequestBody @Valid CorporationConsumer corporationConsumer)
	{
		try {
			corporationConsumerDAO.save(corporationConsumer);
		} catch (Exception ex) {
			return "Error creating the Corporation Consumer: " + ex.toString();
		}
		return "Corporation Consumer identified with id "+ corporationConsumer.getId()+ " was succesfully created" ;
	}
	
	
	/**
	 * /corporationconsumer/update -> updated the corporation consumer with passed 
	 * @return the corporation consumer if it was succesfully updated and  null if it wasn´t updated.
	 */
	@PutMapping("/update")
	@ResponseBody
	public CorporationConsumer updateCorporationConsumer(@RequestBody @Valid CorporationConsumer corporationConsumer) {
		try {
			CorporationConsumer oldCorporationConsumer = corporationConsumerDAO.findCorporationConsumerByID(corporationConsumer.getId());
			if (oldCorporationConsumer==null) {
				throw new Exception("The Corporative consumer with id: " + corporationConsumer.getId()+ " doesn´t exist in our database" );
			}
			oldCorporationConsumer.setName(corporationConsumer.getName());
			oldCorporationConsumer.setNickName(corporationConsumer.getNickName());
			oldCorporationConsumer.setPassword(corporationConsumer.getPassword());
			oldCorporationConsumer.setEmail(corporationConsumer.getEmail());
			oldCorporationConsumer.setRepresentant(corporationConsumer.getRepresentant());
			corporationConsumerDAO.save(oldCorporationConsumer);
			return oldCorporationConsumer;
		} catch (Exception ex) {
			ex.toString();
			return null;
		}
	}

	
	/**
	 * /corporationconsumer/allCorporationConsumers -> return all the CorporationConsumers..
	 * @return a empty list.
	 */
	@ResponseBody
	@GetMapping("/allcorporationconsumers")
	public List<CorporationConsumer> getCorporationConsumer() {
		try {
			List<CorporationConsumer> corporationConsumers = corporationConsumerDAO.getAllCorporationConsumer();
			return corporationConsumers;
		} catch (Exception ex) {
			return null;
		}
	}
	
	
	/**
	 * /corporationconsumer/delete/{id} -> delete the corporation consumer user having the passed id.
	 * @param id The id for the user to delete
	 * @return A string describing if the corporation consumer is succesfully deleted or not.
	 */
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public String deleteCorporationConsumer(@PathVariable("id") long id) {
		try {
			CorporationConsumer corporativeConsumer = corporationConsumerDAO.findCorporationConsumerByID(id);
			if (corporativeConsumer==null) {
				return null;
			}
			corporationConsumerDAO.delete(new CorporationConsumer(id));
		} catch (Exception ex) {
			return "Error deleting the corporative consumer:" + ex.toString();
		}
		return "Corporation Conrumser identified with id "+ id + " was succesfully deleted";
	}
	
	
	// Start the methods for relationships//
	/**
	 * Create a new parking consumer associated  to a  corporation consumer
	 * @param id the id of the corporation consumer  that you want  to asociate with the new parking consumer
	 * @param parkingconsumer the parking consumer you want to create with all their  attributes
	 * @exception returns a string that notifies that it doesn´t exists  the corporation consumer with the id in the data base 
	 * @return a string with the information if the new parking consumer is created and related to the corporation consumer
	 */
	@PostMapping("/create/{id}/parkingconsumers")
	@ResponseBody
	public String createCorporationConsumerParkingConsumer(@PathVariable("id") long id, @RequestBody @Valid ParkingConsumer parkingconsumer) 
	{
		try {
			CorporationConsumer corporativeConsumer = corporationConsumerDAO.findCorporationConsumerByID(id);
			if (corporativeConsumer==null) {
				throw new Exception( "The corporative consumer with id : "+ id +" isn´t registered in our data base");
			}
			parkingconsumer.setCorporativeConsumer(corporativeConsumer);
			parkingConsumerDAO.save(parkingconsumer);
			
		}catch(Exception ex) {
			return "Error creating parking consumer  related with the corporative consumer with id: " + id;
		}
		return "Parking consumer with id "+parkingconsumer.getId() + " succesfully created linked to Corporative Consumer with id: " + id;
	}
	
	/**
	 * Get the parking consumer with idP that is associated to the coporative consumer with id idC
	 * @param idC id of the corporative consumer that is on search
	 * @param idP id of the parking consumer that is on search
	 * @exception that the corporative consumer  you are trying to found doesn´t exist
	 * @return the parking consumer with idP and associated to the corporative consumer with idC
	 */
	@GetMapping("/getbyid/{idC}/parkingconsumers/{idP}")
	@ResponseBody
	public ParkingConsumer findByIDCorporativeConsumerParkingConsumer(@PathVariable("idC") long idC, @PathVariable("idP") long idP) {
		try {
			CorporationConsumer corporativeConsumer = corporationConsumerDAO.findCorporationConsumerByID(idC);
			if (corporativeConsumer==null) {
				throw new Exception("The Parking Consumer with id:  " + idC + "doesn´t exist");
			}
			ParkingConsumer parkingConsumer = parkingConsumerDAO.findParkingConsumerByID(idP);
			if(parkingConsumer.getCorporativeConsumer().getId() != corporativeConsumer.getId()) {
				throw new Exception("The Parking Consumer with id:  " + idP + "doesn´t exist");
			}
			return parkingConsumer;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Get all the parking consumers asociated to the  corporative consumer with idC
	 * @param idC id of the corporative consumer 
	 * @exception the corporative consumer with idC doesn´t exist in our  data base
	 * @return the list  of all parking consumers that are related to the parking consumer
	 */
	@GetMapping("/all/{idC}/parkingconsumers")
	@ResponseBody
	public List<ParkingConsumer> findCorporativeConsumersParkingConsumers(@PathVariable("idC") long idC){
		try {
			CorporationConsumer corporativeConsumer = corporationConsumerDAO.findCorporationConsumerByID(idC);
			if (corporativeConsumer==null) {
				throw new Exception("The Corporative Consumer with id:  " + idC + "doesn´t exist");
			}
			return corporativeConsumer.getParkingConsumers();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	/**
	 * Update the parking consumer with idP asociated to the corporative consumer with idC
	 * @param idC id of the corporative consumer
	 * @param parkingConsumer the parking consumer with all the new  information for update
	 * @return the parking consumer with the updated information
	 */
	@PutMapping("/update/{idC}/parkingconsumers/{idP}")
	@ResponseBody
	public ParkingConsumer updateCorporativeConsumerParkingConsumer(@PathVariable("idC") long idC, @RequestBody @Valid ParkingConsumer parkingConsumer) {
		try {
			CorporationConsumer corporativeConsumer = corporationConsumerDAO.findCorporationConsumerByID(idC);
			if (corporativeConsumer==null) {
				throw new Exception("The Corporative Consumer with id:  " + idC + "doesn´t exist");
			}
			ParkingConsumer oldparking = parkingConsumerDAO.findParkingConsumerByID(parkingConsumer.getId());
			if (oldparking==null) {
				throw new Exception("The Parking Consumer with that id  doesn´t exist");
			}
			oldparking.setCreditCard(parkingConsumer.getCreditCard());
			oldparking.setEmail(parkingConsumer.getEmail());
			oldparking.setName(parkingConsumer.getName());
			oldparking.setNickName(parkingConsumer.getNickName());
			oldparking.setPassword(parkingConsumer.getPassword());
			oldparking.setCorporativeConsumer(corporativeConsumer);
			parkingConsumerDAO.save(oldparking);
			return oldparking;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Delete a parking consumer with idP associated to the corporative consumer with idC
	 * @param idC id of the corporative consumer
	 * @param idP id of the parking consumer
	 * @return a string with the information if the parking consumer was  deleted
	 */
	@DeleteMapping("/delete/{idC}/parkingconsumers/{idP}")
	@ResponseBody
	public String deleteCorporativeConsumerParkingConsumer(@PathVariable("idC") long idC, @PathVariable("idP") long idP) {
		try {
			CorporationConsumer corporativeConsumer = corporationConsumerDAO.findCorporationConsumerByID(idC);
			if (corporativeConsumer==null) {
				throw new Exception("The Corporative Consumer with id:  " + idC + "doesn´t exist");
			}
			ParkingConsumer parkingConsumer = parkingConsumerDAO.findParkingConsumerByID(idP);
			if (parkingConsumer==null) {
				throw new Exception("The Parking Consumer with id:  " + idP + "doesn´t exist");
			}
			parkingConsumer.setCorporativeConsumer(corporativeConsumer);
			parkingConsumerDAO.delete(parkingConsumer);
		} catch (Exception e) {
			return "Error deleting the parking Consumer:" + e.toString();
		}
		return "parking consumer with id "+ idP +"succesfully deleted";
	}
}
