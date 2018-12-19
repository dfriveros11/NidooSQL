package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO.UserDAO;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.ParkingConsumer;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.ParkingProvider;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.User;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserDAO userDAO;

	/**
	 * /users/getbyid/{id} -> return the user having the passed id.
	 * @param id The id to search in the database
	 * @return The user or a message error if the user is not found.
	 */
	@GetMapping("/getbyid/{id}")
	@ResponseBody
	public User getUserID(@PathVariable("id") long id) {
		try {
			User user = userDAO.findUserByID(id);
			
			if(user == null) throw new Exception("The user with id: " + id + " doesn't exist");
			
			if (user instanceof ParkingConsumer) {
				ParkingConsumer parkingConsumer = (ParkingConsumer) user;
				return parkingConsumer;
			}

			if (user instanceof ParkingProvider) {
				ParkingProvider parkingProvider = (ParkingProvider) user;
				return parkingProvider;
			}

			return user;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * /users/getbyemail/{email} -> return the user having the passed id.
	 * @param email The email to search in the database.
	 * @return The user or a message error if the user is not found.
	 */
	@GetMapping("/getbyemail/{email}")
	@ResponseBody
	public User getUserEmail(@PathVariable("email") String email) {
		try {
			User user = userDAO.findByEmail(email);
			
			if(user == null) throw new Exception("The user with email: " + email + " doesn't exist");
			
			if (user instanceof ParkingConsumer) {
				ParkingConsumer parkingConsumer = (ParkingConsumer) user;
				return parkingConsumer;
			}

			if (user instanceof ParkingProvider) {
				ParkingProvider parkingProvider = (ParkingProvider) user;
				return parkingProvider;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * /users/ -> return all the user..
	 * @return The user or a message error if the user is not found.
	 */
	@ResponseBody
	@GetMapping("/all")
	public List<User> getUsers() {
		try {
			List<User> users = userDAO.getAllUsers();
			return users;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	
}
